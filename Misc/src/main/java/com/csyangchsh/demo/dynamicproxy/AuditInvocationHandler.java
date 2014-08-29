package com.csyangchsh.demo.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 */
public class AuditInvocationHandler implements InvocationHandler{
    private Object target;

    public AuditInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        if(method.getName().equals("process")) {
            System.out.println("Logging...");
        }
        return result;
    }
}
