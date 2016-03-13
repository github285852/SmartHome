package com.young.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//String key = "1q2w3e4r";
@SuppressWarnings("restriction")
public class AESUtils {
	private AESUtils() {

	}

	public static String encrypt(String strKey, String strIn) {
		try {
			SecretKeySpec skeySpec = getKey(strKey);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(
					"0102030405060708".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(strIn.getBytes());
			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(String strKey, String strIn) {
		try {
			SecretKeySpec skeySpec = getKey(strKey);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(
					"0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(strIn);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
		return skeySpec;
	}
}
