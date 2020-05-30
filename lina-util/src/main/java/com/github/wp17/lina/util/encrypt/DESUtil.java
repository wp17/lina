package com.github.wp17.lina.util.encrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.googlecode.protobuf.format.util.HexUtils;
import org.bouncycastle.util.encoders.Hex;

/**
 * DES-Data Encryption Standard,即数据加密算法。是IBM公司于1975年研究成功并公开发表的。
 * DES算法的入口参数有三个:Key、Data、Mode。
 * 其中 Key为8个字节共64位,是DES算法的工作密钥;
 * Data也为8个字节64位,是要被加密或被解密的数据;
 * Mode为DES的工作方式,有两种:加密 或解密。
 * DES算法把64位的明文输入块变为64位的密文输出块,它所使用的密钥也是64位。 
 * 
 * 
 * DES有很多同胞兄弟，如DESede(TripleDES)、AES、Blowfish、RC2、RC4(ARCFOUR)。
 * 只要换掉ALGORITHM换成对应的值，同时做一个代码替换SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);就可以了，此外就是密钥长度不同了。
 * @author user
 *
 */
public class DESUtil {
	private static final String ALGORITHM = "DES";
	
	private static SecretKey toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
		DESKeySpec keySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		
		SecretKey secretKey = keyFactory.generateSecret(keySpec);

		//SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);// ALGORITHM 为 DESede(TripleDES)、AES、Blowfish、RC2、RC4(ARCFOUR)时使用。
		
		return secretKey;
	}
	
	private static String initKey(String seed) throws NoSuchAlgorithmException, IOException{
		SecureRandom secureRandom = null;
		
		if (null == seed) {
			secureRandom = new SecureRandom();
		}else {
			secureRandom = new SecureRandom(seed.getBytes());
		}
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		
		keyGenerator.init(secureRandom);
		
		SecretKey secretKey = keyGenerator.generateKey();
		return EncryptUtils.encryptBASE64(secretKey.getEncoded());
	}
	
	/**
	 * 解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception{
		SecretKey secretKey = toKey(EncryptUtils.decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception{
		SecretKey secretKey = toKey(EncryptUtils.decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception {
		String message = "wwangencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencryptencrypt";
		String key = initKey("secret");
		
		System.out.println("原始信息："+message);
		System.out.println("秘钥："+key);
		
		byte[] encrypt = encrypt(message.getBytes(), key);

		String hexString = org.apache.commons.codec.binary.Hex.encodeHexString(encrypt);
		System.out.println("加密信息："+hexString);


		byte[] decrypt = Hex.decode(hexString);
		String dString = new String(decrypt(decrypt, key));
		System.out.println("解密信息："+dString);
	}

}
