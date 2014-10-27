package com.tibidat;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock table = new ReentrantLock();
        Philosopher p1 = new Philosopher(table);
        Philosopher p2 = new Philosopher(table);
        Philosopher p3 = new Philosopher(table);
        Philosopher p4 = new Philosopher(table);
        Philosopher p5 = new Philosopher(table);
        p1.setLeft(p5);
        p1.setRight(p2);
        p2.setLeft(p1);
        p2.setRight(p3);
        p3.setLeft(p2);
        p3.setRight(p4);
        p4.setLeft(p3);
        p4.setRight(p5);
        p5.setLeft(p4);
        p5.setRight(p1);

        p1.run();
        p2.run();
        p3.run();
        p4.run();
        p5.run();
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }
}
