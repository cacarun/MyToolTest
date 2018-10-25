package com.mytooltest.screenshot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

public class ScreenShotActivity extends AppCompatActivity {

    private static final String TAG = "ScreenShotActivity";

    private int defaultWidth = 720; // 720 * 1080 防止从本地 mipmap or drawable 中加载的图片被studio处理过，导致图片宽高与坐标点的比例不符

    private ImageView mIvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);

        mIvResult = findViewById(R.id.iv_result);


        drawNewBitmap(mIvResult, "cjw");
    }


    /**
     *
     * R.drawable.cover 三个矩形的坐标点
     *
     degree: 5
     .markPoint(63, 43).markPoint(495, 80)
     .markPoint(454, 552).markPoint(21, 515)

     degree: -12
     .markPoint(362, 439).markPoint(653, 372)
     .markPoint(724, 685).markPoint(434, 751)

     degree: 9
     .markPoint(95, 648).markPoint(366, 692)
     .markPoint(316, 988).markPoint(45, 943)

     * @param imageView
     * @param str
     */
    private void drawNewBitmap(ImageView imageView, String str) {

        DisplayMetrics displayMetrics = DeviceUtil.getScreenPix(this);
        int screenWidthPixels = displayMetrics.widthPixels;
        int screenHeightPixels = displayMetrics.heightPixels;
        Log.d(TAG, "screenWidthPixels: " + screenWidthPixels + " screenHeightPixels: " + screenHeightPixels
                + " density: " + displayMetrics.density + " densityDpi: " + displayMetrics.densityDpi + " scaledDensity: " + displayMetrics.scaledDensity);

        Bitmap bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.cover);
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();

        Log.d(TAG, "bgWidth: " + bgWidth + " bgHeight: " + bgHeight);

        Bitmap photoResult = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888); // 建立一个空的Bitmap
        Canvas canvas = new Canvas(photoResult); // 初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取更清晰的图像采样，防抖动
        photoPaint.setFilterBitmap(true); // 过滤一下，抗剧齿
        photoPaint.setAntiAlias(true);

        Rect src = new Rect(0, 0, bgWidth, bgHeight); // 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, bgWidth, bgHeight); // 创建一个指定的新矩形的坐标
        canvas.drawBitmap(bg, src, dst, photoPaint); // 将photo 缩放或则扩大到dst使用的填充区photoPaint

//        int begin = canvas.save(Canvas.ALL_SAVE_FLAG); canvas.restoreToCount(begin);
        canvas.save();

        TextPaint textPaint = myTextPaint();
        //  720 * 1080 位置 63, 43  旋转角度 5
        drawText(canvas, textPaint, "hello world", bgHeight, bgWidth, 63, 43, 5); // 写入文字的位置

        canvas.restore();
        canvas.save();

        //  720 * 1080 位置 362, 439  旋转角度 -12
        drawText(canvas, textPaint, "YH20hDDz", bgHeight, bgWidth, 362, 439, -12); // 写入文字的位置

        canvas.restore();
        canvas.save();

        drawBitmap(canvas, bgHeight, bgWidth,95, 648, 316, 988); // 画图片

        imageView.setImageBitmap(photoResult);

//        saveMyBitmap(this, photoResult);
    }

    private void drawBitmap(Canvas canvas, int photoHeight, int photoWidth, int left, int top, int right, int bottom) {

//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.layer1);
//        Bitmap drawCover = BitmapUtil.scaleBitmap(thumb, scale);
//        canvas.drawBitmap(drawCover, 0, 0, null);

//        float scale = LayerUtil.calculateFitScale(width, height, (int) layerRectF.width(), (int) layerRectF.height());


        Rect myRect = new Rect(getRadio(left, photoWidth), getRadio(top, photoWidth),
                getRadio(right, photoWidth), getRadio(bottom, photoWidth));

        Drawable drawable = getResources().getDrawable(R.drawable.layer1);
        drawable.setBounds(myRect);
        drawable.draw(canvas);
    }


    // 设置画笔的字体和颜色
    public TextPaint myTextPaint() {
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG); // 设置画笔
        int textSize = DeviceUtil.dip2px(this, 20);
        Log.d(TAG, "textSize: " + textSize);
        textPaint.setTextSize(textSize); // 字体大小

        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
//        textPaint.setColor(Color.argb(255, 94, 38, 18)); // 采用的颜色
        textPaint.setColor(ContextCompat.getColor(this, R.color.blue)); // 采用的颜色

        return textPaint;
    }

    // 写入文字，自动换行的方法
    public void drawText(Canvas canvas, TextPaint Paint, String textString, int photoHeight, int photoWidth, int posX, int posY, int degree) {

        int start_x = getRadio(posX, photoWidth);
        int start_y = getRadio(posY, photoWidth);
        Log.d(TAG, "start_x: " + start_x + " start_y: " + start_y);

        // width: layout的宽度，字符串超出宽度时自动换行
        // spacingmult: 相对行间距，相对字体大小，1.5f表示行间距为1.5倍的字体高度
        // spacingadd:  在基础行距上添加多少，实际行间距等于这两者的和
        int layoutWidth = photoWidth - start_x;
        StaticLayout staticLayout = new StaticLayout(textString, Paint, layoutWidth,
                Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);

        // 注意：这个layout是默认画在 Canvas 的(0,0)点的，如果需要调整位置只能在draw之前移 Canvas 的起始坐标 canvas.translate(x,y)
        //绘制的位置
        canvas.translate(start_x, start_y);
        canvas.rotate(degree);

        staticLayout.draw(canvas);
    }

    private int getRadio(int pos, int photoWidth) {
        return Math.round(pos * photoWidth * 1.0f / defaultWidth);
    }


//    public void onScreenShot(View v) {
//        // 本View是inflate加载而来，不是Activity的xml本身的
//        View view = getLayoutInflater().inflate(R.layout.item_screenshot_test,null);
//        ImageView mtv = (ImageView) view.findViewById(R.id.mIv);
//
//        ViewGroup.LayoutParams upPartLayoutParams = mtv.getLayoutParams();
//        int upPartMeasureHeight = View.MeasureSpec.makeMeasureSpec(upPartLayoutParams.height, View.MeasureSpec.EXACTLY);
//        mtv.setImageDrawable(getResources().getDrawable(R.drawable.error));
//
//        // 没有显示到界面上的view本身无大小可言，所以我们要手动指定一下
//        ScreenShotUtil.layoutView(mtv, upPartMeasureHeight, upPartMeasureHeight);
//
//        // View生成截图
//        Bitmap cacheBitmapFromView = ScreenShotUtil.getCacheBitmapFromView(mtv);
//        mIvResult.setImageBitmap(cacheBitmapFromView);
//        // 保存bitmap到sd卡
//        ScreenShotUtil.saveBitmapToSdCard(this,cacheBitmapFromView,"styleTwo");
//    }

}
