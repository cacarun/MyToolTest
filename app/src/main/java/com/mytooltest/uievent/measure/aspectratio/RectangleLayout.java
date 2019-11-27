package com.mytooltest.uievent.measure.aspectratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.mytooltest.R;

/**
 *
 * 自定义宽高比 - 通过继承已有的 ViewGroup 调整宽高
 *
 */
public class RectangleLayout extends RelativeLayout {

    //宽度占比
    private int width_weight = 1;
    //高度占比
    private int height_weight = 1;

    public RectangleLayout(Context context) {
        this(context, null);
    }

    public RectangleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectangleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RectangleLayout, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RectangleLayout_width_weight:
                    //默认值为1
                    width_weight = typedArray.getInt(attr, 1);
                    break;
                case R.styleable.RectangleLayout_height_weight:
                    //默认值为1
                    height_weight = typedArray.getInt(attr, 1);
                    break;
            }
        }
    }

    /**
     * 最复杂的子View位置的计算，RelativeLayout已经帮我们做好了，
     * 我们要做的只是最后一步，宽度和高度的修正（按一定的比例给值），这也是为什么继承一个现有ViewGroup的原因。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取父容器允许的宽高
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取测量的宽高
        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();

        //最终设定的宽高
        int width = 0;
        int height = 0;

        //根据MeasureSpec模式的不同，宽高取值不同
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //当都为wrap时，宽高取最大值

            /**
             * 当ViewGroup宽高的模式都设为wrap_content时，看子View占据的最大宽高，尽量满足子View。
             *
             * 例如，布局中子View横向纵向的宽高加上margin等值，共需要100dp x 120dp。而宽高比设定为2:1，
             * 那么最终宽高就应该是240dp x 120dp。
             */
            if (mWidth * height_weight >= mHeight * width_weight) {
                width = mWidth;
                height = width * height_weight / width_weight;
            } else {
                height = mHeight;
                width = height * width_weight / height_weight;
            }
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            //当都为固定值或match时，宽高取最小值，以保证不会越过设定的最大范围

            /**
             * 当ViewGroup宽高的模式都为EXACTLY时，以该ViewGroup为准，子View不够时，空着；子View太大时，显示一部分。
             * 由于ViewGroup宽高固定，但要满足一定的宽高比例，只能取在这个范围内，最大的那个矩形。
             *
             * 例如ViewGroup宽高设定为50dp x 40dp，宽高比还是2:1，
             * 而在50 x 40这个矩形中，最大的矩形应该是50 x 25的，那么最终获得的宽高不管子View大还是小，多还是少，宽高就是50dp x 25dp，之前设置的高40dp无效。
             */
            if (mWidth * height_weight <= mHeight * width_weight) {
                width = mWidth;
                height = width * height_weight / width_weight;
            } else {
                height = mHeight;
                width = height * width_weight / height_weight;
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {
            //当一项设为固定值或match时，以这条为标准

            /**
             * 当ViewGroup宽高的模式只有一个为EXACTLY时，以这个为标准。这个就不举例子了。
             */

            width = mWidth;
            height = width * height_weight / width_weight;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            //当一项设为固定值或match时，以这条为标准

            /**
             * 当ViewGroup宽高的模式只有一个为EXACTLY时，以这个为标准。这个就不举例子了。
             */

            height = mHeight;
            width = height * width_weight / height_weight;
        }

        //最终和父容器允许的宽高比较，最大不超过父容器的约束

        /**
         * 最大宽高不能超过父容器约束的宽高。
         *
         * 若按照以上三条原则计算出宽高为50 x 50，而父容器只能允许40 x 40，那么最终宽高也只能是40 x 40。
         */

        if (maxWidth < width) {
            width = maxWidth;
            height = width * height_weight / width_weight;
        }
        if (maxHeight < height) {
            height = maxHeight;
            width = height * width_weight / height_weight;
        }

        //将最终的宽高设定为容器的宽高
        setMeasuredDimension(width, height);
    }
}
