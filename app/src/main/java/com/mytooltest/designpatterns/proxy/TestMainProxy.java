package com.mytooltest.designpatterns.proxy;


public class TestMainProxy {

    public static void main(String[] args) {

        Bird bird = new Bird();

        /**
         * Bird fly start...
         * Bird is flying...
         * Bird fly end...
         * Fly time = 741
         */
        BirdLogProxy p1 = new BirdLogProxy(bird);
        BirdTimeProxy p2 = new BirdTimeProxy(p1);
        p2.fly();

        /**
         * Bird fly start...
         * Bird is flying...
         * Fly time = 884
         * Bird fly end...
         */
//        BirdTimeProxy p2 = new BirdTimeProxy(bird);
//        BirdLogProxy p1 = new BirdLogProxy(p2);
//        p1.fly();
    }

}
