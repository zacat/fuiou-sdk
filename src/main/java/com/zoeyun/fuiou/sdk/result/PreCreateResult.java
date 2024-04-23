package com.zoeyun.fuiou.sdk.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.bean.BasePayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class PreCreateResult extends BasePayResult {

    /**
     * 订单类型:ALIPAY,WECHAT,UNIONPAY(银联二维码),BESTPAY(翼支付)
     */
    @XStreamAlias("order_type")
    private String orderType;

    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("session_id")
    private String sessionId;

    /**
     * trade_type 为 APPLEPAY、UNIONPAY时返回，用以重定向拉起支付
     */
    @XStreamAlias("qr_code")
    private String qrCode;


    /**
     * 富友生成的订单号,需要商户与商户订单号进行关联
     */
    @XStreamAlias("reserved_fy_order_no")
    private String reservedFyOrderNo;

    /**
     * 富友系统内部追踪号
     */
    @XStreamAlias("reserved_fy_trace_no")
    private String reservedFyTraceNo;

    /**
     * 条码流水号，用户账单二维码对应的流水
     */
    @XStreamAlias("reserved_channel_order_id")
    private String reservedChannelOrderId;


    @Override
    protected void loadXml(Document d) {
        orderType = readXmlString(d, "order_type");
        sessionId = readXmlString(d, "session_id");
        qrCode = readXmlString(d, "qr_code");
        reservedFyOrderNo = readXmlString(d, "reserved_fy_order_no");
        reservedFyTraceNo = readXmlString(d, "reserved_fy_trace_no");
        reservedChannelOrderId = readXmlString(d, "reserved_channel_order_id");
    }
}
