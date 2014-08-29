package com.csyangchsh.demo.proxy;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 */
public class Proxy implements Subject{

    private Subject subject;

    public Proxy() {
        this.subject = new RealSubject();
    }

    @Override
    public void process() {
        subject.process();
        System.out.println("Invoke Proxy.process()...");
    }

    public static void main(String[] args) {
        Subject subject = new Proxy();
        subject.process();
    }
}
