package com.zoeyun.fuiou.sdk.service.impl;

import com.zoeyun.fuiou.sdk.bean.BasePayResult;
import com.zoeyun.fuiou.sdk.config.SdkConfig;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.request.*;
import com.zoeyun.fuiou.sdk.result.*;
import com.zoeyun.fuiou.sdk.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class BasePayServiceImpl implements PayService {
    Logger log = LoggerFactory.getLogger(BasePayServiceImpl.class);

    SdkConfig sdkConfig;

    @Override
    public void setConfig(SdkConfig sdkConfig) {
        this.sdkConfig = sdkConfig;
    }

    @Override
    public SdkConfig getConfig() {
        return sdkConfig;
    }

    @Override
    public String getPayBaseUrl() {
        if (sdkConfig.isUseSandboxEnv()) {
            return "https://fundwx.fuiou.com";
        }
        return this.getConfig().getPayBaseUrl();
    }


    @Override
    public PreCreateResult preCreate(PreCreateRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/preCreate";
        String responseContent = this.post(url, request.toXML(), true);
        PreCreateResult result = BasePayResult.fromXML(responseContent, PreCreateResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public MicropayResult micropay(MicropayRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/micropay";
        String responseContent = this.post(url, request.toXML(), true);
        MicropayResult result = BasePayResult.fromXML(responseContent, MicropayResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public WxPreCreateResult preCreate(WxPreCreateRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/wxPreCreate";
        String responseContent = this.post(url, request.toXML(), true);
        WxPreCreateResult result = BasePayResult.fromXML(responseContent, WxPreCreateResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public CommonQueryResult query(CommonQueryRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/commonQuery";
        String responseContent = this.post(url, request.toXML(), true);
        CommonQueryResult result = BasePayResult.fromXML(responseContent, CommonQueryResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public CommonRefundResult refund(CommonRefundRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/commonRefund";
        String responseContent = this.post(url, request.toXML(), true);
        CommonRefundResult result = BasePayResult.fromXML(responseContent, CommonRefundResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public RefundQueryResult refundQuery(RefundQueryRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/refundQuery";
        String responseContent = this.post(url, request.toXML(), true);
        RefundQueryResult result = BasePayResult.fromXML(responseContent, RefundQueryResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public PayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws SdkErrorException {
        String xml = null;
        try {
            xml = URLDecoder.decode(xmlData, "GBK");
            log.info("支付异步通知请求参数：{}", xml);
            PayOrderNotifyResult result = BasePayResult.fromXML(xml, PayOrderNotifyResult.class);
            log.debug("支付异步通知请求解析后的对象：{}", result);
            result.checkResult(sdkConfig, false);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new SdkErrorException(e.getMessage());
        }
    }

    @Override
    public CloseOrderResult close(CloseOrderRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/closeorder";
        String responseContent = this.post(url, request.toXML(), true);
        CloseOrderResult result = BasePayResult.fromXML(responseContent, CloseOrderResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }

    @Override
    public CancelOrderResult cancel(CancelOrderRequest request) throws SdkErrorException {
        request.checkAndSign(sdkConfig);
        String url = this.getPayBaseUrl() + "/cancelorder";
        String responseContent = this.post(url, request.toXML(), true);
        CancelOrderResult result = BasePayResult.fromXML(responseContent, CancelOrderResult.class);
        result.checkResult(sdkConfig, true);
        return result;
    }
}
