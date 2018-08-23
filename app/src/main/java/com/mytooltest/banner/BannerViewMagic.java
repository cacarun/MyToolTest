package com.mytooltest.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mytooltest.R;
import com.mytooltest.banner.transformer.ScaleInTransformer;
import com.mytooltest.util.ImageLoaderWrapper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarvis on 2018/7/31.
 */

public class BannerViewMagic extends RelativeLayout implements View.OnTouchListener, ViewPager.OnPageChangeListener {

    private static final int BANNER_CHANGE = 0;
    private static final int DELAY_MILLIS = 3000;

    private Context context;

    private ViewPager vp;
    private LinearLayout llIndication;
    private List<View> indicationList;

    private List<String> dataList;

    private boolean isLoop = true; // 自动滚动是否开启

    private int currentPos; // 当前位置

    private MyHandler mHandler;

    private class MyHandler extends Handler {

        WeakReference<Context> mWeakReference;

        public MyHandler(Context context) {
            mWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            final Context context = mWeakReference.get();
            if (context == null) {
                return;
            }

            switch (msg.what) {
                case BANNER_CHANGE:
                    //形成轮播循环
                    currentPos++;
                    vp.setCurrentItem(currentPos);
                    mHandler.sendEmptyMessageDelayed(BANNER_CHANGE, DELAY_MILLIS);
                    break;
            }

        }
    }

    public BannerViewMagic(Context context) {
        this(context, null);
    }

    public BannerViewMagic(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewMagic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        LayoutInflater.from(context).inflate(R.layout.item_banner_magic, this, true);

        initView();

    }

    private void initView() {
        vp = findViewById(R.id.vp);
        vp.setOnTouchListener(this);
        vp.addOnPageChangeListener(this);

        int margin = (int) context.getResources().getDimension(R.dimen.wallet_banner_page_margin);
        vp.setPageMargin(margin);
        vp.setOffscreenPageLimit(3);

//        vp.setPageTransformer(true, (new AlphaPageTransformer()));
        vp.setPageTransformer(true, (new ScaleInTransformer()));
//        vp.setPageTransformer(true, (new AlphaPageTransformer(new ScaleInTransformer())));


        llIndication = findViewById(R.id.ll_indication);
    }

    /**
     * 添加图片和指示器
     */
    public void initData(List<String> data) {

        dataList = data;

        vp.setAdapter(new BannerPagerAdapter(context, dataList));

        //指示器集合
        indicationList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            //初始化指示器
            ImageView iv2 = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 0, 0, 0);
            iv2.setLayoutParams(lp);
            if (i == 0) {
                iv2.setBackgroundResource(R.drawable.banner_point_on);
            } else {
                iv2.setBackgroundResource(R.drawable.banner_point_off);
            }
            indicationList.add(iv2);
            //添加到圆点布局
            llIndication.addView(iv2);
        }

        /**
         * 2147483647 / 2 = 1073741820 - 1
         * 设置ViewPager的当前项为一个比较大的数，以便一开始就可以左右循环滑动
         */
        int n = Integer.MAX_VALUE / 2 % dataList.size();
        currentPos = Integer.MAX_VALUE / 2 - n;

        vp.setCurrentItem(currentPos);

    }

    /**
     * 启动轮播
     */
    public void startBanner() {
        startBanner(true);
    }

    /**
     * 启动轮播
     */
    public void startBanner(boolean loop) {
        isLoop = loop;

        if (mHandler == null) {
            mHandler = new MyHandler(getContext());
        }

        if (isLoop) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(BANNER_CHANGE, DELAY_MILLIS);
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 结束轮播
     */
    public void stopBanner() {
        mHandler.removeCallbacksAndMessages(null);
        isLoop = false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int previousSelectPosition = position  % dataList.size();
//      tv_page.setText(previousSelectPosition+1+"/"+topImglist.size());
        currentPos = position;

        // 改变指示器变化
        bannerPointLight(position % indicationList.size());
    }

    private void bannerPointLight(int currentPoint) {
        for (int i = 0; i < indicationList.size(); i++) {
            if (currentPoint == i) {
                indicationList.get(i).setBackgroundResource(R.drawable.banner_point_on);
            } else {
                indicationList.get(i).setBackgroundResource(R.drawable.banner_point_off);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (!isLoop) {
            return false;
        }

//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                toLoop = false;
//                break;
//            case MotionEvent.ACTION_UP:
//                toLoop = true;
//                break;
//
//            default:
//                break;
//        }

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //结束轮播
                mHandler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_UP:
                //开启轮播
                mHandler.sendEmptyMessageDelayed(BANNER_CHANGE, DELAY_MILLIS);
                break;
            case MotionEvent.ACTION_CANCEL:
                //开启轮播
                mHandler.sendEmptyMessageDelayed(BANNER_CHANGE, DELAY_MILLIS);
                break;

        }

        return false;
    }

    class BannerPagerAdapter extends PagerAdapter {

        private List<String> listData;
        private Context context;
        private ImageLoader imageLoader;
        private DisplayImageOptions options;

        public BannerPagerAdapter(Context context , List<String> listData) {
            imageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.empty)
                    .showImageOnFail(R.drawable.error)
                    .resetViewBeforeLoading(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
            this.listData = listData;
            this.context = context;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 判断是否可以复用条目
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        /**
         * 销毁条目
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 初始化条目
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setTag(position);
            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    Log.d("BannerView", "image click... position: " + position);
                }
            });

            String imageUrl = listData.get(position % listData.size());

//            imageLoader.displayImage(imageUrl, imageView, options);

            ImageLoaderWrapper.displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                    // 设置图片比例
                    int vpWidth = vp.getWidth();
                    int vpHeight = 160 * vpWidth / 351;
                    LayoutParams vpLayoutParams = (RelativeLayout.LayoutParams) vp.getLayoutParams();
                    vpLayoutParams.height = vpHeight;
                    vpLayoutParams.width = vpWidth;
                    vp.setLayoutParams(vpLayoutParams);

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            container.addView(imageView);

            return imageView;
        }


    }
}
