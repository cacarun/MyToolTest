package com.mytooltest.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

    public static void saveBitmapToFile(Bitmap bitmap, String picPath) {
        try {
            File f = new File(picPath);
            if (f.exists()) {
                f.delete();
            }

            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getCutBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        int src_w = bitmap.getWidth() / 2;
        int src_h = bitmap.getHeight() / 2;
        int temp_width = width / 2;
        int temp_height = height / 2;

        int start_w = src_w - temp_width;
        if (start_w < 0) {
            start_w = 0;
        }

        int start_h = src_h - temp_height;
        if (start_h < 0) {
            start_h = 0;
        }

        return Bitmap.createBitmap(bitmap, start_w, start_h, width, height);
    }

    public static void getWindowAttributes(Dialog dialog, Activity activity){
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

}
