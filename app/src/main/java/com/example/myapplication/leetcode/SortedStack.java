package com.example.myapplication.leetcode;

import java.util.Stack;

/**
 * Your SortedStack object will be instantiated and called as such:
 * SortedStack obj = new SortedStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.isEmpty();
 */
public class SortedStack {

    Stack<Integer> stack = new Stack<>();
    public SortedStack() {

    }

    public void push(int val) {
        if (stack.isEmpty()){
            stack.push(val);
        }else {
            int top = stack.peek();
            if (top >= val){
                stack.push(val);
            }else {
                int temp = stack.pop();
                push(val);// 递归 push
                stack.push(temp);
            }
        }
    }

    public void pop() {
        if (!stack.isEmpty()){
            stack.pop();
        }
    }

    public int peek() {
        int peek = -1;
        if (!stack.isEmpty()){
            peek = stack.peek();
        }
        return peek;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
