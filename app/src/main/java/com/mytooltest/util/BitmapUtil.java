package com.mytooltest.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;

import com.mytooltest.imageloader.loader.ImageLoaderWrapper;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;


public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    public static Bitmap scaleBitmap(Bitmap b, float scale) {
        Bitmap resizeBitmap = null;
        Matrix scaleMatrix = new Matrix();
        if (b != null && !b.isRecycled()) {
            scaleMatrix.postScale(scale, scale);
            try {
                resizeBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), scaleMatrix, false);
            } catch (Exception e) {
                return b;
            }
        }
        return resizeBitmap;
    }

    /**
     * 清除bitmap对象
     *
     * @param bitmap 目标对象
     */
    public static void destroyBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap b = bitmap;
            if (b != null && !b.isRecycled()) {
                b.recycle();
            }
            bitmap = null;
        }
    }


    /**
     * 从应用内的文件缓存中获取使用BitmapUtil.downloadBitmap()下载的图片Bitmap
     * @param url
     * 图片网络地址
     * @return
     * 缓存的图片
     */
    public static Bitmap getBitmapFromUrlIfDownloaded(String url) {
        File cachedPicFile = DiskCacheUtils.findInCache(url, ImageLoaderWrapper.getImageLoader().getDiskCache());
        if (cachedPicFile == null || !cachedPicFile.exists()) {
            return null;
        }
        Log.i(TAG, "image load form cache " + url);
        return BitmapFactory.decodeFile(cachedPicFile.getAbsolutePath());
    }

    /**
     * 下载网络图片到缓存文件夹中
     * @param imageUrl
     * 图片网络地址
     */
    public static void downloadBitmapIntoCache(final String imageUrl) {

        ImageLoaderWrapper.getImageLoader().loadImage(imageUrl, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.e(TAG, "image download failed " + s);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.i(TAG, "image download complete " + s);
            }

        });
    }

    /**
     * 从缓存中获取图片，若缓存中没有立即返回null，并启动一个异步任务去做下载
     * @param imageUrl
     * 图片网络地址
     * @return
     * 缓存中取得的Bitmap
     */
    public static Bitmap getBitmapFromCacheOrStartToDownloadIt(String imageUrl) {
        Bitmap cachedBitmap = getBitmapFromUrlIfDownloaded(imageUrl);
        if (cachedBitmap == null) {
            Log.i(TAG, "started to download image" + imageUrl);
            downloadBitmapIntoCache(imageUrl);
        }
        return cachedBitmap;
    }

}
