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
public class CommonRefundRequest extends BasePayRequest {


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
     * 商户退款单号
     * (5到30个字符、只能包含字母数字或者下划线，区分大小写)(也要加订单前缀)
     */
    @Required
    @XStreamAlias("refund_order_no")
    String refundOrderNo;

    /**
     * 总金额
     */
    @Required
    @XStreamAlias("total_amt")
    Integer totalAmt;

    /**
     * 退款金额
     */
    @Required
    @XStreamAlias("refund_amt")
    Integer refundAmt;

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

    /**
     * 原交易日期(yyyyMMdd)！该值必定等于reserved_fy_settle_dt(富友接收交易时间。理论和合作方下单时间一致。微量跨日交易会不一致)。
     * 不填该值，支持30天内的交易进行退款。
     * 填写该值，支持360天内的退款。
     */
    @XStreamAlias("reserved_origi_dt")
    String reservedOrigiDt;

    /**
     * 附加数据
     */
    @XStreamAlias("reservedAddnInf")
    String reservedAddnInf;

    @Override
    protected void checkConstraints() throws SdkErrorException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("order_type", orderType);
        map.put("refund_order_no", refundOrderNo);
        map.put("total_amt", totalAmt.toString());
        map.put("refund_amt", refundAmt.toString());
        map.put("operator_id", operatorId);
        map.put("reserved_fy_term_id", reservedFyTermId);
        map.put("reserved_origi_dt", reservedOrigiDt);
        map.put("reserved_addn_inf", reservedAddnInf);
    }
}
