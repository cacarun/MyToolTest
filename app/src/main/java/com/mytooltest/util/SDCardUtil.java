package com.mytooltest.util;

import android.os.Environment;
import android.util.Log;

import com.mytooltest.DTApplication;
import com.mytooltest.encryption.DtUtil;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;


public class SDCardUtil
{
	private static final String TAG = "SDCardUtil";
	
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

	public static void testPath() {
		// SDCard
		Log.d(TAG, Environment.getExternalStorageDirectory().toString());

		// SDCard/Android/data/{package}/files
		Log.d(TAG, DTApplication.getInstance().getExternalFilesDir("").getPath());
		// SDCard/Android/data/{package}/cache
		Log.d(TAG, DTApplication.getInstance().getExternalCacheDir().getPath());

		// data/data/{package}/files
		Log.d(TAG, DTApplication.getInstance().getFilesDir().getPath());
		// data/data/{package}/cache
		Log.d(TAG, DTApplication.getInstance().getCacheDir().getPath());

		// data/data/{package}/app_cjw
		Log.d(TAG, DTApplication.getInstance().getDir("cjw", MODE_PRIVATE).getPath());
	}
}
