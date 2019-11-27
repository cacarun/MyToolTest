package com.mytooltest.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.mytooltest.R;


public class WebViewJSBridgeTestActivity extends AppCompatActivity {

    private static final String TAG = "WebViewJSBridge";

    private static final String DEFAULT_URL = "file:///android_asset/test.html";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_jsbridge_test);

        BridgeWebView bridgeWebView = findViewById(R.id.bridge_web_view);
        bridgeWebView.registerHandler("AppBridgeSuperFn", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "CommonEvent, handler = submitFromWeb, data from web = " + data);
                function.onCallBack("CommonEvent, submitFromWeb exe, response data from Java");
            }
        });

        bridgeWebView.loadUrl(DEFAULT_URL);
    }


}

