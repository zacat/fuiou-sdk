package com.zoeyun.fuiou.sdk.service;

import com.zoeyun.fuiou.sdk.config.SdkConfig;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.request.*;
import com.zoeyun.fuiou.sdk.result.*;

public interface PayService {


    void setConfig(SdkConfig sdkConfig);

    SdkConfig getConfig();

    /**
     * 获取微信支付请求url前缀，沙箱环境可能不一样.
     *
     * @return the pay base url
     */
    String getPayBaseUrl();
    /**
     * 发送post请求，得到响应字节数组.
     *
     * @param url        请求地址
     * @param requestStr 请求信息
     * @param useKey     是否使用证书
     * @return 返回请求结果字节数组 byte [ ]
     * @throws SdkErrorException the wx pay exception
     */
    byte[] postForBytes(String url, String requestStr, boolean useKey) throws SdkErrorException;

    /**
     * 发送post请求，得到响应字符串.
     *
     * @param url        请求地址
     * @param requestStr 请求信息
     * @param useKey     是否使用证书
     * @return 返回请求结果字符串 string
     * @throws SdkErrorException the wx pay exception
     */
    String post(String url, String requestStr, boolean useKey) throws SdkErrorException;

    MicropayResult micropay(MicropayRequest request) throws SdkErrorException;
    PreCreateResult preCreate(PreCreateRequest request) throws SdkErrorException;
    WxPreCreateResult preCreate(WxPreCreateRequest request) throws SdkErrorException;

    CommonQueryResult query(CommonQueryRequest request) throws SdkErrorException;

    CommonRefundResult refund(CommonRefundRequest request) throws SdkErrorException;

    RefundQueryResult refundQuery(RefundQueryRequest request) throws SdkErrorException;

    CloseOrderResult close(CloseOrderRequest request) throws SdkErrorException;

    CancelOrderResult cancel(CancelOrderRequest request) throws SdkErrorException;

    PayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws SdkErrorException;

}
