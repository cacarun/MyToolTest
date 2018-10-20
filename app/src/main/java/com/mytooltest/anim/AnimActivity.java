package com.mytooltest.anim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mytooltest.R;
import com.mytooltest.anim.view.Circle;
import com.mytooltest.anim.view.CircleEvaluator;
import com.mytooltest.anim.view.CircleView;

public class AnimActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);



    }

    public void onCircle(final  View view) {
        final CircleView circleView = findViewById(R.id.view_circle);

        // ObjectAnimator.ofObject
        Circle startCircle = new Circle(168, Color.RED, 0);
        Circle middleCircle = new Circle(300, Color.GREEN, 15);
        Circle endCircle = new Circle(450, Color.BLUE, 30);
        ObjectAnimator.ofObject(circleView, "circle", new CircleEvaluator(), startCircle, middleCircle, endCircle)
                .setDuration(5000)
                .start();

        // ValueAnimator.ofObject
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CircleEvaluator(), startCircle, middleCircle, endCircle);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Circle circle = (Circle) animation.getAnimatedValue();
                circleView.setCircle(circle);
            }
        });
        valueAnimator.start();

    }

    public void onScaleWidth(final View view) {
        // 获取屏幕宽度
        final int maxWidth = getWindowManager().getDefaultDisplay().getWidth();

        // ValueAnimator  XML
//        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,
//                R.animator.value_animator);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                // 当前动画值，即为当前宽度比例值
//                int currentValue = (Integer) animator.getAnimatedValue();
//                // 根据比例更改目标view的宽度
//                view.getLayoutParams().width = maxWidth * currentValue / 100;
//                view.requestLayout();
//            }
//        });
//        valueAnimator.start();

        // ValueAnimator  JAVA
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(300);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 动画更新过程中的动画值，可以根据动画值的变化来关联对象的属性，实现属性动画
                float value = (float) animation.getAnimatedValue();
                Log.d("ValueAnimator", "动画值：" + value);
            }
        });



        // ObjectAnimator XML
        // 将xml转化为ObjectAnimator对象
//        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
//        // 将目标view进行包装
//        ViewWrapper wrapper = new ViewWrapper(view, maxWidth);
//        // 设置动画的目标对象为包装后的view
//        objectAnimator.setTarget(wrapper);
//        // 启动动画
//        objectAnimator.start();

        // ObjectAnimator JAVA
//        ObjectAnimator
//                .ofFloat(view, "rotationY", 0f, 360f)
//                .setDuration(2000)
//                .start();


        // AnimatorSet XML
        // 将目标view进行包装
//        ViewWrapper wrapper = new ViewWrapper(view, maxWidth);
//        // 将xml转化为ObjectAnimator对象
//        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set);
//        // 设置动画的目标对象为包装后的view
//        animatorSet.setTarget(wrapper);
//        // 启动动画
//        animatorSet.start();
    }


    private static class ViewWrapper {
        private View target; //目标对象
        private int maxWidth; //最长宽度值

        public ViewWrapper(View target, int maxWidth) {
            this.target = target;
            this.maxWidth = maxWidth;
        }

        public int getWidth() {
            return target.getLayoutParams().width;
        }

        public void setWidth(int widthValue) {
            //widthValue的值从100到20变化
            target.getLayoutParams().width = maxWidth * widthValue / 100;
            target.requestLayout();
        }

        public void setMarginTop(int margin) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) target.getLayoutParams();
            layoutParams.setMargins(0, margin, 0, 0);
            target.setLayoutParams(layoutParams);
        }
    }
}
