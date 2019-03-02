package com.lib.compiler;

import com.lib.compiler.dynamicproxy.Bird2;
import com.lib.compiler.dynamicproxy.Flyable2;
import com.lib.compiler.dynamicproxy.MyInvocationHandler;

/**
 * 手动实现动态代理
 * https://juejin.im/post/5a99048a6fb9a028d5668e62
 */
public class TestMain {

    public static void main(String[] args) throws Exception {

        Flyable2 flyable2 = (Flyable2) MyProxy.newProxyInstance(Flyable2.class, new MyInvocationHandler(new Bird2()));
        flyable2.fly();

        System.out.println(flyable2.getClass().getName());
    }

}
