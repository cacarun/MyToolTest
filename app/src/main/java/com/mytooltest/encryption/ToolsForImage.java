package com.mytooltest.encryption;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class ToolsForImage {
	private final static String tag = "ToolsForImage";

	public static byte[] ReadStream(InputStream inStream) throws Exception {
		if(inStream == null){
			return null;
		}
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer, 0, 1024)) != -1) {
			outStream.write(buffer, 0, len);
			outStream.flush();
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static Bitmap GetCompressPicFromFile(String filePath) {
		if(filePath == null || filePath.isEmpty()){
			return null;
		}
		BitmapFactory.Options ops = new BitmapFactory.Options();
		File file = new File(filePath);
		Log.i(tag, "Credit Card Optimize, GetCompressPicFromFile...filePath=" + filePath
				+ "...file.length()=" + file.length());
		if (file.length() < 102400 ) { // 100k
			ops.inSampleSize = 1;
		} else if (file.length() < 262144) { // 256k
			ops.inSampleSize = 1;
		} else if (file.length() < 524288) { // 512k
			ops.inSampleSize = 2;
		} else if (file.length() < 2097152) { // 2M
			ops.inSampleSize = 3;
		} else if (file.length() < 6291456) { // 6M
			ops.inSampleSize = 4;
		} else {
			ops.inSampleSize = 5;
		}
		Log.i(tag, "Credit Card Optimize, GetCompressPicFromFile...ops.inSampleSize=" + ops.inSampleSize);

		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(filePath, ops);
		} catch (OutOfMemoryError err) {
			Log.e(tag, "Credit Card Optimize, GetCompressPicFromFile...ops.inSampleSize="
					+ ops.inSampleSize);
			ops.inSampleSize += 2;
			Log.e(tag,
					"Credit Card Optimize, GetCompressPicFromFile...OutOfMemoryError.1..new...ops.inSampleSize="
							+ ops.inSampleSize);
			try {
				bitmap = BitmapFactory.decodeFile(filePath, ops);
			} catch (OutOfMemoryError err2) {
				ops.inSampleSize += 2;
				Log.e(tag,
						"Credit Card Optimize, GetCompressPicFromFile...OutOfMemoryError.2..new...ops.inSampleSize="
								+ ops.inSampleSize);
				try {
					bitmap = BitmapFactory.decodeFile(filePath, ops);
				} catch (OutOfMemoryError err3) {
					Log.e(tag, "Credit Card Optimize, GetCompressPicFromFile...OutOfMemoryError.3..");
				}
			}
		}

		return bitmap;
	}

	public static byte[] bitmap2BytesWithJPEG(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		return baos.toByteArray();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	protected static int sizeOf(Bitmap data) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
			return data.getRowBytes() * data.getHeight();
		} else {
			return data.getByteCount();
		}
	}
}
