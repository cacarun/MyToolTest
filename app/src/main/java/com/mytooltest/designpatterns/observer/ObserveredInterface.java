package com.mytooltest.designpatterns.observer;

import java.util.List;

// 被观察者
public interface ObserveredInterface {

    // 发送报纸
    void sendPaper(List<ObserverInterface> subList);

}
