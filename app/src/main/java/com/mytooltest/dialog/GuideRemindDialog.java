package com.mytooltest.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.dialog.base.SuperDialog;
import com.mytooltest.util.Feedback;


public class GuideRemindDialog extends SuperDialog implements View.OnClickListener {

    private Activity activity;
    private Feedback feedback;

    public GuideRemindDialog(Activity activity, Feedback feedback) {
        super(activity, R.style.TranslucentFloatDialog);
        this.activity = activity;
        this.feedback = feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide_remind);

        initView();
    }

    private void initView() {
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.btn_get_now).setOnClickListener(this);

        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {

            this.dismiss();
        } else if (id == R.id.btn_get_now) {

            this.dismiss();
            if (feedback != null) {
                feedback.onFeedback(R.id.btn_get_now);
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
