package com.mytooltest.designpatterns.proxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理 可以不用像静态代理那样为接口实现一个代理类，但最终它仍然实现了相同的功能，这其中的差别，动态代理所谓“动态”的原因
 * <p>
 * Proxy 动态产生的代理会调用 InvocationHandler 实现类，所以 InvocationHandler 是实际执行者
 * <p>
 * e.g. 动态代理服务
 * 1）卖酒 接口
 * 2）卖烟 接口
 * <p>
 * 同样是通过 Proxy.newProxyInstance() 方法，却产生了 SellWine 和 SellCigarette 两种接口的实现类代理
 * <p>
 * https://blog.csdn.net/briblue/article/details/73928350
 */
public class TestMainProxy2 {

    public static void main(String[] args) {

        // 卖酒
        MaotaiJiu maotaijiu = new MaotaiJiu();
        Wuliangye wu = new Wuliangye();

        InvocationHandler handler1 = new GuitaiA(maotaijiu);
        InvocationHandler handler2 = new GuitaiA(wu);

        SellWine dynamicProxy1 = (SellWine) Proxy.newProxyInstance(MaotaiJiu.class.getClassLoader(),
                MaotaiJiu.class.getInterfaces(), handler1);
        SellWine dynamicProxy2 = (SellWine) Proxy.newProxyInstance(Wuliangye.class.getClassLoader(),
                Wuliangye.class.getInterfaces(), handler2);

        dynamicProxy1.mainJiu();
        dynamicProxy2.mainJiu();


        // 卖烟
        Furongwang fu = new Furongwang();

        InvocationHandler handler3 = new GuitaiA(fu);

        SellCigarette dynamicProxy3 = (SellCigarette) Proxy.newProxyInstance(Furongwang.class.getClassLoader(),
                Furongwang.class.getInterfaces(), handler3);

        dynamicProxy3.sell();

        // dynamicProxy1 class name:com.sun.proxy.$Proxy0
        System.out.println("dynamicProxy1 class name:" + dynamicProxy1.getClass().getName());
        // dynamicProxy2 class name:com.sun.proxy.$Proxy0
        System.out.println("dynamicProxy2 class name:" + dynamicProxy2.getClass().getName());
        // dynamicProxy3 class name:com.sun.proxy.$Proxy1
        System.out.println("dynamicProxy3 class name:" + dynamicProxy3.getClass().getName());

    }

}
