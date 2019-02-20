package com.mytooltest.webview.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mytooltest.R;


public class WebViewCommonActivity extends WebViewBaseAcitivity {
    private static final String TAG = "WebViewCommonActivity";

    public static final String INTENT_TITLE = "title";
    public static final String INTENT_URL = "url";

    private FrameLayout mWebViewContainer;
    private TextView tvTitle;

    protected String urlStr;
    protected String titleStr;

    private ClientAndWebViewInteractListener clientAndWebViewInteractListener;

    public static void startActivity(Context context, String title, String url) {
        Bundle webBundleHelp = new Bundle();
        webBundleHelp.putString(INTENT_TITLE, title);
        webBundleHelp.putString(INTENT_URL, url);
        Intent intent = new Intent(context, WebViewCommonActivity.class);
        intent.putExtras(webBundleHelp);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_common);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(INTENT_TITLE)) {
                titleStr = bundle.getString(INTENT_TITLE);
            }
            if (bundle.containsKey(INTENT_URL)) {
                urlStr = bundle.getString(INTENT_URL);
            }
        }

        initView();

        // initWebViewAndProgressView();

        createWebViewAndInitProgressBar();

        loadUrl(urlStr, webViewStateListener);

    }

    private void createWebViewAndInitProgressBar() {

        webView = findViewById(R.id.webview_content);

        initProgressView();

        initWebViewSetting();
    }

    private PageStateListener webViewStateListener = new PageStateListener() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading openUrl=" + url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "onPageStarted:" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished:" + url);
            if (null != clientAndWebViewInteractListener) {
                clientAndWebViewInteractListener.onPageFinished(view, url);
            }
            if (TextUtils.isEmpty(titleStr)) {
                setTitle(view.getTitle());
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d(TAG, "onReceivedError:" + errorCode + " + " + description + " + " + failingUrl);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setProgress(newProgress * 100);
            Log.d(TAG, "onProgressChanged:" + newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.d(TAG, "onReceivedTitle:" + title);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if (null != clientAndWebViewInteractListener) {
                return clientAndWebViewInteractListener.onJsPrompt(view, url, message, defaultValue, result);
            }
            return true;
        }


    };

    @Override
    protected FrameLayout getWebViewContainer() {
        return mWebViewContainer;
    }

    private void initView() {

        mWebViewContainer = (FrameLayout) findViewById(R.id.webview_webview);

        FrameLayout flClose = (FrameLayout) findViewById(R.id.fl_close);
        flClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        FrameLayout flMore = (FrameLayout) findViewById(R.id.fl_more);
        flMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
//                // 打开本地浏览器
//                WebViewCommonActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr)));
//                // 刷新页面
//                loadUrl(urlStr, webViewStateListener);
            }
        });

        tvTitle = (TextView) findViewById(R.id.webview_title);
        setTitle(titleStr);
    }

    protected void setTitle(String title) {
        titleStr = title;
        if (TextUtils.isEmpty(titleStr)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(titleStr);

            final float textWidth = tvTitle.getPaint().measureText(titleStr);

            tvTitle.post(new Runnable() {
                @Override
                public void run() {

                    int titleWidth = tvTitle.getWidth();
                    if (titleWidth > 0 && titleWidth < textWidth) {
                        // 一行显示不下的时候设置小字体
                        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    }

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mWebViewContainer != null) {
            mWebViewContainer.removeAllViews();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // webView.goBack();
                finish();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    public void setClientAndWebViewInteractListener(ClientAndWebViewInteractListener clientAndWebViewInteractListener) {
        this.clientAndWebViewInteractListener = clientAndWebViewInteractListener;
    }

    public interface ClientAndWebViewInteractListener {
        void onPageFinished(WebView view, String url);

        boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
    }

}