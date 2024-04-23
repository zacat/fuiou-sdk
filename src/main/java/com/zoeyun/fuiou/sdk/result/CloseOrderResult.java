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
public class CloseOrderResult extends BasePayResult {

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
    @Override
    protected void loadXml(Document d) {
        orderType = readXmlString(d,"order_type");
        mchntOrderNo = readXmlString(d,"mchnt_order_no");
    }
}
