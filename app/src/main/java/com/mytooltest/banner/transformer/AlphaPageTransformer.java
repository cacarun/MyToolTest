package com.mytooltest.banner.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaPageTransformer extends BasePageTransformer
{
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer()
    {
    }

    public AlphaPageTransformer(float minAlpha)
    {
        this(minAlpha, NonPageTransformer.INSTANCE);
    }

    public AlphaPageTransformer(ViewPager.PageTransformer pageTransformer)
    {
        this(DEFAULT_MIN_ALPHA, pageTransformer);
    }

    public AlphaPageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer)
    {
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position)
    {
        view.setScaleX( 0.999f);//hack

        /**
         *
         * 代码非常简短，简单的介绍下，可以看到postion主要分为

             [-Infinity,-1)
             (1,+Infinity]
             [-1,1]
             这三个区间，对于前两个，拿我们的页面上目前显示的3个Page来说，前两个分别对应左右两个露出一点的Page，那么对于alpha值，只需要设置为最小值即可。

             对于[-1,1]，这个就需要详细分析了，我们这里拿：第一页->第二页这个过程来说，主要看position的变化

             第1页->第2页

             页1的postion变化为：从0到-1
             页2的postion变化为：从1到0
             第一页到第二页，实际上就是左滑，第一页到左边，第二页成为currentItem到达中间，那么对应alpha的变化应该是：

             页1到左边，对应alpha应该是：1到minAlpha
             页2到中间，成为currentItem，对应alpha应该是：minAlpha到1

         *
         *
         *
         */

        if (position < -1)
        { // [-Infinity,-1)
            view.setAlpha(mMinAlpha);
        } else if (position <= 1)
        { // [-1,1]


            /**
             *
             * position是0到-1的变化

               那么1+position就是从1到0的变化

               (1 - mMinAlpha) * (1 + position)就是1 - mMinAlpha到0的变化

               再加上一个mMinAlpha，就变为1到mMinAlpha的变化。

               其实绕来绕去就是为了实现factor是1到minAlpha的变化，具体这样的算式，每个人的思路可能不同，但是达到相同的效果即可。

               同理，页2是minAlpha到1的变化。
             *
             */

            if (position < 0) //[0，-1]
            {           //[1,min]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                //[min,1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else
        { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
