package com.csyangchsh.demo.io.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * Date: 14/8/25
 */
public class ScannerTest {

    public static void main(String[] args) {
        FileInputStream inputStream = null;
        Scanner sc = null;
        File file = new File("/Users/yang/github/practices/JavaIO/AUDJPY-2014-01.csv");
        try {
            long start = System.currentTimeMillis();
            inputStream = new FileInputStream(file);
            sc = new Scanner(inputStream, "UTF-8");
            double maxBid = 0;
            double maxAsk = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                tokenizer.nextToken();
                tokenizer.nextToken();
                double bid = Double.parseDouble(tokenizer.nextToken());
                if(bid > maxBid) {
                    maxBid = bid;
                }
                double ask = Double.parseDouble(tokenizer.nextToken());
                if(ask > maxAsk) {
                    maxAsk = ask;
                }
            }
            System.out.println(System.currentTimeMillis()-start);
            System.out.println(maxBid);
            System.out.println(maxAsk);
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
    }
}
