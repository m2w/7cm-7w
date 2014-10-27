package com.tibidat;

public class DiningPhilosophers {

    public static void main(String[] args) throws InterruptedException {
        Chopstick c1 = new Chopstick(1);
        Chopstick c2 = new Chopstick(2);
        Chopstick c3 = new Chopstick(3);
        Chopstick c4 = new Chopstick(4);
        Chopstick c5 = new Chopstick(5);
        Philosopher p1 = new Philosopher(c1, c2);
        Philosopher p2 = new Philosopher(c2, c3);
        Philosopher p3 = new Philosopher(c3, c4);
        Philosopher p4 = new Philosopher(c4, c5);
        Philosopher p5 = new Philosopher(c5, c1);

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
