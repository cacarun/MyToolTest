package com.mytooltest.webview.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

import java.util.ArrayList;
import java.util.List;


public abstract class WebViewBaseAcitivity extends AppCompatActivity {
    private List<String> loadHistoryUrls = new ArrayList<String>();
    private PageStateListener pageStateListener = null;
    private static final String TAG = "WebViewBaseAcitivity";
    private FrameLayout frameLayoutWebViewParent = null;
    private ProgressBar progressBar = null;
    public WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public void initWebViewAndProgressView() {
//        initWebView();
//        initProgressView();
//        initWebViewSetting();
//    }

//    protected void initWebView() {
//        this.frameLayoutWebViewParent = getWebViewContainer();
//        FrameLayout.LayoutParams webview_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
//                .MATCH_PARENT);
//        webView = new WebView(getApplicationContext());
//        webView.setLayoutParams(webview_params);
//        this.frameLayoutWebViewParent.addView(webView);
//    }

    protected void initProgressView() {
        this.frameLayoutWebViewParent = getWebViewContainer();
        FrameLayout.LayoutParams progress_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8);
        progressBar = (ProgressBar) View.inflate(getApplicationContext(), R.layout.widget_webview_progressbar, null);
        progress_params.height = DeviceUtil.dip2px(this, 2f);
        progressBar.setLayoutParams(progress_params);
        this.frameLayoutWebViewParent.addView(progressBar);
    }

    public void loadUrl(String url, PageStateListener pageStateListener) {
        this.pageStateListener = pageStateListener;
        if (webView != null && url != null && !url.isEmpty()) {
            webView.loadUrl(url);
        } else {
            Log.d(TAG, "URL is empty");
        }
    }

    protected interface PageStateListener {
        public boolean shouldOverrideUrlLoading(WebView view, String url);

        public void onPageStarted(WebView view, String url, Bitmap favicon);

        public void onPageFinished(WebView view, String url);

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

        public void onProgressChanged(WebView view, int newProgress);

        public void onReceivedTitle(WebView view, String title);

        boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
    }

    protected void initWebViewSetting() {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);// 支持javascript
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        /*webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点*/

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
/*                if (!loadHistoryUrls.contains(url)) {
                    loadHistoryUrls.add(url);
                } else {
                    int index = loadHistoryUrls.indexOf(url);
                    if (index >= 1) {
                        loadHistoryUrls = loadHistoryUrls.subList(0, index);
                    } else {
                        loadHistoryUrls = loadHistoryUrls.subList(0, 1);
                    }
                }
                if (pageStateListener != null) {
                    boolean isShouldOverLoad = pageStateListener.shouldOverrideUrlLoading(view, url);
                    if (isShouldOverLoad) {
                        return true;
                    } else {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                }*/
                if (pageStateListener != null) {
                    return pageStateListener.shouldOverrideUrlLoading(view, url);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (pageStateListener != null) {
                    pageStateListener.onPageStarted(view, url, favicon);
                }
                if (progressBar != null && progressBar.getVisibility() == View.GONE) {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.VISIBLE);
                } else if (progressBar != null) {
                    progressBar.setProgress(0);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pageStateListener != null) {
                    pageStateListener.onPageFinished(view, url);
                }
                if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (pageStateListener != null) {
                    pageStateListener.onReceivedError(view, errorCode, description, failingUrl);
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (pageStateListener != null) {
                    pageStateListener.onProgressChanged(view, newProgress);
                }
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                    /*if (newProgress == 100) {
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        if (progressBar.getVisibility() == View.GONE) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        progressBar.setProgress(newProgress);
                    }*/
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (pageStateListener != null) {
                    pageStateListener.onReceivedTitle(view, title);
                }
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            //
            @Override
            public void onHideCustomView() {
                hideCustomView();
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (pageStateListener != null) {
                    return pageStateListener.onJsPrompt(view, url, message, defaultValue, result);
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d(TAG, "download_url=" + url);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        //initFinish = true;
        // webView.loadUrl(linkAction);
    }

    protected abstract FrameLayout getWebViewContainer();

    //protected abstract ProgressBar setProgressView();

    @Override
    protected void onDestroy() {
        if (webView != null) {
            Log.d(TAG, "onDestroy");
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        if (frameLayoutWebViewParent != null) {
            frameLayoutWebViewParent.removeAllViews();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return;
        }

/*        if (loadHistoryUrls != null && loadHistoryUrls.size() > 1){
            loadHistoryUrls = loadHistoryUrls.subList(0, loadHistoryUrls.size() - 1);
            webView.loadUrl(loadHistoryUrls.get(loadHistoryUrls.size() - 1));
        } else {
            super.onBackPressed();
        }*/
        super.onBackPressed();
    }


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

}
