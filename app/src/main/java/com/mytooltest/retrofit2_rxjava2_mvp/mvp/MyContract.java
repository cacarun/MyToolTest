package com.mytooltest.retrofit2_rxjava2_mvp.mvp;

import com.mytooltest.retrofit2_rxjava2_mvp.bean.ContributorBean;
import com.mytooltest.retrofit2_rxjava2_mvp.http.result.BaseResult;

import java.util.List;

import io.reactivex.Observable;


public class MyContract {

    public interface View {
        void getDataSuccess();
        void getDataFail();
    }

    public interface MyModel {
        public Observable<BaseResult<List<ContributorBean>>> getContributorList(String owner, String repo);
        public Observable<List<ContributorBean>> getRawContributorList(String owner, String repo);
    }

}
