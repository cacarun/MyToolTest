package com.mytooltest.designpatterns.observer;

public class ObserverImpl implements ObserverInterface {

    private String name;
    public ObserverImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void hasNewPaper() {
        System.out.println(name + "!! 有新的报纸了，请查收！");
    }
}
