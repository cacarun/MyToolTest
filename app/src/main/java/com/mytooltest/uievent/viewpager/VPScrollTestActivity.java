package com.mytooltest.uievent.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytooltest.R;

public class VPScrollTestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_scroll_test);

        ViewPager vp = findViewById(R.id.vp);
        vp.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                if (position == 1) {
                    View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_vp_scroll_test_scroll, null);

                    container.addView(view);
                    return view;

                } else {
                    TextView tv = new TextView(container.getContext());
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(20);
                    tv.setText("Page index = " + position);

                    container.addView(tv);
                    return tv;
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_vp) {

        }
    }


}
