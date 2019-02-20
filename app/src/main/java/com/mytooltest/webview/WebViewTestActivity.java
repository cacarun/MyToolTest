package com.mytooltest.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.webview.base.WebViewCommonActivity;

public class WebViewTestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test);

        findViewById(R.id.btn_show_normal).setOnClickListener(this);
        findViewById(R.id.btn_show_js).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_show_normal) {

            WebViewCommonActivity.startActivity(this, "", "http://www.baidu.com");
        } else if (id == R.id.btn_show_js) {

            NumberAssistanceWebViewActivity.launch(this, "", "http://www.mypage.com");
        }

    }

}
