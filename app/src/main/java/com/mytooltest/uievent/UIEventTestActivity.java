package com.mytooltest.uievent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.uievent.measure.MeasureTestActivity;
import com.mytooltest.uievent.viewpager.VPScrollTestActivity;

public class UIEventTestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_event_test);

        findViewById(R.id.btn_vp).setOnClickListener(this);
        findViewById(R.id.btn_measure).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_vp) {
            startActivity(new Intent(this, VPScrollTestActivity.class));
        } else if (id == R.id.btn_measure) {
            startActivity(new Intent(this, MeasureTestActivity.class));
        }
    }


}
