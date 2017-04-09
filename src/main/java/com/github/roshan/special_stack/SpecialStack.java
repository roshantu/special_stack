package com.github.roshan.special_stack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by roshan on 2017/4/7.
 */
public class SpecialStack extends Stack<Integer> {
    private int capacity;
    private HashHeap leftHeap;
    private HashHeap leftAuxHeap;
    private HashHeap rightHeap;
    private HashHeap rightAuxHeap;

    public SpecialStack(int capacity) {
        this.capacity = capacity;
        leftHeap  = new HashHeap(capacity, true);
        leftAuxHeap = new HashHeap(capacity);
        rightHeap = new HashHeap(capacity);
        rightAuxHeap = new HashHeap(capacity, true);
    }

    @Override
    public Integer push(Integer x) {
        if (super.isEmpty()) {
            super.push(x);
            leftHeap.insert(x);
            leftAuxHeap.insert(x);
        } else {
            super.push(x);
            if (x <= leftHeap.getRoot()) {
                leftHeap.insert(x);
                leftAuxHeap.insert(x);
            } else {
                rightHeap.insert(x);
                rightAuxHeap.insert(x);
            }

            rebuildHeap();
        }

        return x;
    }

    @Override
    public Integer pop() {
        int node = super.pop();
        if (node <= leftHeap.getRoot()) {
            leftHeap.removeNode(node);
            leftAuxHeap.removeNode(node);
        } else {
            rightHeap.removeNode(node);
            rightAuxHeap.removeNode(node);
        }

        rebuildHeap();
        return node;
    }

    public Integer top() {
        return super.peek();
    }

    public Integer max() {
        if (rightAuxHeap.getSize() > 0) {
            return rightAuxHeap.getRoot();
        } else {
            return leftHeap.getRoot();
        }
    }

    public Integer min() {
        if (leftAuxHeap.getSize() > 0) {
            return leftAuxHeap.getRoot();
        } else {
            return null;
        }
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
            moveHeapRoot(leftHeap, leftAuxHeap, rightHeap, rightAuxHeap);
        }

        while (((rightHeap.getSize() - 1) > leftHeap.getSize()) ||
                ((leftHeap.getSize() == 0) && (rightHeap.getSize() > 0))) {
            moveHeapRoot(rightHeap, rightAuxHeap, leftHeap, leftAuxHeap);
        }
    }

    private void moveHeapRoot(HashHeap src, HashHeap srcAux,
                              HashHeap dest, HashHeap destAux) {
        int removeRoot = src.removeRoot();
        srcAux.removeNode(removeRoot);
        dest.insert(removeRoot);
        destAux.insert(removeRoot);
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
            stringBuilder.append("|leftAuxHeap:" + leftAuxHeap);
            stringBuilder.append("|rightHeap:" + rightHeap);
            stringBuilder.append("|rightAuxHeap:" + rightAuxHeap);
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
