package com.zoeyun.fuiou.sdk.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.annotation.Required;
import com.zoeyun.fuiou.sdk.bean.BasePayRequest;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import lombok.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class RefundQueryRequest extends BasePayRequest {
    /**
     * 商户系统内部的订单号
     */
    @Required
    @XStreamAlias("refund_order_no")
    String refundOrderNo;

    @Override
    protected void checkConstraints() throws SdkErrorException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("refund_order_no", refundOrderNo);
    }
}
