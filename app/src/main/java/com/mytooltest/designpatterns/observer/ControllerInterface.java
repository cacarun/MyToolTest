package com.mytooltest.designpatterns.observer;

// 控制类：将订阅，取消订阅摘出来
public interface ControllerInterface {

    //添加订阅者
    void registerSubscriber(ObserverInterface f_subScribe);

    //取消订阅
    void removeSubscriber(ObserverInterface f_subScribe);

    //发送报纸
    void sendPaper();
}
