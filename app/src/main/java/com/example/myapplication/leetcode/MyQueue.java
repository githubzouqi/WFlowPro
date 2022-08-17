package com.example.myapplication.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyQueue {

    private List<Integer> listQueue;
    /** Initialize your data structure here. */
    public MyQueue() {
        listQueue = new ArrayList<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        listQueue.add(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        int popObj = listQueue.get(0);
        listQueue.remove(0);
        return popObj;
    }

    /** Get the front element. */
    public int peek() {
        int peekObj = listQueue.get(0);
        return peekObj;
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        boolean isEmpty = true;
        if (listQueue != null && listQueue.size() != 0){
            isEmpty = false;
        }
        return isEmpty;
    }

}
