package com.tibidat;

import java.util.Random;

public class OriginalPhilosopher extends Thread {
    private Chopstick left, right;
    private Random random;

    public OriginalPhilosopher(Chopstick left, Chopstick right) {
        this.left = left;
        this.right = right;
        random = new Random();
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(1);
                synchronized (left) {
                    Thread.sleep(1000);
                    synchronized (right) {
                        System.out.println("eating!");
                        Thread.sleep(1);
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("aborted");
        }
    }
}
