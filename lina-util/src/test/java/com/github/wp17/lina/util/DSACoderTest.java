package com.github.wp17.lina.util;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.github.wp17.lina.util.encrypt.dsa.DSACoder;
import org.junit.Test;

public class DSACoderTest {
	public static void main(String[] args) throws Exception{
		String inputString = "q";
		byte[] data = inputString.getBytes();

		Map<String, Object> keyMap = DSACoder.initKey();

		String publicKey = DSACoder.getPublicKey(keyMap);
		String privateKey = DSACoder.getPrivateKey(keyMap);

		String sign = DSACoder.sign(data, privateKey);

		System.out.println(sign.length());
		System.out.println(sign);

		boolean status = DSACoder.verify(data, publicKey, sign);

		System.err.println("状态：\r\n"+status);

		assertTrue(status);
	}
}
