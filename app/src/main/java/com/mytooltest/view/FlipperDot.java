package com.mytooltest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

import com.mytooltest.util.DeviceUtil;


public class FlipperDot extends ViewFlipper {

    private Context context;

    private Paint paint = new Paint();

    public FlipperDot(Context context) {
        this(context, null);
    }

    public FlipperDot(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        paint.setAntiAlias(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int width = getWidth();

        float margin = DeviceUtil.dip2px(context, 3);
        float radius = DeviceUtil.dip2px(context, 2.5f);
        float cx = width / 2 - (radius + margin) * 2 * getChildCount() / 2;
        float cy = getHeight() - DeviceUtil.dip2px(context, 20);

        cx += (margin + radius);

        canvas.save();

        for (int i = 0; i < getChildCount(); i++) {

            if (i == getDisplayedChild()) {

                paint.setColor(Color.parseColor("#999999"));
                canvas.drawCircle(cx, cy, radius, paint);

            } else {

                paint.setColor(Color.parseColor("#D8D8D8"));
                canvas.drawCircle(cx, cy, radius, paint);
            }
            cx += 2 * (radius + margin);
        }

        canvas.restore();
    }

}
