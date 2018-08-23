package com.mytooltest.util;

import android.widget.ImageView;

import com.mytooltest.DTApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderWrapper {

    private static final int CACHE_SIZE = 2 * 1024 * 1024;
    private synchronized static void initImageLoader() {

        if(!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(DTApplication.getInstance())
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                    .denyCacheImageMultipleSizesInMemory()
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCacheSize(CACHE_SIZE)
                            //.writeDebugLogs()
                    .build();

            //Initialize ImageLoader with configuration
            ImageLoader.getInstance().init(config);
        }
    }

    public synchronized static void displayImage(String uri, ImageView imageView) {

        initImageLoader();
        ImageLoader.getInstance().displayImage(uri, imageView);
    }

    public synchronized static void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {
        initImageLoader();
        ImageLoader.getInstance().displayImage(uri, imageView, listener);
    }

    public synchronized static void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
        initImageLoader();
        ImageLoader.getInstance().displayImage(uri, imageView, options, listener);
    }
}
