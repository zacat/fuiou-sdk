package com.zoeyun.fuiou.sdk.service.impl;

import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.result.PayOrderNotifyResult;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.https.DefaultHostnameVerifier;

import javax.net.ssl.SSLContext;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PayServiceApacheHttpImpl extends BasePayServiceImpl {

    Logger log = LoggerFactory.getLogger(PayServiceApacheHttpImpl.class);


    @SneakyThrows
    private UrlEncodedFormEntity createEntry(String requestStr) {
        String reqBody = URLEncoder.encode(requestStr, "GBK");
        System.out.println("==============================编码后字符串==============================\r\n" + reqBody);
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("req", reqBody));
        return new UrlEncodedFormEntity(formparams, "GBK");
    }

    private HttpClientBuilder createHttpClientBuilder(boolean useKey) throws SdkErrorException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        return httpClientBuilder;
    }

    private HttpPost createHttpPost(String url, String requestStr) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(this.createEntry(requestStr));

        httpPost.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(this.getConfig().getHttpConnectionTimeout())
                .setConnectTimeout(this.getConfig().getHttpConnectionTimeout())
                .setSocketTimeout(this.getConfig().getHttpTimeout())
                .build());

        return httpPost;
    }

    @Override
    public byte[] postForBytes(String url, String requestStr, boolean useKey) throws SdkErrorException {
        try {
            HttpClientBuilder httpClientBuilder = createHttpClientBuilder(useKey);
            HttpPost httpPost = this.createHttpPost(url, requestStr);
            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    final byte[] bytes = EntityUtils.toByteArray(response.getEntity());
                    final String responseData = Base64.getEncoder().encodeToString(bytes);
                    this.log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据(Base64编码后)】：{}", url, requestStr, responseData);
                    return bytes;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());

            throw new SdkErrorException(e.getMessage());
        }
    }

    @Override
    public String post(String url, String requestStr, boolean useKey) throws SdkErrorException {
        System.out.println("==============================待编码字符串==============================\r\n" + requestStr);
        try {
            HttpClientBuilder httpClientBuilder = this.createHttpClientBuilder(useKey);
            HttpPost httpPost = this.createHttpPost(url, requestStr);
            try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    //String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    String responseString = EntityUtils.toString(response.getEntity(), Charset.forName("GBK"));
                    responseString = URLDecoder.decode(responseString, "GBK");
                    this.log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, requestStr, responseString);

                    return responseString;
                }
            } finally {
                httpPost.releaseConnection();
            }
        } catch (Exception e) {
            this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
            throw new SdkErrorException(e.getMessage());
        }
    }

    @Override
    public PayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws SdkErrorException {
        return null;
    }
}
