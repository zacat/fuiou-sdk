package com.zoeyun.fuiou.sdk.service.impl;

import com.zoeyun.fuiou.sdk.bean.BasePayResult;
import com.zoeyun.fuiou.sdk.config.SdkConfig;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.request.PreCreateRequest;
import com.zoeyun.fuiou.sdk.result.PreCreateResult;
import com.zoeyun.fuiou.sdk.service.PayService;

public abstract class BasePayServiceImpl implements PayService {


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
}
