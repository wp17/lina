package com.github.wp17.lina.util.encrypt.dsa;

import com.github.wp17.lina.util.encrypt.EncryptUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DSA-Digital Signature Algorithm 是Schnorr和ElGamal签名算法的变种，被美国NIST作为DSS(DigitalSignature Standard)。
 * 简单的说，这是一种更高级的验证方式，用作数字签名。不单单只有公钥、私钥，还有数字签名。
 * 私钥加密生成数字签名，公钥验证数据及签 名。如果数据和签名不匹配则认为验证失败！
 * 数字签名的作用就是校验数据在传输过程中不被修改。数字签名，是单向加密的升级！ 
 */
public abstract class DSACoder {
	
	public static final String ALGORITHM = "DSA";

	/**
	 * 默认密钥字节数
	 * 
	 * <pre>
	 * DSA 
	 * Default Keysize 1024  
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * 默认种子
	 */
	private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

	private static final String PUBLIC_KEY = "DSAPublicKey";
	private static final String PRIVATE_KEY = "DSAPrivateKey";
	
	public static String sign(byte[] data, String privateKey) throws Exception{
		byte[] keyBytes = EncryptUtils.decryptHex(privateKey);
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initSign(priKey);
		signature.update(data);
		
		return EncryptUtils.encryptHex(signature.sign());
	}
	
	/**
	 * 校验数字签名
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception{
		byte[] keyBytes = EncryptUtils.decryptHex(publicKey);
		
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		
		signature.initVerify(pubKey);
		signature.update(data);
		
		return signature.verify(EncryptUtils.decryptHex(sign));
	}
	
	/**
	 * 生成密钥对
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String seed) throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
		
		SecureRandom secureRandom = new SecureRandom(seed.getBytes());
		
		keyPairGen.initialize(KEY_SIZE, secureRandom);
		
		KeyPair keyPair = keyPairGen.genKeyPair();
		
		DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
		
		DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(PRIVATE_KEY, privateKey);
		map.put(PUBLIC_KEY, publicKey);
		
		return map;
	}
	
	public static Map<String, Object> initKey() throws Exception{
		return initKey(DEFAULT_SEED);
	}
	
	public static String getPublicKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		
		return EncryptUtils.encryptHex(key.getEncoded());
	}
	
	public static String getPrivateKey(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		
		return EncryptUtils.encryptHex(key.getEncoded());
	}

}
