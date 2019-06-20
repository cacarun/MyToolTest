package com.mytooltest.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.ParcelableSpan;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.dialog.base.SuperDialog;
import com.mytooltest.util.Feedback;

import java.util.ArrayList;
import java.util.List;


public class FlipperDialog extends SuperDialog implements View.OnClickListener {

    private Activity activity;
    private Feedback feedback;

    public FlipperDialog(Activity activity, Feedback feedback) {
        super(activity, R.style.TranslucentFloatDialog);
        this.activity = activity;
        this.feedback = feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_flipper);

        initView();
    }

    private void initView() {
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.btn_share).setOnClickListener(this);

        TextView tvEarn100 = findViewById(R.id.tv_earn_100);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(activity,
                R.color.color_yellow_FB8920));
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(20, true);
        List<ParcelableSpan> spanList = new ArrayList<>();
        spanList.add(colorSpan);
        spanList.add(sizeSpan);

        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {

            this.dismiss();

            if (feedback != null) {
                feedback.onFeedback(R.id.iv_close);
            }

        } else if (id == R.id.btn_share) {

            this.dismiss();

            if (feedback != null) {
                feedback.onFeedback(R.id.btn_share);
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
