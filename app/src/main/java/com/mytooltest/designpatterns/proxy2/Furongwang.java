package com.mytooltest.designpatterns.proxy2;

public class Furongwang implements SellCigarette {

    @Override
    public void sell() {
        System.out.println("售卖的是正宗的芙蓉王，可以扫描条形码查证。");
    }
}
