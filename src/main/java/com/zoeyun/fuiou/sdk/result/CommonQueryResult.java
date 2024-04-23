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
public class CommonQueryResult extends BasePayResult {

    /**
     * 订单类型:ALIPAY, WECHAT,UNIONPAY(银联二维码）,BESTPAY(翼支付)
     */
    @XStreamAlias("buyer_id")
    private String buyerId;

    /**
     * 订单类型:ALIPAY, WECHAT,UNIONPAY(银联二维码）,BESTPAY(翼支付)
     */
    @XStreamAlias("order_type")
    private String orderType;

    /**
     * 查询状态，详见应答码表--查询应答码
     */
    @XStreamAlias("trans_stat")
    private String transStat;


    /**
     * 订单金额，单位为分
     */
    @XStreamAlias("order_amt")
    private Integer orderAmt;

    /**
     * 渠道订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;
    /**
     * 商户订单号, 商户系统内部的订单号
     */
    @XStreamAlias("mchnt_order_no")
    private String mchntOrderNo;

    /**
     * 附加数据
     */
    @XStreamAlias("addn_inf")
    private String addnInf;

    /**
     * 富友交易日期
     */
    @XStreamAlias("reserved_fy_settle_dt")
    private String reservedFySettleDt;
    /**
     * 优惠金额(分)
     */
    @XStreamAlias("reserved_coupon_fee")
    private String reservedCouponFee;
    /**
     * 买家在渠道登录账号
     */
    @XStreamAlias("reserved_buyer_logon_id")
    private String reservedBuyerLogonId;
    /**
     * 支付宝交易资金渠道，详细渠道
     */
    @XStreamAlias("reserved_fund_bill_list")
    private String reservedFundBillList;
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
    /**
     * 富友终端号(富友终端号与TUSN号二选一)
     */
    @XStreamAlias("reserved_fy_term_id")
    private String reservedFyTermId;
    /**
     * 1--表示信用卡或者花呗
     * 0--表示其他(非信用方式)
     * 不填，表示未知
     */
    @XStreamAlias("reserved_is_credit")
    private String reservedIsCredit;

    /**
     * 用户支付时间yyyyMMddHHmmss
     */
    @XStreamAlias("reserved_txn_fin_ts")
    private String reservedTxnFinTs;

    /**
     * 应结算订单金额，分为单位的整数。
     * 只有成功交易才会返回
     * 如果使用了商户免充值优惠券，该值为订单金额-商户免充值
     * 如果没有使用商户免充值，该值等于订单金额
     */
    @XStreamAlias("reserved_settlement_amt")
    private Integer reservedSettlementAmt;

    /**
     * 付款方式
     */
    @XStreamAlias("reserved_bank_type")
    private String reservedBankType;
    /**
     * 微信营销详情（资金单位：分），见文档中reserved_promotion_detail说明字段
     */
    @XStreamAlias("reserved_promotion_detail")
    private String reservedPromotionDetail;


    /**
     * 	支付宝交易支付时所使用的所有优惠券信息（资金单位：元）
     * "[{"amount":"1.00","merchant_contribute":"1.00",
     * "name":"1.00元代金券","other_contribute":"0.00",
     * "template_id":"20221107000730017617007JGOUG",
     * "id":"202211070007300282330EB2Y9QQ",
     * "type":"ALIPAY_BIZ_VOUCHER"}]"
     */
    @XStreamAlias("reserved_voucher_detail_list")
    private String reservedVoucherDetailList;


    /**
     * 	支付宝交易支付所使用的单品券优惠的商品优惠信息（资金单位：元）
     * "[{"goodsId":"STANDARD1026181538"," goodsName":"雪碧","discountAmount":"10.00"}]"
     */
    @XStreamAlias("reserved_discount_goods_detail")
    private String reservedDiscountGoodsDetail;


    /**
     * 	返回值：1=商户出息
     */
    @XStreamAlias("reserved_hb_is_seller")
    private String reservedHbIsSeller;

    /**
     * 手续费减免标识
     * Y：表示减免（默认）
     * N：表示不减免
     */
    @XStreamAlias("reserved_service_charge_flag")
    private String reservedServiceChargeFlag;

    /**
     * 交易子商户号
     */
    @XStreamAlias("reserved_sub_mchnt_cd")
    private String reservedSubMchntCd;
    /**
     * 交易类型
     * ALBS支付宝被扫、WXBS微信被扫、WXFP微信刷脸、ALFP支付宝刷脸、
     */
    @XStreamAlias("reserved_trade_type")
    private String reservedTradeType;




    @Override
    protected void loadXml(Document d) {
        buyerId = readXmlString(d, "buyer_id");
        orderType = readXmlString(d,"order_type");
        transStat = readXmlString(d,"trans_stat");
        orderAmt = readXmlInteger(d, "order_amt");
        transactionId = readXmlString(d,"transaction_id");
        mchntOrderNo = readXmlString(d,"mchnt_order_no");
        addnInf = readXmlString(d,"addn_inf");

        reservedFySettleDt = readXmlString(d,"reserved_fy_settle_dt");
        reservedCouponFee = readXmlString(d,"reserved_coupon_fee");

        reservedBuyerLogonId = readXmlString(d,"reserved_buyer_logon_id");
        reservedFundBillList = readXmlString(d,"reserved_fund_bill_list");
        reservedFyTraceNo = readXmlString(d,"reserved_fy_trace_no");
        reservedChannelOrderId = readXmlString(d,"reserved_channel_order_id");
        reservedFyTermId = readXmlString(d,"reserved_fy_term_id");
        reservedIsCredit = readXmlString(d,"reserved_is_credit");
        reservedTxnFinTs = readXmlString(d,"reserved_txn_fin_ts");

        reservedSettlementAmt = readXmlInteger(d,"reserved_settlement_amt");
        reservedBankType = readXmlString(d,"reserved_bank_type");
        reservedPromotionDetail = readXmlString(d,"reserved_promotion_detail");
        reservedHbIsSeller = readXmlString(d,"reserved_hb_is_seller");
        reservedServiceChargeFlag = readXmlString(d,"reserved_service_charge_flag");
        reservedVoucherDetailList = readXmlString(d,"reserved_voucher_detail_list");
        reservedDiscountGoodsDetail = readXmlString(d,"reserved_discount_goods_detail");
        reservedSubMchntCd = readXmlString(d,"reserved_sub_mchnt_cd");
        reservedTradeType = readXmlString(d,"reserved_trade_type");
    }
}
