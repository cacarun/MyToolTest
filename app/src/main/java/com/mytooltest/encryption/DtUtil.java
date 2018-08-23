package com.mytooltest.encryption;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DtUtil {

	private static final String tag = "DtUtil";

	/**
	 * Check if external storage is built-in or removable.
	 *
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@TargetApi(9)
	public static boolean isExternalStorageRemovable() {
		if (hasGingerbread()) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}
	//8
	public static boolean hasFroyo() {
		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	//9
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}
	//11
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}
	//12
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasIcecreamsandwitch() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	//16
	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}


	public static String get32MD5Str(String val) throws NoSuchAlgorithmException{

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<m.length; i++) {

			String hexStr = Integer.toHexString(m[i] & 0xff);
			if(hexStr.length() == 1) {

				sb.append("0").append(hexStr);
			}else {
				sb.append(hexStr);
			}
		}

		return sb.toString().substring(0, 16);
	}

	public static String getCreditCardPhotoAESKey(String param, String ex) {

		String key = "dingtone@123" + param + ex;

		try {
			return DtUtil.get32MD5Str(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getCreditCardPhotoAESIV(String param, String ex) {

		String iv = param + "dingtone!123" + ex;

		try {
			return DtUtil.get32MD5Str(iv);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * encrypt the plain data to String. Using AES to encrypt the plain data and
	 * base64 the cipher data into stirng.
	 * @param plainData
	 * @return
	 */
	public static String encryptCreditCardPhotoAESData(byte[] plainData, String userId,
													   String deviceId, String creditCardKey) {

		String aesKey = getCreditCardPhotoAESKey(userId, creditCardKey);
		String aesIV = getCreditCardPhotoAESIV(deviceId, creditCardKey);

		IvParameterSpec ivParameterSpec;
		ivParameterSpec = new IvParameterSpec(aesIV.getBytes());
		SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), "AES");

		try {

//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(plainData);

			String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");

			return encryptedText;

		} catch (Throwable e) {

		}

		return null;
	}
}
