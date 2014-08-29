package com.csyangchsh.demo.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 */
public class DynamicProxyTest {

    public static void main(String[] args) {
        Class[] proxyIf = {IBusiness.class};
        AuditInvocationHandler invocationHandler =
                new AuditInvocationHandler(new BusinessImpl());
        ClassLoader classLoader = DynamicProxyTest.class.getClassLoader();
        IBusiness business = (IBusiness) Proxy.newProxyInstance(classLoader,proxyIf,invocationHandler);
        business.process();
    }
}
