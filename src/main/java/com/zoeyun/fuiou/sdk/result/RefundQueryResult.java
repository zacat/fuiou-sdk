package com.zoeyun.fuiou.sdk.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.annotation.Required;
import com.zoeyun.fuiou.sdk.bean.BasePayResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class RefundQueryResult extends BasePayResult {

    /**
     * 交易状态
     * SUCCESS—退款成功
     * PAYERROR--退款失败
     */
    @XStreamAlias("trans_stat")
    private String transStat;

    /**
     * 订单类型：
     * ALIPAY(支付宝)
     * WECHAT(微信)
     * UNIONPAY(银联二维码)
     * BESTPAY(翼支付)
     * DIGICCY(数字货币)
     */
    @XStreamAlias("order_type")
    String orderType;

    /**
     * 商户订单号,商户系统内部的订单号（5到30个字符、只能包含字母数字,区分大小写)
     */
    @XStreamAlias("mchnt_order_no")
    String mchntOrderNo;

    /**
     * 商户退款单号
     */
    @XStreamAlias("refund_order_no")
    private String refundOrderNo;


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
    private Integer reservedRefundAmt;

    /**
     * 富友系统内部追踪号
     */
    @XStreamAlias("reserved_fy_trace_no")
    private String reservedFyTraceNo;

    /**
     * 富友系统内部追踪号
     */
    @XStreamAlias("reserved_promotion_detail")
    private String reservedPromotionDetail;

    @Override
    protected void loadXml(Document d) {
        transStat = readXmlString(d, "trans_stat");
        orderType = readXmlString(d, "order_type");
        mchntOrderNo = readXmlString(d, "mchnt_order_no");
        refundOrderNo = readXmlString(d, "refund_order_no");
        refundId = readXmlString(d, "refund_id");
        reservedFySettleDt = readXmlString(d, "reserved_fy_settle_dt");
        reservedRefundAmt = readXmlInteger(d, "reserved_refund_amt");
        reservedFyTraceNo = readXmlString(d, "reserved_fy_trace_no");
        reservedPromotionDetail = readXmlString(d, "reserved_promotion_detail");
    }
}
