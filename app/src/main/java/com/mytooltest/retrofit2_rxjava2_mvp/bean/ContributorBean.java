package com.mytooltest.retrofit2_rxjava2_mvp.bean;

public class ContributorBean {
    public final String login;
    public final int contributions;

    public ContributorBean(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
