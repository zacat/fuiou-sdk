package com.zoeyun.fuiou.sdk.error;

public class SdkRuntimeException extends RuntimeException{


    public SdkRuntimeException(Throwable e) {
        super(e);
    }

    public SdkRuntimeException(String msg) {
        super(msg);
    }

    public SdkRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}
