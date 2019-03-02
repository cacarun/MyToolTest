package com.lib.compiler.dynamicproxy;

import java.util.Random;

public class Bird2 implements Flyable2 {

    @Override
    public void fly() {
        System.out.println("Bird is flying...");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
