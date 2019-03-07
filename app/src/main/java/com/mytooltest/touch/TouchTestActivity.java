package com.mytooltest.touch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

public class TouchTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test);

    }

    public void onButtonClick(View v) {
        switch (v.getId()) {

            case R.id.btn_scroll_with_viewpager_out:
                jump(TouchScrollWithViewPagerOutActivity.class);
                break;

            case R.id.btn_scroll_with_viewpager_in:
                jump(TouchScrollWithViewPagerInActivity.class);
                break;

            case R.id.btn_viewpager_with_viewpager_in:
                jump(TouchViewPagerWithViewPagerInActivity.class);
                break;

            case R.id.btn_coordinator:
                jump(TouchCoordinatorActivity.class);
                break;
            default:
                break;

        }

    }

    public void jump(Class<? extends Activity> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }


}
