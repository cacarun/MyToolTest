package com.mytooltest.encryption;

import android.util.Base64;

import java.security.MessageDigest;

/**
 * 小加解密类，主要是BASE64的加解密
 * 
 * @author chen
 * 
 */
public class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		// return (new BASE64Decoder()).decodeBuffer(key);
		return Base64.decode(key, Base64.DEFAULT);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key)  {
		// return (new BASE64Encoder()).encodeBuffer(key);
		return Base64.encodeToString(key, Base64.NO_WRAP);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}
}
