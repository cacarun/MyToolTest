package com.mytooltest.imageloader.loader;

import android.widget.ImageView;

import com.mytooltest.DTApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by jarvis on 2018/12/24.
 */

public class ImageLoaderWrapper {

    static {
        initImageLoader();
    }

    /**
     *
     * @return 获取一个我们已经配置好的ImageLoader
     */
    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }

    private static void initImageLoader() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(DTApplication.getInstance())
                .diskCacheFileCount(40).diskCacheSize(1024 * 1024 * 20)
                .diskCache(new UnlimitedDiskCache(DTApplication.getInstance().getFilesDir()))
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(1024 * 1024 * 20)
                .defaultDisplayImageOptions(new DisplayImageOptions
                        .Builder()
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .build()).build();

        ImageLoader.getInstance().init(config);
    }


    public synchronized static void displayImage(String uri, ImageView imageView) {

        ImageLoader.getInstance().displayImage(uri, imageView);
    }

    public synchronized static void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(uri, imageView, listener);
    }

    public synchronized static void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(uri, imageView, options, listener);
    }


}
