package com.example.lab7;

import java.net.MalformedURLException;
import java.net.URL;

public class URLDepthPair {
    URL url;
    int depth;

    public URLDepthPair(String url, int depth) throws MalformedURLException {
        this.url = new URL(url);
        this.depth = depth;
    }

    public String toString(){
        String out = url + "\t" + depth;
        return out;
    }

    public String getHost() {
        return url.getHost();
    }

    public String getPath() {
        return url.getPath();
    }

    public int getDepth() {
        return depth;
    }

    public String getURLString() {
        return url.toString();
    }

}
