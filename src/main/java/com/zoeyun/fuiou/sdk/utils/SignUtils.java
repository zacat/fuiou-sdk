package com.zoeyun.fuiou.sdk.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zoeyun.fuiou.sdk.bean.BasePayRequest;
import com.zoeyun.fuiou.sdk.bean.BasePayResult;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class SignUtils {

    private static Logger log = LoggerFactory.getLogger(SignUtils.class);
    private static final List<String> NO_SIGN_PARAMS = Lists.newArrayList("sign", "xmlString", "xmlDoc");

    /**
     * 将bean按照@XStreamAlias标识的字符串内容生成以之为key的map对象.
     *
     * @param bean 包含@XStreamAlias的xml bean对象
     * @return map对象 map
     */
    public static Map<String, String> xmlBean2Map(Object bean) {
        Map<String, String> result = Maps.newHashMap();
        List<Field> fields = new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(bean.getClass().getSuperclass().getDeclaredFields()));
        if (bean.getClass().getSuperclass().getSuperclass() == BasePayRequest.class) {
            fields.addAll(Arrays.asList(BasePayRequest.class.getDeclaredFields()));
        }

        if (bean.getClass().getSuperclass().getSuperclass() == BasePayResult.class) {
            fields.addAll(Arrays.asList(BasePayResult.class.getDeclaredFields()));
        }

        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                //if (field.get(bean) == null) {
                //    field.setAccessible(isAccessible);
                //    continue;
                //}

                if (field.isAnnotationPresent(XStreamAlias.class)) {
                    if (field.get(bean) == null) {
                        result.put(field.getAnnotation(XStreamAlias.class).value(), "");
                    } else {
                        result.put(field.getAnnotation(XStreamAlias.class).value(), field.get(bean).toString());
                    }
                } else if (!Modifier.isStatic(field.getModifiers())) {
                    //忽略掉静态成员变量
                    if (field.get(bean) == null) {
                        result.put(field.getName(), "");
                    } else {
                        result.put(field.getName(), field.get(bean).toString());
                    }
                }

                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }

        }

        return result;
    }

    /**
     * @param xmlBean       Bean里的属性如果存在XML注解，则使用其作为key，否则使用变量名
     * @param privateKey    签名Key
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return 签名字符串 string
     */
    public static String createSign(Object xmlBean, String privateKey, String[] ignoredParams) {
        Map<String, String> map = null;

        if (XmlConfig.fastMode) {
            if (xmlBean instanceof BasePayRequest) {
                map = ((BasePayRequest) xmlBean).getSignParams();
            }
        }
        if (map == null) {
            map = xmlBean2Map(xmlBean);
        }
        return createSign(map, privateKey, ignoredParams);
    }


    /**
     * @param params        参数信息
     * @param privateKey    签名Key
     * @param ignoredParams 签名时需要忽略的特殊参数
     * @return 签名字符串 string
     */
    @SneakyThrows
    public static String createSign(Map<String, String> params, String privateKey, String[] ignoredParams) {
        StringBuilder toSign = new StringBuilder();
        for (String key : new TreeMap<>(params).keySet()) {
            String value = params.get(key);
            boolean shouldSign = false;
            if (!ArrayUtils.contains(ignoredParams, key)
                    && !NO_SIGN_PARAMS.contains(key)) {
                shouldSign = true;
            }

            if (key.length() >= 8 && key.substring(0, 8).equalsIgnoreCase("reserved")) {
                shouldSign = false;
            }

            if (shouldSign) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        String preSignStr = toSign.toString();
        if (StringUtils.endsWith(preSignStr, "&")) {
            preSignStr = StringUtils.removeEnd(preSignStr, "&");
        }
        System.out.println("==============================待签名字符串==============================\r\n" + preSignStr);
        // 解密由base64编码的私钥
        byte[] bytesKey = (new BASE64Decoder()).decodeBuffer(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(bytesKey);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        java.security.Signature signature = java.security.Signature.getInstance("MD5WithRSA");
        signature.initSign(priKey);
        signature.update(preSignStr.getBytes("GBK"));
        String sign = (new BASE64Encoder()).encodeBuffer(signature.sign());
        return sign.replace("\r\n", "");
    }

    /**
     * 校验签名是否正确.
     *
     * @param xmlBean   Bean需要标记有XML注解
     * @param publicKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Object xmlBean, String publicKey, String sign) {
        return checkSign(xmlBean2Map(xmlBean), publicKey, new String[]{}, sign);
    }

    /**
     * 校验签名是否正确.
     *
     * @param xmlBean   Bean需要标记有XML注解
     * @param publicKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    public static boolean checkSign(Object xmlBean, String publicKey, String[] ignoredParams, String sign) {
        return checkSign(xmlBean2Map(xmlBean), publicKey, ignoredParams, sign);
    }

    /**
     * 校验签名是否正确.
     *
     * @param params    需要校验的参数Map
     * @param publicKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     */
    @SneakyThrows
    public static boolean checkSign(Map<String, String> params, String publicKey, String[] ignoredParams, String sign) {
        StringBuilder toSign = new StringBuilder();
        for (String key : new TreeMap<>(params).keySet()) {
            String value = params.get(key);
            boolean shouldSign = false;
            if (!ArrayUtils.contains(ignoredParams, key)
                    && !NO_SIGN_PARAMS.contains(key)) {
                shouldSign = true;
            }

            if (key.length() >= 8 && key.substring(0, 8).equalsIgnoreCase("reserved")) {
                shouldSign = false;
            }

            if (shouldSign) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        String preSignStr = toSign.toString();
        if (StringUtils.endsWith(preSignStr, "&")) {
            preSignStr = StringUtils.removeEnd(preSignStr, "&");
        }

        System.out.println("==============================待签名字符串==============================\r\n" + preSignStr);
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(preSignStr.getBytes("GBK"));
        return signature.verify(decryptBASE64(sign));
    }


    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

}
