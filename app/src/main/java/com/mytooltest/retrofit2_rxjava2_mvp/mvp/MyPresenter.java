package com.mytooltest.retrofit2_rxjava2_mvp.mvp;


import android.util.Log;

import com.mytooltest.retrofit2_rxjava2_mvp.bean.ContributorBean;
import com.mytooltest.retrofit2_rxjava2_mvp.http.result.ResultTransformer;
import com.mytooltest.retrofit2_rxjava2_mvp.http.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MyPresenter {

    private MyModel model;

    private MyContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public MyPresenter(MyModel model,
                       MyContract.View view,
                       BaseSchedulerProvider schedulerProvider) {
        this.model = model;
        this.view = view;
        this.schedulerProvider = schedulerProvider;

        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }

    public void getList() {

        Disposable disposable = model.getContributorList("square", "retrofit")
                .compose(ResultTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(contributorBeans -> {

                    for (ContributorBean contributor : contributorBeans) {
                        Log.e("test","请求返回参数 "+contributor.login + " (" + contributor.contributions + ")");
                    }

                    view.getDataSuccess();
                }, throwable -> {
                    view.getDataFail();
                });

        mDisposable.add(disposable);
    }

    public void getRawList() {

        Disposable disposable = model.getRawContributorList("square", "retrofit")
                .compose(schedulerProvider.applySchedulers())
                .subscribe(contributorBeans -> {

                    for (ContributorBean contributor : contributorBeans) {
                        Log.e("test","请求返回参数 "+contributor.login + " (" + contributor.contributions + ")");
                    }

                    view.getDataSuccess();
                }, throwable -> {
                    view.getDataFail();
                });

        mDisposable.add(disposable);
    }

}
