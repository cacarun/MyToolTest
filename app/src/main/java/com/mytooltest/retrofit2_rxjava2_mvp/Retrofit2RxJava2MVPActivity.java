package com.mytooltest.retrofit2_rxjava2_mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.retrofit2_rxjava2_mvp.http.HttpManager;
import com.mytooltest.retrofit2_rxjava2_mvp.http.schedulers.SchedulerProvider;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyContract;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyModel;
import com.mytooltest.retrofit2_rxjava2_mvp.mvp.MyPresenter;

public class Retrofit2RxJava2MVPActivity extends AppCompatActivity implements View.OnClickListener, MyContract.View {


    private MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2_rxjava2_mvp);

        HttpManager.getInstance().init();

        presenter = new MyPresenter(new MyModel(), this, SchedulerProvider.getInstance());

        findViewById(R.id.btn_click).setOnClickListener(this);

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



//            // Create a very simple REST adapter which points the GitHub API.
//            Retrofit retrofit=new Retrofit.Builder()
//                    .baseUrl(GitHub.API_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            // Create an instance of our GitHub API interface.
//            GitHub gitHub = retrofit.create(GitHub.class);
//            // Create a call instance for looking up Retrofit contributors.
//            final retrofit2.Call<List<SimpleService.Contributor>> call = gitHub.contributors("square", "retrofit");
//
////            new Thread() {
////                @Override
////                public void run() {
////
////                    try {
////                        List<SimpleService.Contributor> contributors = call.execute().body();
////                        for (SimpleService.Contributor contributor : contributors) {
////                            Log.e("maoqitian", "Retrofit同步请求返回参数" + contributor.login + " (" + contributor.contributions + ")");
////                        }
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }.start();
//
//            call.enqueue(new retrofit2.Callback<List<SimpleService.Contributor>>() {
//                @Override
//                public void onResponse(retrofit2.Call<List<SimpleService.Contributor>> call, retrofit2.Response<List<SimpleService.Contributor>> response) {
//                    List<SimpleService.Contributor> body = response.body();
//                    for (SimpleService.Contributor contributor : body) {
//                        Log.e("maoqitian","Retrofit异步请求返回参数"+contributor.login + " (" + contributor.contributions + ")");
//                    }
//                }
//
//                @Override
//                public void onFailure(retrofit2.Call<List<SimpleService.Contributor>> call, Throwable t) {
//                    Log.e("maoqitian","Retrofit异步请求失败"+t.getMessage());
//                }
//            });

        }
    }


}
