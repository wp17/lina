package com.github.wp17.lina.util;

import static org.junit.Assert.*;

import java.util.Map;

import com.github.wp17.lina.util.encrypt.dh.DHCoder;
import org.junit.Test;

/**
 * http://cn.jarfire.org/junit.html
 * @author user
 *
 */
public class DHCoderTest {

	@Test
	public void test() throws Exception{
		
		//生成甲方密钥
		Map<String, Object> aKeyMap = DHCoder.initKey();
		String aPrivateKey = DHCoder.getPrivateKey(aKeyMap);
		String aPublicKey = DHCoder.getPublicKey(aKeyMap);
		
		//根据甲方密钥生成乙方密钥
		Map<String, Object> bKeyMap = DHCoder.initKey(aPublicKey);
		String bPrivateKey = DHCoder.getPrivateKey(bKeyMap);
		String bPublicKey = DHCoder.getPublicKey(bKeyMap);
		
		String aput = "qbc***";
		System.out.println("信息原文："+aput);
		
		byte[] encode = DHCoder.encrypt(aput.getBytes(), aPublicKey, bPrivateKey);
		
		byte[] decode = DHCoder.decrypt(encode, bPublicKey, aPrivateKey);
		
		String bout = new String(decode);
		
		System.out.println("解密信息："+bout);
		
		assertEquals(aput, bout);
		
		String bput = "中国人名";
		System.out.println("信息原文："+bput);
		
		byte[] bencode = DHCoder.encrypt(bput.getBytes(), bPublicKey, aPrivateKey);
		
		byte[] bdecode = DHCoder.decrypt(bencode, aPublicKey, bPrivateKey);
		
		String aout = new String(bdecode);
		
		System.out.println("信息解密："+aout);
	}
}
