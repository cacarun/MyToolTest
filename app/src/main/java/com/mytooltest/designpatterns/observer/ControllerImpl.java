package com.mytooltest.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements ControllerInterface {

    private List<ObserverInterface> subList = new ArrayList<>();

    ObserveredImpl observeredImpl;

    public ControllerImpl(ObserveredImpl observeredImpl) {
        super();
        this.observeredImpl = observeredImpl;
    }

    @Override
    public void registerSubscriber(ObserverInterface subScribe) {
        subList.add(subScribe);
    }

    @Override
    public void removeSubscriber(ObserverInterface subScribe) {
        if (subList.indexOf(subScribe) >= 0) {
            subList.remove(subScribe);
        }
    }

    @Override
    public void sendPaper() {
        observeredImpl.sendPaper(subList);
    }
}
