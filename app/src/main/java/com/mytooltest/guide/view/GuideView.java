package com.mytooltest.guide.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.mytooltest.DTApplication;
import com.mytooltest.util.DeviceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class GuideView extends View {
    private Map<RectF, Integer> rectIntegerMap = new ConcurrentHashMap<>();
    private Map<Integer, RectF> integerRectMap = new ConcurrentHashMap<>();
    private String tag="GuideView";
    //    映射后的矩形列表
    private List<RectInfo> rectList = new ArrayList<>();

    private Bitmap mFgBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private boolean hasShowAll;
    class RectInfo{
        RectF rectF;
        OnClickListener onClickListener;
    }
    public GuideView(Context context) {
        super(context);
        init();
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initBg();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void initBg() {
        int screenWidth = DeviceUtil.getScreenWidth(DTApplication.getInstance());
        int screenHeight = DeviceUtil.getScreenHeight(DTApplication.getInstance());
        mFgBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_4444);
        mCanvas = new Canvas(mFgBitmap);
        mCanvas.drawColor(Color.parseColor("#cc000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFgBitmap != null) {
            canvas.drawBitmap(mFgBitmap, 0, 0, null);
        }
    }
    public void addRect(RectF rectF, int id, int border, int borderForHight, OnClickListener listener) {
//        计算屏幕位置
        int[] loc = new int[2];
        getLocationOnScreen(loc);
        rectF.left -= loc[0];
        rectF.top -= loc[1];
        rectF.right -= loc[0];
        rectF.bottom -= loc[1];

        RectF rect = new RectF(rectF.left - border > 10 ? rectF.left - border : 10,
                rectF.top - borderForHight > 10 ? rectF.top - borderForHight : 10,
                rectF.right + border < getWidth() ? rectF.right + border : getWidth() - 10,
                rectF.bottom + borderForHight < getHeight() ? rectF.bottom + borderForHight : getHeight() - 10);

        rectIntegerMap.put(rect, id);
        integerRectMap.put(id, rect);
        RectInfo rectInfo=new RectInfo();
        rectInfo.rectF=rect;
        rectInfo.onClickListener=listener;
        rectList.add(0, rectInfo);
        Log.i(tag, "addRect rect info" + rect.toString());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(tag, "onTouchEvent");
        if (rectList.size()==0){
            GuideContainer parent = (GuideContainer) getParent();
            parent.animOut();
            Log.i(tag, "RECT NUM IS 0");
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!hasShowAll) return true;
            for (int i = 0; i < rectList.size(); ++i) {
                RectInfo rectInfo = rectList.get(i);
                Log.i(tag, "rectList "+i+rectInfo.rectF.toString());
                if (rectInfo.rectF.contains(x, y)) {
                    if (rectInfo.onClickListener!=null){
                        boolean b = rectInfo.onClickListener.onClick();
                        GuideContainer parent = (GuideContainer) getParent();
                        parent.animOut();
                        if (b){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        GuideContainer parent = (GuideContainer) getParent();
                        parent.animOut();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public RectF getRectById(int id) {
        return integerRectMap.get(id);
    }

    public void clearRect() {
        Log.i(tag, "clearRect" + rectList.size());
        rectIntegerMap.clear();
        integerRectMap.clear();
        rectList.clear();
        initBg();
        invalidate();
    }

    public void showAllRect() {
        Log.i(tag, "showAllRect" + rectList.size());
        initBg();
        for (int i = 0; i < rectList.size(); ++i) {
            mCanvas.drawRoundRect(rectList.get(i).rectF, 10, 10, mPaint);
        }
        invalidate();
    }

    public void showRectByIndex(int i) {
        Log.i(tag, "showRectByIndex " + i);
        if (mCanvas == null) return;
        int i1 = rectList.size() - i - 1;
        if (i1 >= rectList.size() || i1 < 0) return;
        mCanvas.drawRoundRect(rectList.get(i1).rectF, 10, 10, mPaint);
        invalidate();
    }

    /**
     * 设置是否动画结束 控件是否完全展示
     *
     * @param hasShowAll
     */
    public void setHasShowAll(boolean hasShowAll) {
        this.hasShowAll = hasShowAll;
    }



    public interface OnClickListener{
        boolean onClick();
    }
}
