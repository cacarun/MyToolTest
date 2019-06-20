package com.mytooltest.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mytooltest.R;
import com.mytooltest.dialog.base.SuperDialog;
import com.mytooltest.progress.view.CircleProgress;
import com.mytooltest.view.NumberRunningTextView;


public class NumberRunningDialog extends SuperDialog implements View.OnClickListener {

    private Activity activity;
    private String pointStr;
    private String dollarStr;

    private CircleProgress viewCircleProgress;
    private NumberRunningTextView tvNumberRunningPoint;
    private TextView tvPointDollar;

    public NumberRunningDialog(Activity activity, String pointStr, String dollarStr) {
        super(activity, R.style.TranslucentFloatDialog);
        this.activity = activity;
        this.pointStr = pointStr;
        this.dollarStr = dollarStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_running);
        initView();
    }

    private void initView() {
        Button btnOK = findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(this);

        TextView tvWeekGet = findViewById(R.id.tv_week_get);
        tvWeekGet.setVisibility(View.VISIBLE);

        btnOK.setText("OK");

        viewCircleProgress = findViewById(R.id.view_circle_progress);
        tvNumberRunningPoint = findViewById(R.id.tv_number_running_point);
        tvPointDollar = findViewById(R.id.tv_point_dollar);

        viewCircleProgress.setProgress(100);
        tvNumberRunningPoint.setContent(pointStr);
        tvPointDollar.setText(dollarStr);

        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_ok) {
            this.dismiss();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
