package com.mytooltest.retrofit2_rxjava2_mvp.http;


import com.mytooltest.retrofit2_rxjava2_mvp.http.api.ApiGitHub;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API初始化类
 */

public class HttpManager {

    private static HttpManager mInstance;
    private static Retrofit retrofit;

    private static volatile ApiGitHub apiGitHub;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiGitHub.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiGitHub getApiGitHub() {
        if (apiGitHub == null) {
            synchronized (ApiGitHub.class) {
                apiGitHub = retrofit.create(ApiGitHub.class);
            }
        }
        return apiGitHub;
    }


}
