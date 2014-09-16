package com.csyangchsh.demo.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @author csyangchsh
 * Date: 14/9/9
 */
public class Example1NoNIO {

    private static final int NUM_INTS = 1000000;

    public static void main(String[] args) {
        File file = new File("./test.dat");

        try {
            long start = System.currentTimeMillis();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            Random random = new Random();
            int i = 0;
            while (i < NUM_INTS) {
                byte[] ba = int2bytes(random.nextInt());
                out.write(ba);
                i++;
            }
            out.close();
            i = 0;
            while (i < NUM_INTS) {
                byte[] ba = new byte[4];
                in.read(ba);
                int j = bytes2int(ba);
                i++;
            }
            in.close();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static int bytes2int(byte[] val) {
       int res = ByteBuffer.wrap(val).getInt();
        return  res;
    }

   protected static byte[] int2bytes(int val) {
       ByteBuffer buffer = ByteBuffer.allocate(4);
       buffer.putInt(val);
       return buffer.array();
   }
}
