package com.mytooltest.imageloader.worker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.widget.ImageView;

import com.mytooltest.DTApplication;
import com.mytooltest.imageloader.worker.util.ImageCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FacebookHeadImageFetcher extends ImageFetcher {

	public FacebookHeadImageFetcher(Context context, int imageSize) {
		super(context, imageSize);
		// TODO Auto-generated constructor stub
	}

	private static final String TAG = "FacebookFetcher";

	private static FacebookHeadImageFetcher sImageFetcher = null;

	private static final String IMAGE_CACHE_DIR = "fbheadimags";

	public static enum Shape {
		Circle, Rectangle
	};

	private static Shape sImageShape = Shape.Circle;

	private Map<Object, Shape> mShapeMap = new ConcurrentHashMap<Object, Shape>();

	public static void setImageShape(Shape shape) {
		sImageShape = shape;
	}

	public static Shape getImageShape() {
		return sImageShape;
	}

	public static ImageFetcher getImageFetcher() {

		if (sImageFetcher == null) {
			synchronized (FacebookHeadImageFetcher.class) {
				if (sImageFetcher == null) {
					createImageFetacher(DTApplication.getInstance(), 100);
				}
			}
		}
		return sImageFetcher;
	}

	private static void createImageFetacher(Context context, int imageSize) {

		Log.d(TAG, "createImageFetacher imageSize = " + imageSize);

		if (sImageFetcher == null) {

			ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(context,
					IMAGE_CACHE_DIR);
			cacheParams.compressFormat = CompressFormat.PNG;
			cacheParams.setMemCacheSizePercent(0.05f); // Set memory cache to
														// 25% of app memory
			sImageFetcher = new FacebookHeadImageFetcher(context, imageSize);
			// The ImageFetcher takes care of loading images into our ImageView
			// children asynchronously
			// sImageFetcher.setLoadingImage(R.drawable.empty_photo);
			// sImageFetcher.setLoadingImage(R.drawable.img_head);
			sImageFetcher.setLoadingImage(null);
			sImageFetcher.addImageCache(cacheParams);
		}

	}

	public static void destroyImageFetcher() {

		if (sImageFetcher != null) {

			sImageFetcher.clearCache();
			sImageFetcher.closeCache();
			sImageFetcher.mShapeMap.clear();

			sImageFetcher = null;
		}
	}

	public static void displayImage(Object data, ImageView imageView) {

		ImageFetcher imageFetcher = getImageFetcher();
		if (imageFetcher != null) {
			imageFetcher.loadImage(data, imageView);
		}
	}

	public static void displayImage(Object data, ImageView imageView,
                                    Shape shape) {
		getImageFetcher();
		if (sImageFetcher != null) {
			ImageCache cache = sImageFetcher.getImageCache();
			if (cache == null
					|| cache.getBitmapFromMemCache(String.valueOf(data)) == null) {
				sImageFetcher.mShapeMap.put(data, shape);
			}
			
			displayImage(data, imageView);
		}
	}

	/**
	 * Override the processBitmap
	 * 
	 * @param data
	 * @return
	 */
	@Override
	protected Bitmap processBitmap(Object data) {

		Log.d(TAG, "processBitmap data=" + data);
		Bitmap bitmap = super.processBitmap(data);
		Shape shape = sImageShape;
		if (mShapeMap.containsKey(data)) {
			shape = mShapeMap.get(data);
			mShapeMap.remove(data);
		}

		return processBitmapByShape(shape, bitmap);
	}

	private Bitmap processBitmapByShape(Shape shape, Bitmap bitmap) {
		if (shape == Shape.Circle) {
//			return HeadImgMgr.getInstance().getRoundedBitmap(bitmap);
			return bitmap;
		} else if (shape == Shape.Rectangle) {
			return bitmap;
		}

		return bitmap;
	}
}
