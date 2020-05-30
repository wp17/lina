package com.github.wp17.lina.util.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;

/**
 * BASE64的加密解密是双向的，可以求反解。
 * MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。
 * 其中HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。
 * 单向加密的用途主要是为了校验数据在传输过程中是否被修改。
 * <p>
 * http://www.open-open.com/lib/view/open1397274257325.html
 */
public class EncryptUtils {

    /**
     * base64解密
     */
    public static byte[] decryptBASE64(String data) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Base64.decode(data, out);
        return out.toByteArray();
    }

    /**
     * base64解密
     */
    public static String decryptBASE64String(String data) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Base64.decode(data, out);
        return out.toString("UTF-8");
    }

    /**
     * 按 照RFC2045的定义，Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
     * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
     * BASE加密后产生的字节位数是8的倍数，如果不够位数以=符号填充。
     * base64加密
     */
    public static String encryptBASE64(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Base64.encode(data, out);
        return out.toString("UTF-8");
    }

    /**
     * MD5 -- message-digest algorithm 5 （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。
     * 不管文件多大，经过MD5后都能生成唯一的MD5值。
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(data);
        return digest.digest();
    }

    /**
     * 通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。
     */
    public static String MD5AndBASE64(String message) throws Exception {
        byte[] bytes = encryptMD5(message.getBytes());
        return encryptBASE64(bytes);
    }

    /**
     * 将字符串MD5编码后转化为十六进制字符串
     */
    public static String MD5AndHex(String message) throws Exception {
        byte[] bytes = encryptMD5(message.getBytes());
        return Hex.encodeHexString(bytes);
    }

    /**
     * 将字符串MD5编码后转化为十六进制字符串
     */
    public static String MD5AndHex2(String message) throws Exception {
        byte[] bytes = encryptMD5(message.getBytes());
        char[] chars = hex(bytes);
        return String.copyValueOf(chars);
    }

    /**
     * 将一个字节数组按照一定规则转换为十六进制的字符数组
     */
    public static char[] hex(byte[] data) {
        int j = data.length;
        char[] chars = new char[j * 2];
        int k = 0;
        for (byte b : data) {
            chars[k++] = hexDigits[b >>> 4 & 0xf];
            chars[k++] = hexDigits[b & 0xf];
        }
        return chars;
    }

    private static final char[] hexDigits =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void testMD5() throws Exception {
        String message = "wang鹏";

        System.out.println(MD5AndHex(message));

        System.out.println(MD5AndHex2(message));

        System.out.println(MD5AndHex2(message).equals(MD5AndHex(message)));

        System.out.println(MD5AndBASE64(message));
    }

    /**
     * SHA\SHA1\SHA-1\SHA-256\SHA-384\SHA-512
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest d = MessageDigest.getInstance("SHA");
        d.update(data);
        return d.digest();
    }

    public static String SHAAndHex(String message) throws NoSuchAlgorithmException {
        byte[] bytes = encryptSHA(message.getBytes());
        return Hex.encodeHexString(bytes);
    }

    public static String SHAAndBase64(String message) throws Exception {
        byte[] bytes = encryptSHA(message.getBytes());
        return encryptBASE64(bytes);
    }

    public static void testSHA() throws Exception {
        String message = "wang鹏";
        System.out.println(SHAAndHex(message));
        System.out.println(SHAAndBase64(message));
    }

    /**
     * 生成Hmac密钥
     * <p>
     * MAC系列算法支持表
     * 算法                   摘要长度      备注
     * HmacMD5     128      BouncyCastle实现
     * HmacSHA1    160      BouncyCastle实现
     * HmacSHA256  256      BouncyCastle实现
     * HmacSHA384  384      BouncyCastle实现
     * HmacSHA512  512      JAVA6实现
     * HmacMD2     128      BouncyCastle实现
     * HmacMD4     128      BouncyCastle实现
     * HmacSHA224  224      BouncyCastle实现
     */
    public static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    /**
     * 使用HMAC MD5算法加密
     */
    public static byte[] encryptHMACMD5(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * 将HMACMD5算法加密结果转换为十六进制字符串
     * https://www.bouncycastle.org/latest_releases.html
     */
    public static String encodeHmacMD5AndHex(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = encryptHMACMD5(data, key);
        return Hex.encodeHexString(bytes);
    }

    public static void testHmac() throws Exception {
        String message = "wang鹏";

        byte[] key1 = initHmacMD5Key();
        String result1 = encodeHmacMD5AndHex(message.getBytes(), key1);
        System.out.println(result1);

        byte[] key2 = initHmacMD5Key();
        String result2 = encodeHmacMD5AndHex(message.getBytes(), key2);
        System.out.println(result2);
    }

    /**
     * 将byte数组编码为十六进制字符串
     */
    public static String encryptHex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    /**
     * 将十六进制字符串解析为byte数组
     */
    public static byte[] decryptHex(String hexString) {
        try {
            return Hex.decodeHex(hexString);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将十六进制字符串解码
     */
    public static String decryptHexS(String hexString) {
        try {
            return new String(Hex.decodeHex(hexString));
        } catch (DecoderException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void testHex() {
        String message = "wang鹏";

        String encrypted = encryptHex(message.getBytes());
        System.out.println("十六进制编码：" + encrypted);

        String decrypted = decryptHexS(encrypted);
        System.out.println("十六进制解码：" + decrypted);

    }

    public static void main(String[] args) throws Exception {
		testMD5();
		testSHA();
		testHmac();
        testHex();
    }
}
