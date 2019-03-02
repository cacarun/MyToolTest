package com.lib.compiler.dynamicproxy;

import java.lang.reflect.Method;

/**
 * InvocationHandler 接口，用于处理自定义逻辑
 * （主要是为了增加控制的灵活性，将代理的处理逻辑也抽离出来，e.g. 这里的例子是打印方法的执行时间）。
 */
public interface MInvocationHandler {

    /**
     * @param proxy    动态生成的代理类，这里是TimeProxy
     * @param method   传入接口中的所有Method对象
     * @param args     当前method方法中的参数
     */
    void invoke(Object proxy, Method method, Object[] args);
}
