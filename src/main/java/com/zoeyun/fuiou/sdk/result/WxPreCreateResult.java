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
public class WxPreCreateResult extends BasePayResult {

    /**
     * 支付通道对应的子商户识别码
     */
    @XStreamAlias("sub_mer_id")
    private String subMerId;

    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("session_id")
    private String sessionId;

    /**
     * 支二维码链接
     * trade_type 为 APPLEPAY、UNIONPAY时返回，用以重定向拉起支付
     */
    @XStreamAlias("qr_code")
    private String qrCode;

    /**
     * 子商户公众号id
     */
    @XStreamAlias("sub_appid")
    private String subAppid;

    /**
     * 子商户用户标识
     */
    @XStreamAlias("sub_openid")
    private String subOpenid;

    /**
     * sdk_appid
     */
    @XStreamAlias("sdk_appid")
    private String sdkAppid;

    /**
     * 时间戳，自1970年1月1日 0点0分0秒以来的秒数
     */
    @XStreamAlias("sdk_timestamp")
    private String sdkTimestamp;

    /**
     * 随字符串
     */
    @XStreamAlias("sdk_noncestr")
    private String sdkNoncestr;

    /**
     * 订单性情扩展字符串
     */
    @XStreamAlias("sdk_package")
    private String sdkPackage;


    /**
     * 签名方式，trade_type 为 JSAPI、LETPAY时才返回
     */
    @XStreamAlias("sdk_signtype")
    private String sdkSigntype;


    /**
     * 签名
     */
    @XStreamAlias("sdk_paysign")
    private String sdkPaysign;


    /**
     * trade_type 为 APP时才返回
     */
    @XStreamAlias("sdk_partnerid")
    private String sdkPartnerid;

    /**
     * 富友生成的订单号,需要商户与商户订单号进行关联
     */
    @XStreamAlias("reserved_fy_order_no")
    private String reservedFyOrderNo;


    /**
     * 富友交易日期
     */
    @XStreamAlias("reserved_fy_settle_dt")
    private String reservedFySettleDt;


    /**
     * 渠道交易流水号
     * trade_type 为 FWC、MPAY时返回（用于调起支付）
     */
    @XStreamAlias("reserved_transaction_id")
    private String reservedTransactionId;


    /**
     * 渠道交易流水号
     * trade_type 为 FWC、MPAY时返回（用于调起支付）
     */
    @XStreamAlias("reserved_pay_info")
    private String reservedPayInfo;


    /**
     * 渠道交易流水号
     * trade_type 为 FWC、MPAY时返回（用于调起支付）
     */
    @XStreamAlias("reserved_channel_order_id")
    private String reservedChannelOrderId;


    /**
     * 附加数据
     */
    @XStreamAlias("reserved_addn_inf")
    private String reservedAddnInf;

    @Override
    protected void loadXml(Document d) {
        sessionId = readXmlString(d, "session_id");
        qrCode = readXmlString(d, "qr_code");
        subAppid = readXmlString(d, "sub_appid");
        subOpenid = readXmlString(d, "sub_openid");
        sdkAppid = readXmlString(d, "sdk_appid");
        sdkTimestamp = readXmlString(d, "sdk_timestamp");
        sdkNoncestr = readXmlString(d, "sdk_noncestr");
        sdkPackage = readXmlString(d, "sdk_package");
        sdkSigntype = readXmlString(d, "sdk_signtype");
        sdkPaysign = readXmlString(d, "sdk_paysign");
        sdkPartnerid = readXmlString(d, "sdk_partnerid");
        reservedFyOrderNo = readXmlString(d, "reserved_fy_order_no");
        reservedFySettleDt = readXmlString(d, "reserved_fy_settle_dt");
        reservedTransactionId = readXmlString(d, "reserved_transaction_id");
        reservedPayInfo = readXmlString(d, "reserved_pay_info");
        reservedChannelOrderId = readXmlString(d, "reserved_channel_order_id");
        reservedAddnInf = readXmlString(d, "reserved_addn_inf");
    }
}
