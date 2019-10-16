package com.mytooltest.retrofit2_rxjava2_mvp.mvp;


import com.mytooltest.retrofit2_rxjava2_mvp.bean.ContributorBean;
import com.mytooltest.retrofit2_rxjava2_mvp.http.HttpManager;
import com.mytooltest.retrofit2_rxjava2_mvp.http.result.BaseResult;

import java.util.List;

import io.reactivex.Observable;

public class MyModel implements MyContract.MyModel {

    @Override
    public Observable<BaseResult<List<ContributorBean>>> getContributorList(String owner, String repo) {
        return HttpManager.getApiGitHub().contributors(owner, repo);
    }

    @Override
    public Observable<List<ContributorBean>> getRawContributorList(String owner, String repo) {
        return HttpManager.getApiGitHub().rawContributors(owner, repo);
    }
}
