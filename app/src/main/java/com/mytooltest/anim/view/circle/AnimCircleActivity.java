package com.mytooltest.anim.view.circle;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

public class AnimCircleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_circle);



    }

    public void onCircle(final  View view) {
        final CircleView circleView = findViewById(R.id.view_circle);


        // ObjectAnimator.ofObject
        Circle startCircle = new Circle(50, Color.RED, 0);
        Circle middleCircle = new Circle(150, Color.GREEN, 30);
        Circle endCircle = new Circle(200, Color.BLUE, 60);
        ObjectAnimator.ofObject(circleView, "circle", new CircleEvaluator(), startCircle, middleCircle, endCircle)
                .setDuration(5000)
                .start();

        // ValueAnimator.ofObject
//        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CircleEvaluator(), startCircle, middleCircle, endCircle);
//        valueAnimator.setDuration(5000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Circle circle = (Circle) animation.getAnimatedValue();
//                circleView.setCircle(circle);
//            }
//        });
//        valueAnimator.start();

    }


}
