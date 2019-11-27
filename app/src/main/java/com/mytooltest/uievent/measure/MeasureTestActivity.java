package com.mytooltest.uievent.measure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mytooltest.R;

public class MeasureTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MeasureTestActivity";

    private TextView tvMeasureText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_test);

//        findViewById(R.id.btn_vp).setOnClickListener(this);


        testGetSize();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_vp) {

        } else if (id == R.id.btn_measure) {

        }
    }


    /**
     * 根据 View 的 LayoutParams 分情况调用 view.measure()，再通过 getMeasuredWidth 和 getMeasuredHeight 获取宽高
     *
     * 1) 如果 view 的布局是 match_parent
     *    直接放弃，根据上述表格 measure 过程，构造此种 MeasureSpec 需要知道 parentSize，即父容器的剩余空间，
     *    而这个时候我们无法知道 parentSize 的大小，所以理论上不可能测量出 View 的大小。
     *
     * 2) 如果 view 的布局是具体的数值（dp／px）
     *
     * 3) 如果 view 的布局是 wrap_content
     *
     * 4) 直接传递两个0的写法 view.measure(0,0) 适合 wrap_content
     *
     */
    private void testGetSize() {

        tvMeasureText = findViewById(R.id.tv_measure_text);

        // 对照
//        tvText.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "post getMeasuredWidth=" + tvMeasureText.getMeasuredWidth() + " getMeasuredHeight=" + tvMeasureText.getMeasuredHeight()
//                        + " getWidth=" + tvMeasureText.getWidth() + " getHeight=" + tvMeasureText.getHeight());
//            }
//        });


        // 如果 view 的布局是具体的数值（dp／px）
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(DeviceUtil.dip2px(this, 210), View.MeasureSpec.EXACTLY);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(DeviceUtil.dip2px(this, 120), View.MeasureSpec.EXACTLY);
//        tvText.measure(widthMeasureSpec,heightMeasureSpec);
//        Log.d(TAG, "measure EXACTLY getMeasuredWidth=" + tvMeasureText.getMeasuredWidth() + " getMeasuredHeight=" + tvMeasureText.getMeasuredHeight()
//                + " getWidth=" + tvMeasureText.getWidth() + " getHeight=" + tvMeasureText.getHeight());


        // 如果 view 的布局是 wrap_content
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30)-1, View.MeasureSpec.AT_MOST);
//        tvText.measure(widthMeasureSpec,heightMeasureSpec);
//        Log.d(TAG, "measure AT_MOST getMeasuredWidth=" + tvMeasureText.getMeasuredWidth() + " getMeasuredHeight=" + tvMeasureText.getMeasuredHeight()
//                + " getWidth=" + tvMeasureText.getWidth() + " getHeight=" + tvMeasureText.getHeight());


        // 直接传递两个0的写法 view.measure(0,0) 适合 wrap_content

        tvMeasureText.measure(0,0);
        Log.d(TAG, "measure 0,0 getMeasuredWidth=" + tvMeasureText.getMeasuredWidth() + " getMeasuredHeight=" + tvMeasureText.getMeasuredHeight()
                + " getWidth=" + tvMeasureText.getWidth() + " getHeight=" + tvMeasureText.getHeight());

    }

}
