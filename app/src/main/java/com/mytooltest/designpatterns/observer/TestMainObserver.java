package com.mytooltest.designpatterns.observer;

public class TestMainObserver {

    public static void main(String[] args) {

        ObserverImpl _me = new ObserverImpl("我");
        ObserverImpl _XiaoMing = new ObserverImpl("小明");

        ObserveredImpl _paper = new ObserveredImpl();

        ControllerInterface controllerImpl = new ControllerImpl(_paper);

        controllerImpl.registerSubscriber(_me);
        controllerImpl.registerSubscriber(_XiaoMing);

        // 有新报纸了
        controllerImpl.sendPaper();

        System.out.println("---------------发完报纸了------------------");
        // 小明不想订了，取消报纸
        controllerImpl.removeSubscriber(_XiaoMing);
        // 又有新报纸了 就没有小明的报纸 了
        controllerImpl.sendPaper();

    }
}
