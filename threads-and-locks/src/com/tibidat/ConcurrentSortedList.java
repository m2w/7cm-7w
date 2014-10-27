package com.tibidat;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {
    private final ReentrantLock lock = new ReentrantLock();
    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public void insert(int value) {
        Node current = head;
        lock.lock();
        Node next = current.next;
        try {
            while (true) {
                if (next == tail || next.value < value) {
                    Node node = new Node(value, current, next);
                    next.prev = node;
                    current.next = node;
                    return;
                }
                current = next;
                next = current.next;
            }
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        Node current = tail;
        int count = 0;
        lock.lock();
        try {
            while (current.prev != head) {
                ++count;
                current = current.prev;
            }
        } finally {
            lock.unlock();
        }
        return count;
    }

    private class Node {
        int value;
        Node prev;
        Node next;

        Node() {
        }

        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
