package com.github.wp17.lina.util.encrypt;

import org.apache.commons.codec.binary.Hex;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * PBE——Password-based encryption（基于密码加密）。
 * 其特点在于口令由用户自己掌管，不借助任何物理媒体；
 * 采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。
 * 是一种简便的加密方式。
 * @author user
 *
 */
public class PBEUtil {
	/**
	 * 支持以下任意一种算法
	 * 
	 * <pre>
	 * PBEWithMD5AndDES 
	 * PBEWithMD5AndTripleDES 
	 * PBEWithSHA1AndDESede
	 * PBEWithSHA1AndRC2_40
	 * </pre>
	 */
	private static final String ALGORITHM = "PBEWithMD5AndDES";
	private static final byte[] salt = initSalt();
	private static final int iterationCount = 3;
	
	private static byte[] initSalt(){
		byte[] salt = new byte[8];
		Random r = new Random();
		r.nextBytes(salt);
		return salt;
	}
	
	private static Key toKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException{
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
		
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		
		return keyFactory.generateSecret(keySpec);
	}
	
	/**
	 * 加密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception{
		Key key = toKey(password);
		
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount);
		
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
		
//		cipher.update(data);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密
	 * @param data
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception{
		Key key = toKey(password);
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		
		cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
		
//		cipher.update(data);
		
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws Exception {
		String message = "wang鹏";
		String password = "password";
		System.out.println("原始消息："+message);
		
		byte[] encrypt = encrypt(message.getBytes(), password, salt);
		String eHex = Hex.encodeHexString(encrypt);
		System.out.println("加密之后："+eHex);
		
		byte[] bytes = Hex.decodeHex(eHex);
		byte[] decrypt = decrypt(bytes, password, salt);
		System.out.println("解密之后："+new String(decrypt));
		
	}
}
