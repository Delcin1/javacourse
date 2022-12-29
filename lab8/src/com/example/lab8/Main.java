package com.example.lab8;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Enter url and depth: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] ss = s.split(" ");
        if (ss.length != 3) {
            System.out.println("usage: java Crawler <URL> <maximum_depth> <number_of_threads>");
            return;
        }
        String startURL = ss[0];
        int maxDepth = Integer.parseInt(ss[1]);
        int numThreads = Integer.parseInt(ss[2]);

        UrlPool pool = new UrlPool(maxDepth);
        UrlDepthPair firstPair = new UrlDepthPair(startURL, 0);
        pool.addPair(firstPair);

        for (int i = 0; i < numThreads; i++) {
            CrawlerTask c = new CrawlerTask(pool, startURL);
            Thread t = new Thread(c);
            t.start();
        }

        while (pool.getWaitCount() != numThreads) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.out.println("Thread is interrupted");
            }
        }
        LinkedList<UrlDepthPair> foundUrls = pool.getSeenUrls();
        for (UrlDepthPair pair : foundUrls) {
            System.out.println(pair.toString());
        }
        System.exit(0);
    }
}