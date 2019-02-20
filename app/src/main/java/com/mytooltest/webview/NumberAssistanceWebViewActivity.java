package com.mytooltest.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.google.gson.JsonObject;
import com.mytooltest.webview.base.WebViewCommonActivity;
import com.mytooltest.webview.data.ClientForwardPageType;
import com.mytooltest.webview.util.GsonUtil;
import com.mytooltest.webview.util.JWTUtils;


public class NumberAssistanceWebViewActivity extends WebViewCommonActivity {

    private static final String TAG = "NumberAssistance";

    public static final String SCHEME = "js";
    public static final String AUTHOR = "dingtone";
    public static final String ARG = "arg";

    public static void launch(Context context, String title, String url) {
        if (null == context) {
            return;
        }
        Bundle webBundleHelp = new Bundle();
        webBundleHelp.putString(INTENT_TITLE, title);
        //webBundleHelp.putString(INTENT_URL, WebViewUtils.getWebViewURlByKey(WebViewUtils.POINT_TASK_URL));
        webBundleHelp.putString(INTENT_URL, url);
        Intent intent = new Intent(context, NumberAssistanceWebViewActivity.class);
        intent.putExtras(webBundleHelp);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setClientAndWebViewInteractListener(new ClientAndWebViewInteractListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                String msg = GsonUtil.parseBeanToGson(JWTUtils.getClientToJsBaseData()).toString();
                Log.i(TAG, "JWTUtils.getClientToJsBaseData()).toString() = " + JWTUtils.getClientToJsBaseData().toString());
                //传递基本user信息给H5
                clientCallJs(msg);

                //传递基本link给H5
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("businessType", "2");
                jsonObject.addProperty("linkUrl", "Hello, http://www.baidu.com");
                clientCallJs(jsonObject.toString());
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG, "onJsPrompt message = " + message);
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals(SCHEME)) {
                    if (uri.getAuthority().equals(AUTHOR)) {
                        String value = uri.getQueryParameter(ARG);
                        //js://dingtone?arg={"clientForwardPageType":"0"}
                        if (!TextUtils.isEmpty(value)) {
                            try {

                                ClientForwardPageType clientForwardPageType = GsonUtil.parseJsonStrToBean(value, ClientForwardPageType.class);

                                String id = clientForwardPageType.id;
                                Log.i(TAG, "Assistance, id=" + id + " clientForwardPageType=" + clientForwardPageType.clientForwardPageType);
                                if ("111".equals(clientForwardPageType.clientForwardPageType)) {

                                }

                            } catch (Exception e) {
                                Log.i(TAG, "onJsPrompt parse arg error " + e.getMessage());
                            }
                        }

                    }
                }
                result.cancel();
                return true;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void clientCallJs(String msg) {
        if (null == webView) {
            return;
        }
        final int version = Build.VERSION.SDK_INT;
        if (version <= 18) {
            Log.d(TAG, "Test Client call js one version=" + version);
            webView.loadUrl("javascript:androidCallJS('" + msg + "')");
        } else {
            Log.d(TAG, "Test Client call js two version=" + version);
            webView.evaluateJavascript("javascript:androidCallJS('" + msg + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, "Client call js result = " + value);
                }
            });
        }

    }


}

