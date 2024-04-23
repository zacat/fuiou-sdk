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
public class CancelOrderRequest extends BasePayRequest {

    /**
     * 订单类型：
     * ALIPAY(支付宝)
     * WECHAT(微信)
     * UNIONPAY(银联二维码)
     * BESTPAY(翼支付)
     * DIGICCY(数字货币)
     */
    @Required
    @XStreamAlias("order_type")
    String orderType;

    /**
     * 商户订单号,商户系统内部的订单号（5到30个字符、只能包含字母数字,区分大小写)
     */
    @Required
    @XStreamAlias("mchnt_order_no")
    String mchntOrderNo;

    /**
     * 商户撤销单号
     */
    @Required
    @XStreamAlias("cancel_order_no")
    String cancelOrderNo;


    /**
     * 操作员
     */
    @XStreamAlias("operator_id")
    String operatorId;

    /**
     * 富友终端号(富友终端号与TUSN号二选一)
     */
    @XStreamAlias("reserved_fy_term_id")
    String reservedFyTermId;

    @Override
    protected void checkConstraints() throws SdkErrorException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {

    }
}
