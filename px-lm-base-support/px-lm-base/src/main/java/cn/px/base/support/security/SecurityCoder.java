/**
 * 2011-01-11
 */
package cn.px.base.support.security;

import java.security.MessageDigest;
import java.security.Security;

/**
 * 加密基类
 *
 * @author PXHLT
 * @version 1.0
 * @since 1.0
 */
public abstract class SecurityCoder {
    private static Byte ADDFLAG = 0;
    static {
        if (ADDFLAG == 0) {
            // 加入BouncyCastleProvider支持
            Security.addProvider(new BouncyCastleProvider());
            ADDFLAG = 1;
        }
    }

    public static byte[] digest(String algorithm, String data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // 执行消息摘要
        return md.digest(data.getBytes("UTF-8"));
    }
}
