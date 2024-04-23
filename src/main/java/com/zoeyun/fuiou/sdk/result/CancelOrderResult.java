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
public class CancelOrderResult extends BasePayResult {


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
     * 商户撤销单号
     */
    @XStreamAlias("cancel_order_no")
    private String cancelOrderNo;

    /**
     * 渠道订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 渠道撤销流水号
     */
    @XStreamAlias("cancel_id")
    private String cancelId;

    /**
     * 是否有资金流向(只有支付宝有该字段)
     */
    @XStreamAlias("fund_change")
    private String fundChange;

    /**
     * 是否需要新调用撤销(当为Y时，需要重新调用撤销接口)
     */
    @XStreamAlias("recall")
    private String recall;

    /**
     * 富友交易日期
     */
    @XStreamAlias("reserved_fy_settle_dt")
    private String reservedFySettleDt;



    /**
     * 富友系统内部追踪号
     */
    @XStreamAlias("reserved_fy_trace_no")
    private String reservedFyTraceNo;

    @Override
    protected void loadXml(Document d) {
        orderType = readXmlString(d,"order_type");
        mchntOrderNo = readXmlString(d,"mchnt_order_no");
        cancelOrderNo = readXmlString(d,"cancel_order_no");
        transactionId = readXmlString(d,"transaction_id");
        cancelId = readXmlString(d,"cancel_id");
        fundChange = readXmlString(d,"fund_change");
        recall = readXmlString(d,"recall");
        reservedFySettleDt = readXmlString(d,"reserved_fy_settle_dt");
        reservedFyTraceNo = readXmlString(d,"reserved_fy_trace_no");

    }
}
