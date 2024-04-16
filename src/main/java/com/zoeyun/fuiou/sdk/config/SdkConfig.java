package com.zoeyun.fuiou.sdk.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@ToString(exclude = "verifier")
@EqualsAndHashCode(exclude = "verifier")
public class SdkConfig {

    private static final String DEFAULT_PAY_BASE_URL = "https://spay-cloud.fuioupay.com";

    private String publicKey;
    private String privateKey;
    private String insCd;
    private String mchntCd;
    private String termId;
    private String payBaseUrl = DEFAULT_PAY_BASE_URL;
    /**
     * http请求连接超时时间.
     */
    private int httpConnectionTimeout = 5000;

    /**
     * http请求数据读取等待时间.
     */
    private int httpTimeout = 10000;
    private boolean useSandboxEnv = false;

    /**
     * 返回所设置的微信支付接口请求地址域名.
     *
     * @return 微信支付接口请求地址域名
     */
    public String getPayBaseUrl() {
        if (StringUtils.isEmpty(this.payBaseUrl)) {
            return DEFAULT_PAY_BASE_URL;
        }

        return this.payBaseUrl;
    }
}
