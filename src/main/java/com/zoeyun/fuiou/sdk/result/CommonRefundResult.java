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
public class CommonRefundResult extends BasePayResult {

    /**
     * 订单类型:ALIPAY, WECHAT,UNIONPAY(银联二维码）,BESTPAY(翼支付)
     */
    @XStreamAlias("order_type")
    private String orderType;

    /**
     * 商户订单号, 商户系统内部的订单号
     */
    @XStreamAlias("mchnt_order_no")
    private String mchntOrderNo;

    /**
     * 商户退款单号
     */
    @XStreamAlias("refund_order_no")
    private String refundOrderNo;

    /**
     * 渠道订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 渠道退款流水号
     */
    @XStreamAlias("refund_id")
    private String refundId;

    /**
     * 富友交易日期
     */
    @XStreamAlias("reserved_fy_settle_dt")
    private String reservedFySettleDt;

    /**
     * 退款金额
     */
    @XStreamAlias("reserved_refund_amt")
    private String reservedRefundAmt;

    /**
     * 富友系统内部追踪号
     */
    @XStreamAlias("reserved_fy_trace_no")
    private String reservedFyTraceNo;


    @Override
    protected void loadXml(Document d) {

        orderType = readXmlString(d,"order_type");


        mchntOrderNo = readXmlString(d,"mchnt_order_no");
        refundOrderNo = readXmlString(d,"refund_order_no");


        transactionId = readXmlString(d,"transaction_id");
        refundId = readXmlString(d,"refund_id");

        reservedFySettleDt = readXmlString(d,"reserved_fy_settle_dt");
        reservedRefundAmt = readXmlString(d,"reserved_refund_amt");
        reservedFyTraceNo = readXmlString(d,"reserved_fy_trace_no");

    }
}
