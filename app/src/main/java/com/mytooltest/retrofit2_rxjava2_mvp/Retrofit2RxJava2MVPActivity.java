package com.mytooltest.retrofit2_rxjava2_mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.retrofit2_rxjava2_mvp.bean.ContributorBean;
import com.mytooltest.retrofit2_rxjava2_mvp.http.HttpManager;
import com.mytooltest.retrofit2_rxjava2_mvp.http.api.ApiGitHub;
import com.mytooltest.retrofit2_rxjava2_mvp.http.schedulers.SchedulerProvider;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyContract;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyModel;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyPresenter;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2RxJava2MVPActivity extends AppCompatActivity implements View.OnClickListener, MyContract.View {


    private MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2_rxjava2_mvp);

        HttpManager.getInstance().init();

        presenter = new MyPresenter(new MyModel(), this, SchedulerProvider.getInstance());

        findViewById(R.id.btn_click).setOnClickListener(this);
        findViewById(R.id.btn_click2).setOnClickListener(this);

    }

    @Override
    public void getDataSuccess() {
        Log.e("test","getDataSuccess");
    }

    @Override
    public void getDataFail() {
        Log.e("test","getDataFail");
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_click) {

//            presenter.getList();
            presenter.getRawList();

        } else if (id == R.id.btn_click2) {

            // Create a very simple REST adapter which points the GitHub API.
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(ApiGitHub.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Create an instance of our GitHub API interface.
            ApiGitHub gitHub = retrofit.create(ApiGitHub.class);
            // Create a call instance for looking up Retrofit contributors.
            final retrofit2.Call<List<ContributorBean>> call = gitHub.raw2Contributors("square", "retrofit");

            new Thread() {
                @Override
                public void run() {

                    try {
                        List<ContributorBean> contributors = call.execute().body();
                        for (ContributorBean contributor : contributors) {
                            Log.e("test", "Retrofit同步请求返回参数" + contributor.login + " (" + contributor.contributions + ")");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();


//            final retrofit2.Call<List<ContributorBean>> call2 = call.clone();
//            call2.enqueue(new retrofit2.Callback<List<ContributorBean>>() {
//                @Override
//                public void onResponse(retrofit2.Call<List<ContributorBean>> call, retrofit2.Response<List<ContributorBean>> response) {
//                    List<ContributorBean> body = response.body();
//                    for (ContributorBean contributor : body) {
//                        Log.e("test","Retrofit异步请求返回参数"+contributor.login + " (" + contributor.contributions + ")");
//                    }
//                }
//
//                @Override
//                public void onFailure(retrofit2.Call<List<ContributorBean>> call, Throwable t) {
//                    Log.e("test","Retrofit异步请求失败"+t.getMessage());
//                }
//            });
        }
    }


}
