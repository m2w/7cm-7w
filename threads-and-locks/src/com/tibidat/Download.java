package com.tibidat;

import java.io.IOException;
import java.net.URL;

public class Download {
    public static void main(String[] args) throws InterruptedException {
        try {
            Downloader dl = new Downloader(new URL("http://www.google.de/humans.txt"), "test");
            dl.addListener(new ProgressListener(1));
            dl.addListener(new ProgressListener(2));
            dl.run();
            dl.join();
        } catch (IOException e) {

        }
    }
}
