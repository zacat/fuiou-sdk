package com.zoeyun.fuiou.sdk.bean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.config.SdkConfig;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.error.SdkRuntimeException;
import com.zoeyun.fuiou.sdk.utils.BeanUtils;
import com.zoeyun.fuiou.sdk.utils.SignUtils;
import com.zoeyun.fuiou.sdk.utils.XmlConfig;
import com.zoeyun.fuiou.sdk.utils.xml.XStreamInitializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public abstract class BasePayRequest implements Serializable {

    private static final long serialVersionUID = 759355325574201645L;


    @XStreamAlias("version")
    String version;
    /**
     * 机构号,接入机构在富友的唯一代码
     */
    @XStreamAlias("ins_cd")
    String insCd;
    /**
     * 商户号,富友分配给二级商户的商户号
     */
    @XStreamAlias("mchnt_cd")
    String mchntCd;
    /**
     * 终端号(没有真实终端号统一填88888888)
     */
    @XStreamAlias("term_id")
    String termId;
    /**
     * 随机字符串
     */
    @XStreamAlias("random_str")
    String randomStr;
    /**
     * 签名, 详见签名生成算法
     */
    @XStreamAlias("sign")
    String sign;

    /**
     * 检查请求参数内容，包括必填参数以及特殊约束.
     */
    private void checkFields() throws SdkErrorException {
        //check required fields
        BeanUtils.checkRequiredFields(this);
        //check other parameters
        this.checkConstraints();
    }

    /**
     * 检查约束情况.
     *
     * @throws SdkErrorException the wx pay exception
     */
    protected abstract void checkConstraints() throws SdkErrorException;


    /**
     * 返回xml结构的根节点名称
     *
     * @return 默认返回"xml", 特殊情况可以在子类中覆盖
     */
    protected String xmlRootTagName() {
        return "xml";
    }

    /**
     * 使用快速算法组装xml
     */
    private String toFastXml() {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(xmlRootTagName());

            Map<String, String> signParams = getSignParams();
            signParams.put("sign", sign);
            for (Map.Entry<String, String> entry : signParams.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                Element elm = root.addElement(entry.getKey());
                elm.addText(entry.getValue());
            }

            return document.asXML();
        } catch (Exception e) {
            throw new SdkRuntimeException("generate xml error", e);
        }
    }

    public String toXML() {

        if (XmlConfig.fastMode) {
            return toFastXml();
        }
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(this.getClass());
        return xstream.toXML(this);
    }

    /**
     * 签名时，忽略的参数.
     *
     * @return the string [ ]
     */
    protected String[] getIgnoredParamsForSign() {
        return new String[0];
    }

    /**
     * 获取签名时需要的参数.
     * 注意：不含sign属性
     */
    public Map<String, String> getSignParams() {
        Map<String, String> map = new HashMap<>(8);
        map.put("version", version);
        map.put("ins_cd", insCd);
        map.put("mchnt_cd", mchntCd);
        map.put("termId", termId);
        storeMap(map);
        return map;
    }

    /**
     * 将属性组装到一个Map中，供签名和最终发送XML时使用.
     * 这里需要将所有的属性全部保存进来，签名的时候会自动调用getIgnoredParamsForSign进行忽略，
     * 不用担心。否则最终生成的XML会缺失。
     *
     * @param map 传入的属性Map
     */
    protected abstract void storeMap(Map<String, String> map);

    /**
     * <pre>
     * 检查参数，并设置签名.
     * 1、检查参数（注意：子类实现需要检查参数的而外功能时，请在调用父类的方法前进行相应判断）
     * 2、补充系统参数，如果未传入则从配置里读取
     * 3、生成签名，并设置进去
     * </pre>
     *
     * @param config 支付配置对象，用于读取相应系统配置信息
     * @throws SdkErrorException the wx pay exception
     */
    public void checkAndSign(SdkConfig config) throws SdkErrorException {
        this.checkFields();

        if (StringUtils.isBlank(getRandomStr())) {
            this.setRandomStr(String.valueOf(System.currentTimeMillis()));
        }
        if (StringUtils.isBlank(getVersion())) {
            this.setVersion("1.0");
        }
        if (StringUtils.isBlank(getInsCd())) {
            this.setInsCd(config.getInsCd());
        }
        if (StringUtils.isBlank(getMchntCd())) {
            this.setMchntCd(config.getMchntCd());
        }
        if (StringUtils.isBlank(getTermId())) {
            this.setTermId(config.getTermId());
        }
        //设置签名字段的值
        this.setSign(SignUtils.createSign(this, config.getPrivateKey(), this.getIgnoredParamsForSign()));
    }

}
