package com.zoeyun.fuiou.sdk.error;

public class SdkErrorException extends Exception {
    /**
     * 微信错误代码.
     */
    private int errorCode;

    /**
     * 微信错误信息.
     * （如果可以翻译为中文，就为中文）
     */
    private String errorMsg;

    /**
     * 微信接口返回的错误原始信息（英文）.
     */
    private String errorMsgEn;

    public SdkErrorException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SdkErrorException(String errorMsg) {
        this.errorCode = 400;
        this.errorMsg = errorMsg;
    }

}
