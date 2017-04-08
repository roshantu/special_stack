package com.github.roshan.special_stack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by roshan on 2017/4/7.
 */
public class SpecialStack extends Stack<Integer> {
    private int max = Integer.MIN_VALUE;
    private int min = Integer.MAX_VALUE;
    private int capacity;
    private Heap leftHeap;
    private Heap rightHeap;

    public SpecialStack(int capacity) {
        this.capacity = capacity;
        leftHeap  = new Heap(capacity, true);
        rightHeap = new Heap(capacity);
    }

    @Override
    public Integer push(Integer x) {
        if (super.isEmpty()) {
            leftHeap.insert(x);
            super.push(x);
            min = x;
            max = x;
        } else {
            super.push(x);
            if (x < leftHeap.getRoot()) {
                leftHeap.insert(x);
                if ((leftHeap.getSize() - 1) > rightHeap.getSize()) {
                    int removeRoot = leftHeap.removeRoot();
                    rightHeap.insert(removeRoot);
                }
            } else {
                rightHeap.insert(x);
                if ((rightHeap.getSize() - 1) > leftHeap.getSize()) {
                    int removeRoot = rightHeap.removeRoot();
                    leftHeap.insert(removeRoot);
                }
            }

            min = leftHeap.getMin();
            max = rightHeap.getMax();
        }

        return x;
    }

    @Override
    public Integer pop() {
        int node = super.pop();
        if (node <= leftHeap.getRoot()) {
            leftHeap.removeNode(node);
        } else if (node >= leftHeap.getRoot()){
            rightHeap.removeNode(node);
        }

        rebuildHeap();
        min = leftHeap.getMin();
        if (rightHeap.getSize() > 0) {
            max = rightHeap.getMax();
        } else {
            max = leftHeap.getMax();
        }

        return node;
    }

    public Integer top() {
        return super.peek();
    }

    public Integer max() {
        return max;
    }

    public Integer min() {
        return min;
    }

    public Integer median() {
        if (leftHeap.getSize() >= rightHeap.getSize()) {
            return leftHeap.getRoot();
        } else {
            return rightHeap.getRoot();
        }
    }

    private void rebuildHeap() {
        while ((leftHeap.getSize() - 1) > rightHeap.getSize()) {
            int removeRoot = leftHeap.removeRoot();
            rightHeap.insert(removeRoot);
        }

        while ((rightHeap.getSize() - 1) > leftHeap.getSize()) {
            int removeRoot = rightHeap.removeRoot();
            leftHeap.insert(removeRoot);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!super.empty()) {
            stringBuilder.append("top=" + top());
            stringBuilder.append("|min=" + min());
            stringBuilder.append("|max=" + max());
            stringBuilder.append("|median=" + median());
            stringBuilder.append("|leftHeap:" + leftHeap);
            stringBuilder.append("|rightHeap:" + rightHeap);
        }

        stringBuilder.append("|stack:[");
        if (!super.empty()) {
            for (int i = 0; i < super.size() - 1; i++) {
                stringBuilder.append(super.get(i) + ",");
            }
            stringBuilder.append(super.get(super.size() - 1));
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static void main(String [] args) {
        SpecialStack stack = new SpecialStack(10000);
        System.out.println("input：command + params\n usage:\n push 1 \n pop \n top \n min \n max \n median\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = reader.readLine();
                String[] commandArray = input.split(" ");
                if (commandArray.length > 0) {
                    String command = commandArray[0];
                    switch (command) {
                        case "push":
                            stack.push(Integer.valueOf(commandArray[1]));
                            break;
                        case "pop":
                            stack.pop();
                            break;
                        case "top":
                            System.out.println("stack top=" + stack.top());
                            break;
                        case "min":
                            System.out.println("stack min=" + stack.min());
                            break;
                        case "max":
                            System.out.println("stack max=" + stack.max());
                            break;
                        case "median":
                            System.out.println("stack median=" + stack.median());
                            break;
                        case "debug":
                            System.out.println("stack=" + stack);
                            break;
                        default:
                            System.out.println("invalid params");
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
