package com.mytooltest.util;

import android.os.Environment;
import android.util.Log;

import com.mytooltest.encryption.DtUtil;

import java.io.File;


public class SDCardUtil
{
	private static final String tag = "SDCardUtil";
	
	/** 存储卡 */
	public static String SdCard = getSdCard();
	
	public static final String RootDirPathOnSdcard = SdCard + "/" + "MyToolTest" + "/";

//	public static final String MEDIA_PATH = RootDirPathOnSdcard + "photo/";
	public static final String MEDIA_PATH = RootDirPathOnSdcard;

	public static String getSdCard()
	{
		try{
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
					!DtUtil.isExternalStorageRemovable()) {
				return Environment.getExternalStorageDirectory().toString();

			} else {
				return Environment.getDataDirectory().toString();
			}
		}catch (Exception e){
			return Environment.getDataDirectory().toString();
		}
	}

	public static void createFile2SDCard() {
		File fileSupport = new File(SDCardUtil.MEDIA_PATH);
		if (!fileSupport.exists()) {
			try {
				Log.d("createFile2SDCard", "Mail Support, create support info dir");
				fileSupport.mkdirs();
			} catch (Exception e) {
				Log.e("Exception", "Mail Support, create file failed:" + e);
			}
		}
	}
}
