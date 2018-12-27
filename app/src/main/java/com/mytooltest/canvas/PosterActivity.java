package com.mytooltest.canvas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;
import com.mytooltest.util.InviteUtil;
import com.mytooltest.util.LayerUtil;
import com.mytooltest.util.SDCardUtil;
import com.mytooltest.util.ToolsForImage;

public class PosterActivity extends AppCompatActivity {

    private static final String TAG = "PosterActivity";

    private static final String FILE_PATH = SDCardUtil.MEDIA_PATH + "canvas_test.jpg";

    private int defaultWidth = 720; // 720 * 1080 防止从本地 mipmap or drawable 中加载的图片被studio处理过，导致图片宽高与坐标点的比例不符

    private ImageView mIvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        mIvResult = findViewById(R.id.iv_result);


        drawNewBitmap(mIvResult, "cjw");
    }

    public void onShare(View v) {

//        InviteUtil.shareToSNS(this, "Share Image", "I have many texts. http://www.baidu.com ");
        InviteUtil.shareToSNS(this, "Share Image", "I found a beautiful image. http://www.baidu.com ", FILE_PATH);
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
        Canvas canvas = new Canvas(photoResult); // 初始化画布绘制的图像到 photoResult 上

        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取更清晰的图像采样，防抖动
        photoPaint.setFilterBitmap(true); // 过滤一下，抗剧齿
        photoPaint.setAntiAlias(true);

        Rect src = new Rect(0, 0, bgWidth, bgHeight); // 第一个 Rect 代表要绘制的 bitmap 区域
        Rect dst = new Rect(0, 0, bgWidth, bgHeight); // 第二个 Rect 代表的是要将 bitmap 绘制在屏幕的什么地方
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

        drawBitmap(canvas, photoPaint, bgHeight, bgWidth); // 画图片

        imageView.setImageBitmap(photoResult);

        ToolsForImage.saveBitmapToFile(photoResult, FILE_PATH); // 保存
    }

    /**
     * Test:
     *
     degree: 9
     .markPoint(95, 648).markPoint(366, 692)
     .markPoint(316, 988).markPoint(45, 943)
     *
     */
    private void drawBitmap(Canvas canvas, Paint photoPaint, int photoHeight, int photoWidth) {

        // 方法一：Canvas

        Bitmap source = BitmapFactory.decodeResource(this.getResources(), R.drawable.layer1);

        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        Log.d(TAG, "thumb width: " + sourceWidth + " thumb height: " + sourceHeight);

//        canvas.drawBitmap(source, left, top, photoPaint);

        int leftTopPosX = getRadio(95, photoWidth);
        int leftTopPosY = getRadio(648, photoWidth);
        int rightTopPosX = getRadio(366, photoWidth);
        int rightTopPosY = getRadio(692, photoWidth);
        int rightBottomPosX = getRadio(316, photoWidth);
        int rightBottomPosY = getRadio(988, photoWidth);
        int leftBottomPosX = getRadio(45, photoWidth);
        int leftBottomPosY = getRadio(943, photoWidth);

        // 路径剪裁
        Path path = new Path();
        path.lineTo(leftTopPosX, leftTopPosY);
        path.lineTo(rightTopPosX, rightTopPosY);
        path.lineTo(rightBottomPosX, rightBottomPosY);
        path.lineTo(leftBottomPosX, leftBottomPosY);
        canvas.clipPath(path);

        // 计算出 canvas 方形宽高
        float canvasCalWidth = LayerUtil.distance(new Point(95, 648), new Point(366, 692));
        float canvasCalHeight = LayerUtil.distance(new Point(95, 648), new Point(45, 943));
        Log.d(TAG, "thumb canvasCalWidth: " + canvasCalWidth + " canvasCalHeight: " + canvasCalHeight);
        int sourceCanvasW = getRadio(canvasCalWidth, photoWidth);
        int sourceCanvasH = getRadio(canvasCalHeight, photoWidth);
        float sourceScale =  LayerUtil.calculateFitScale(sourceWidth, sourceHeight, sourceCanvasW, sourceCanvasH);
        Log.d(TAG, "thumb sourceScale: " + sourceScale);

        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(sourceScale, sourceScale);
        matrix.postRotate(9);

        canvas.translate(leftTopPosX, leftTopPosY);

        canvas.drawBitmap(source, matrix, photoPaint);


        // 方法二： Drawable

//        Rect myRect = new Rect(getRadio(left, photoWidth), getRadio(top, photoWidth),
//                getRadio(right, photoWidth), getRadio(bottom, photoWidth));
//
//        Drawable drawable = getResources().getDrawable(R.drawable.layer1);
//        drawable.setBounds(myRect);
//        drawable.draw(canvas);
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

    private int getRadio(float pos, int photoWidth) {
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
