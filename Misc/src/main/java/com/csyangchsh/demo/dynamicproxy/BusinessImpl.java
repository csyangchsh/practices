package com.csyangchsh.demo.dynamicproxy;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 */
public class BusinessImpl implements IBusiness {
    @Override
    public boolean process() {
        System.out.println("Processing (BusinessImpl)...");
        return true;
    }
}
