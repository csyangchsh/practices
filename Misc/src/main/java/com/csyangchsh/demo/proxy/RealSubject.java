package com.csyangchsh.demo.proxy;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 */
public class RealSubject implements Subject {

    @Override
    public void process() {
         System.out.println("Invoke RealSubject.process()...");
    }
}
