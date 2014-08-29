package com.csyangchsh.demo.io.hugefile;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @Author csyangchsh
 * Date: 14/8/25
 *
 * [currency-pair],[timestamp],[bid-price],[ask-price]
 */
public class Test {

    public static void main(String[] args) {
        TextRowDecoder decoder = new TextRowDecoder(4, (byte)',');
        File file = new File("/Users/yang/github/practices/JavaIO/AUDJPY-2014-01.csv");
        long start = System.currentTimeMillis();
        FileReader<byte[][]> reader = FileReader.create(decoder, file);
        double maxBid = 0;
        double maxAsk = 0;
        for (List<byte[][]> chunk : reader) {
            Iterator<byte[][]> it = chunk.iterator();
            while(it.hasNext()) {
               byte[][] b = it.next();
               double bid = Double.parseDouble(new String(b[2]));
               if(bid > maxBid) {
                   maxBid = bid;
               }
               double ask = Double.parseDouble(new String(b[3]));
               if(ask > maxAsk) {
                   maxAsk = ask;
               }
            }

        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(maxBid);
        System.out.println(maxAsk);
    }

}
