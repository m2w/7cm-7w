package com.tibidat;

public class ProgressListener {

    private final String name;

    public ProgressListener(int n) {
        this.name = "PL" + n;
    }

    public void onProgress(int n) {
        System.out.println(this.name + " @ " + n);
    }
}
