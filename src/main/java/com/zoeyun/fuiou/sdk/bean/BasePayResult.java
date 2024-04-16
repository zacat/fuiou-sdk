package com.zoeyun.fuiou.sdk.bean;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.config.SdkConfig;
import com.zoeyun.fuiou.sdk.error.SdkErrorException;
import com.zoeyun.fuiou.sdk.error.SdkRuntimeException;
import com.zoeyun.fuiou.sdk.utils.SignUtils;
import com.zoeyun.fuiou.sdk.utils.XmlConfig;
import com.zoeyun.fuiou.sdk.utils.xml.XStreamInitializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public abstract class BasePayResult implements Serializable {
    private static final long serialVersionUID = -3841297389122436266L;

    @XStreamAlias("result_code")
    String resultCode;
    @XStreamAlias("result_msg")
    String resultMsg;
    @XStreamAlias("ins_cd")
    String insCd;
    @XStreamAlias("mchnt_cd")
    String mchntCd;
    @XStreamAlias("term_id")
    String termId;
    @XStreamAlias("random_str")
    String randomStr;
    @XStreamAlias("sign")
    String sign;

    //以下为辅助属性
    /**
     * xml字符串.
     */
    private String xmlString;

    /**
     * xml的Document对象，用于解析xml文本.
     * make xmlDoc transient to ensure toString() can work.
     */
    private transient Document xmlDoc;


    /**
     * 从xml字符串创建bean对象.
     *
     * @param <T>       the type parameter
     * @param xmlString the xml string
     * @param clz       the clz
     * @return the t
     */
    public static <T extends BasePayResult> T fromXML(String xmlString, Class<T> clz) {
        if (XmlConfig.fastMode) {
            try {
                BasePayResult t = clz.newInstance();
                t.setXmlString(xmlString);
                Document doc = t.getXmlDoc();
                t.loadBasicXML(doc);
                t.loadXml(doc);
                return (T) t;
            } catch (Exception e) {
                throw new SdkRuntimeException("parse xml error", e);
            }
        }
        XStream xstream = XStreamInitializer.getInstance();
        xstream.processAnnotations(clz);
        xstream.setClassLoader(BasePayResult.class.getClassLoader());
        T result = (T) xstream.fromXML(xmlString);
        result.setXmlString(xmlString);
        return result;
    }

    /**
     * 从XML文档中加载属性,供子类覆盖加载额外的属性
     *
     * @param d Document
     */
    protected abstract void loadXml(Document d);

    /**
     * 从XML文档中加载基础属性
     *
     * @param d Document
     */
    private void loadBasicXML(Document d) {
        resultCode = readXmlString(d, "result_code");
        resultMsg = readXmlString(d, "result_msg");
        insCd = readXmlString(d, "ins_cd");
        mchntCd = readXmlString(d, "mchnt_cd");
        termId = readXmlString(d, "term_id");
        randomStr = readXmlString(d, "random_str");
        sign = readXmlString(d, "sign");
    }

    protected static Integer readXmlInteger(Node d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().length() == 0) {
            return null;
        }
        return Integer.parseInt(content);
    }

    protected static String readXmlString(Node d, String tagName) {
        if (!d.hasChildNodes()) {
            return null;
        }
        NodeList childNodes = d.getChildNodes();
        for (int i = 0, j = childNodes.getLength(); i < j; i++) {
            Node node = childNodes.item(i);
            if (tagName.equals(node.getNodeName())) {
                if (!node.hasChildNodes()) {
                    return null;
                }
                return node.getFirstChild().getNodeValue();
            }
        }
        return null;
    }

    public static String readXmlString(Document d, String tagName) {
        NodeList elements = d.getElementsByTagName(tagName);
        if (elements == null || elements.getLength() == 0) {
            return null;
        }

        Node node = elements.item(0).getFirstChild();
        if (node == null) {
            return null;
        }
        return node.getNodeValue();
    }

    protected static Integer readXmlInteger(Document d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().length() == 0) {
            return null;
        }

        return Integer.parseInt(content);
    }

    protected static Long readXmlLong(Document d, String tagName) {
        String content = readXmlString(d, tagName);
        if (content == null || content.trim().length() == 0) {
            return null;
        }

        return Long.parseLong(content);
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 将bean通过保存的xml字符串转换成map.
     *
     * @return the map
     */
    public Map<String, String> toMap() {
        if (StringUtils.isBlank(this.xmlString)) {
            throw new SdkRuntimeException("xml数据有问题，请核实！");
        }

        Map<String, String> result = Maps.newHashMap();
        Document doc = this.getXmlDoc();

        try {
            NodeList list = (NodeList) XPathFactory.newInstance().newXPath()
                    .compile("/xml/*")
                    .evaluate(doc, XPathConstants.NODESET);
            int len = list.getLength();
            for (int i = 0; i < len; i++) {
                result.put(list.item(i).getNodeName(), list.item(i).getTextContent());
            }
        } catch (XPathExpressionException e) {
            throw new SdkRuntimeException("非法的xml文本内容：" + xmlString);
        }

        return result;
    }

    /**
     * 将xml字符串转换成Document对象，以便读取其元素值.
     */
    private Document getXmlDoc() {
        if (this.xmlDoc != null) {
            return this.xmlDoc;
        }
        xmlDoc = openXML(xmlString);
        return xmlDoc;
    }

    protected Document openXML(String content) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            return factory.newDocumentBuilder().parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new SdkRuntimeException("非法的xml文本内容：\n" + this.xmlString, e);
        }
    }

    /**
     * 获取xml中元素的值.
     *
     * @param path the path
     * @return the xml value
     */
    protected String getXmlValue(String... path) {
        Document doc = this.getXmlDoc();
        String expression = String.format("/%s//text()", Joiner.on("/").join(path));
        try {
            return (String) XPathFactory
                    .newInstance()
                    .newXPath()
                    .compile(expression)
                    .evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new SdkRuntimeException("未找到相应路径的文本：" + expression);
        }
    }

    /**
     * 获取xml中元素的值，作为int值返回.
     *
     * @param path the path
     * @return the xml value as int
     */
    protected Integer getXmlValueAsInt(String... path) {
        String result = this.getXmlValue(path);
        if (StringUtils.isBlank(result)) {
            return null;
        }

        return Integer.valueOf(result);
    }

    /**
     * 校验返回结果签名.
     *
     * @param checkSuccess 是否同时检查结果是否成功
     * @param config       支付配置参数
     * @throws SdkErrorException the wx pay exception
     */
    public void checkResult(SdkConfig config,boolean checkSuccess) throws SdkErrorException {
        //校验返回结果签名
        Map<String, String> map = toMap();
        if (getSign() != null && !SignUtils.checkSign(map, config.getPublicKey())) {
            this.getLogger().debug("校验结果签名失败，参数：{}", map);
            throw new SdkErrorException("参数格式校验错误！");
        }

        //校验结果是否成功
        if (checkSuccess) {
            List<String> successStrings = Lists.newArrayList("000000");
            if (!successStrings.contains(StringUtils.trimToEmpty(getResultCode()).toUpperCase())
                    || !successStrings.contains(StringUtils.trimToEmpty(getResultCode()).toUpperCase())) {
                StringBuilder errorMsg = new StringBuilder();
                if (getResultCode() != null) {
                    errorMsg.append("返回代码：").append(getResultCode());
                }
                if (getResultMsg() != null) {
                    errorMsg.append("，返回信息：").append(getResultMsg());
                }
                String fmt = String.format("\n结果业务代码异常，返回结果：%s,\n%s", map, errorMsg.toString());
                this.getLogger().error(fmt);
                throw new SdkErrorException(fmt);
            }
        }
    }

}
