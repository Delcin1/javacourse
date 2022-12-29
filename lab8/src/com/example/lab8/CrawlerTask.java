package com.example.lab8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

// Класс для сканирования
public class CrawlerTask implements Runnable {
    UrlPool pool;
    String startURL;
    static final String HREF_TAG = "<a href=\"http";

    public CrawlerTask(UrlPool pool, String url) {
        this.pool = pool;
        this.startURL = url;
    }

    @Override
    public void run() {
        while (true) {
            UrlDepthPair pair = pool.getNextPair();
            int currDepth = pair.getDepth();
            // Установка соединения с сайтом
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress(pair.getHost(), 80), 3000);
                sock.setSoTimeout(3000);
                System.out.println("Connected to " + pair.getURLString());
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                out.println("GET " + pair.getPath() + " HTTP/1.1");
                out.println("Host: " + pair.getHost());
                out.println("Connection: close");
                out.println();
                out.flush();

                // Извлечение ссылки из страницы и добавление их в пул
                String line;
                int lineLength;
                int shiftIdx;
                while ((line = in.readLine()) != null) {
                    // Проверка текущей строчки на содержание ссылки
                    boolean foundFullLink = false;
                    int idx = line.indexOf(HREF_TAG);
                    if (idx > 0) {
                        // Извлечение ссылки
                        String newUrl = "";
                        shiftIdx = idx + 9;
                        char c = line.charAt(shiftIdx);
                        lineLength = line.length();
                        while (c != '"' && shiftIdx < lineLength - 1) {
                            newUrl += c;
                            shiftIdx++;
                            c = line.charAt(shiftIdx);
                            if (c == '"' && newUrl.contains(startURL)) foundFullLink = true;
                        }
                        // Создание новой пары из найденной ссылки и ее глубины и добавление ее в пул
                        if (foundFullLink) {
                            UrlDepthPair newPair = new UrlDepthPair(newUrl, currDepth + 1);
                            pool.addPair(newPair);
                        }
                    }
                }
                sock.close();
            }
            catch (IOException e) {
            }

        }
    }
}