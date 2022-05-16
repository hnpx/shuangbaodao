/**
 * 2011-01-11
 */
package cn.px.base.support.security.coder;

import cn.px.base.support.security.SecurityCoder;

/**
 * MD加密组件
 *
 * @author PXHLT
 * @version 1.0
 * @since 1.0
 */
public abstract class MDCoder extends SecurityCoder {

    /**
     * MD2加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD2(String data) throws Exception {
        return digest("MD2", data);
    }

    /**
     * MD4加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD4(String data) throws Exception {
        return digest("MD4", data);
    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {
        return digest("MD5", data);
    }

    /**
     * Tiger加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeTiger(String data) throws Exception {
        return digest("Tiger", data);
    }

    /**
     * Whirlpool加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeWhirlpool(String data) throws Exception {
        return digest("Whirlpool", data);
    }

    /**
     * GOST3411加密
     *
     * @param data 待加密数据
     * @return String 消息摘要
     * @throws Exception
     */
    public static byte[] encodeGOST3411(String data) throws Exception {
        return digest("GOST3411", data);
    }
}
