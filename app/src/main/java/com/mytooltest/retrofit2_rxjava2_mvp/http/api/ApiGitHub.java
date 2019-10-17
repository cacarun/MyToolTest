package com.mytooltest.retrofit2_rxjava2_mvp.http.api;

import com.mytooltest.retrofit2_rxjava2_mvp.bean.ContributorBean;
import com.mytooltest.retrofit2_rxjava2_mvp.http.result.BaseResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiGitHub {

    public static final String API_URL = "https://api.github.com";

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<BaseResult<List<ContributorBean>>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<ContributorBean>> rawContributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<ContributorBean>> raw2Contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);
}

