package com.mytooltest.designpatterns.observer;

import java.util.List;

public class ObserveredImpl implements ObserveredInterface {

    @Override
    public void sendPaper(List<ObserverInterface> subList) {
        for (int i = 0; i < subList.size(); i++) {
            ObserverImpl observerImpl = (ObserverImpl) subList.get(i);
            System.out.println("----发报纸----"+observerImpl.getName());
            subList.get(i).hasNewPaper();
        }
    }
}
