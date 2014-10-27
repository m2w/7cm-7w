package com.tibidat;

public class Philosopher extends Thread {
    private boolean eating;
    private String name;
    private Philosopher left;
    private Philosopher right;
    private Object table;

    public Philosopher(String name, Object table) {
        this.name = name;
        eating = false;
        this.table = table;
    }

    public void setLeft(Philosopher left) {
        this.left = left;
    }

    public void setRight(Philosopher right) {
        this.right = right;
    }

    public void run() {
        try {
            while (true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {

        }
    }

    private void think() throws InterruptedException {
        synchronized (table) {
            eating = false;
            synchronized (this) {
                System.out.println(this.name + " thinking");
                this.notifyAll();
            }
        }
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        boolean mustWait = false;
        synchronized (table) {
            if (!(left.eating || right.eating)) {
                System.out.println(this.name + " eating");
                eating = true;
            } else {
                mustWait = true;
            }
        }
        if (mustWait) {
            System.out.println(this.name + " must wait");
            synchronized (this) {
                this.wait(1000);
            }
        } else {
            Thread.sleep(1000);
        }
    }
}

