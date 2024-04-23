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
public class MicropayRequest extends BasePayRequest {

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
     * 商品名称, 显示在用户账单的商品、商品说明等地方
     */
    @Required
    @XStreamAlias("goods_des")
    String goodsDes;

    /**
     * 单品优惠功能字段，见文档中good_detail说明字段
     */
    @XStreamAlias("goods_detail")
    String goodsDetail;

    /**
     * 商品标记
     */
    @XStreamAlias("goods_tag")
    String goodsTag;


    /**
     * 附加数据
     * 如果需要用到微信点餐数据回传，该字段需要填写OrderSource=FoodOrder
     */
    @XStreamAlias("addn_inf")
    String addnInf;

    /**
     * 商户订单号,商户系统内部的订单号（5到30个字符、只能包含字母数字,区分大小写)
     */
    @XStreamAlias("mchnt_order_no")
    String mchntOrderNo;

    /**
     * 货币类型,默认人民币：CNY
     */
    @XStreamAlias("curr_type")
    String currType;

    /**
     * 总金额,订单总金额,单位为分
     */
    @Required
    @XStreamAlias("order_amt")
    Integer orderAmt;

    /**
     * 实时交易终端IP(后期富友、银联侧风控主要依据，请真实填写)
     */
    @Required
    @XStreamAlias("term_ip")
    String termIp;

    /**
     * 交易起始时间,订单生成时间,格式为yyyyMMddHHmmss
     */
    @Required
    @XStreamAlias("txn_begin_ts")
    String txnBeginTs;

    /**
     * 扫码支付授权码，设备读取用户的条码或者二维码信息
     */
    @Required
    @XStreamAlias("authCode")
    String authCode;


    /**
     支付场景,默认1；
     1: 条码支付
     2: 声波支付
     3: 刷脸支付
     */
    @Required
    @XStreamAlias("sence")
    String sence;

    /**
     * 子商户公众号id
     */
    @XStreamAlias("reserved_sub_appid")
    String reservedSubAppid;

    /**
     * 限制支付
     * no_credit:不能使用信用卡
     * credit_group：不能使用花呗以及信用卡
     */
    @XStreamAlias("reserved_limit_pay")
    String reservedLimitPay;

    /**
     * 交易关闭时间
     * 默认120min，上限：360min
     */
    @XStreamAlias("reserved_expire_minute")
    Integer reservedExpireMinute;

    /**
     终端序列号
     */
    @XStreamAlias("reserved_fy_term_id")
    String reservedFyTermId;

    /**
     * 富友终端类型
     * 0:其他
     * 1:富友终端
     * 2:POS机
     * 3:台卡
     * 4:PC软件
     */
    @XStreamAlias("reserved_fy_term_type")
    String reservedFyTermType;


    /**
     终端序列号
     */
    @XStreamAlias("reserved_fy_term_sn")
    String reservedFyTermSn;

    /**
     * 设备信息，托传给微信。用于单品券核销
     */
    @XStreamAlias("reserved_device_info")
    String reservedDeviceInfo;


    /**
     * JSON串，花呗分期示例值：{"dynamic_token_out_biz_no":"66666","hb_fq_num":"3","industry_reflux_info":{"scene_code":"metro_tradeorder","channel":"xxxx","scene_data":{"asset_name":"ALIPAY"}},"food_order_type":"qr_order"}信用卡分期示例值：{"fq_num":"3"}银联分期示例值：{"hb_fq_num":"3","industry_reflux_info":"ICBC,ABC,CCB"}
     */
    @XStreamAlias("reserved_ali_extend_params")
    String reservedAliExtendParams;

    /**
     * 门店id（目前仅支持支付宝）
     */
    @XStreamAlias("reserved_store_code")
    String reservedStoreCode;

    /**
     * 支付宝店铺编号
     */
    @XStreamAlias("reserved_alipay_store_id")
    String reservedAlipayStoreId;

    /**
     * 终端信息说明字段，见文档中reserved_terminal_info终端信息说明字段（259号文，终端信息）
     */
    @XStreamAlias("reserved_terminal_info")
    String reservedTerminalInfo;

    /**
     * 姓名
     */
    @XStreamAlias("reserved_business_params")
    String reservedBusinessParams;

    /**
     * 身份证
     */
    @XStreamAlias("reserved_scene_info")
    String reservedSceneInfo;
    @Override
    protected void checkConstraints() throws SdkErrorException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("goods_des", goodsDes);
        map.put("goods_detail", goodsDetail);
        map.put("goods_tag", goodsTag);
        map.put("addn_inf", addnInf);
        map.put("mchnt_order_no", mchntOrderNo);
        map.put("curr_type", currType);
        map.put("order_amt", orderAmt.toString());
        map.put("term_ip", termIp);
        map.put("txn_begin_ts", txnBeginTs);
        map.put("auth_code", authCode);
        map.put("sence", sence);
        map.put("reserved_sub_appid", reservedSubAppid);
        map.put("reserved_limit_pay", reservedLimitPay);
        map.put("reserved_expire_minute", reservedExpireMinute.toString());
        map.put("reserved_fy_term_id", reservedFyTermId);
        map.put("reserved_fy_term_type", reservedFyTermType);
        map.put("reserved_fy_term_sn", reservedFyTermSn);
        map.put("reserved_device_info", reservedDeviceInfo);
        map.put("reserved_ali_extend_params", reservedAliExtendParams);
        map.put("reserved_store_code", reservedStoreCode);
        map.put("reserved_alipay_store_id", reservedAlipayStoreId);
        map.put("reserved_terminal_info", reservedTerminalInfo);
        map.put("reserved_business_params", reservedBusinessParams);
        map.put("reserved_scene_info", reservedSceneInfo);
    }
}
