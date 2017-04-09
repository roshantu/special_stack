package com.github.roshan.special_stack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roshan on 2017/4/9.
 */
public class HashHeap {
    private boolean isReverse = false; // default is min-heap
    private int[] array;
    private int capacity;
    private int size;
    private Map<Integer, Node> index;

    public static class Node {
        private Integer id;
        private Integer count;

        Node(Integer id, Integer count) {
            this.id = id;
            this.count = count;
        }

        Node(Node input) {
            this.id = input.id;
            this.count = input.count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public void addCount(int num) {
            this.count += num;
        }

        public void subCount(int num) {
            this.count -= num;
        }
    }

    public HashHeap(int capacity) {
        init(capacity);
    }

    public HashHeap(int capacity, boolean isReverse) {
        this.isReverse = isReverse;
        init(capacity);
    }

    private void init(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        index = new HashMap<>();
        size  = 0;
        if (!isReverse) {
            array[0] = Integer.MIN_VALUE;
        } else {
            array[0] = Integer.MAX_VALUE;
        }
    }

    public void insert(int elem) {
        if (size >= capacity) {
            throw new RuntimeException("Heap is full");
        }

        if (index.containsKey(elem)) {
            index.get(elem).addCount(1);
        } else {
            size++;
            array[size] = elem;
            index.put(elem, new Node(size, 1));
        }

        heapBottomUp(size);
    }

    public int getRoot() {
        if (size <= 0) {
            throw new RuntimeException("Heap is empty");
        }

        return array[1];
    }

    public int removeRoot() {
        if (size <=0 ) {
            throw new RuntimeException("Heap is empty");
        }

        int rootNode = getRoot();
        Node node = index.get(rootNode);
        if (node.getCount() == 1) {
            swap(1, size);
            index.remove(rootNode);
            size--;
            if (size != 0) {
                heapBottomUp(1);
            }
            index.remove(rootNode);
        } else {
            index.get(rootNode).subCount(1);
        }

        return rootNode;
    }

    public void removeNode(int elem) {
        Node node = index.get(elem);
        if (node != null) {
            if (node.getCount() == 1) {
                int position = node.getId();
                if (position != size) {
                    swap(position, size);
                    size--;
                    heapTopDown(position);
                } else {
                    size--;
                }
                index.remove(elem);
            } else {
                index.get(elem).subCount(1);
            }
        } else {
            throw new RuntimeException("remove unknown node");
        }
    }

    private void heapTopDown(int pos) {
        while (!isLeafNode(pos)) {
            int leftChild = getLeftChild(pos);
            if (!isReverse) {
                if ((leftChild < size) && (array[leftChild] > array[leftChild + 1])) {
                    leftChild = leftChild +1;
                }

                if (array[pos] <= array[leftChild]) {
                    return;
                }
            } else {
                if ((leftChild < size) && (array[leftChild] < array[leftChild + 1])) {
                    leftChild = leftChild +1;
                }

                if (array[pos] >= array[leftChild]) {
                    return;
                }
            }
            swap(pos, leftChild);
            pos = leftChild;
        }
    }



    private void heapBottomUp(int pos) {
        int current = pos;
        if (!isReverse) {
            while (array[current] < array[getParentNode(current)]) {
                swap(current, getParentNode(current));
                current = getParentNode(current);
            }
        } else {
            while (array[current] > array[getParentNode(current)]) {
                swap(current, getParentNode(current));
                current = getParentNode(current);
            }
        }
    }

    private int getParentNode(int pos) {
        return pos / 2;
    }

    private int getLeftChild(int pos) {
        return 2 * pos;
    }

    private int getRightChild(int pos) {
        return 2 * pos + 1;
    }

    private boolean isLeafNode(int pos) {
        return ((pos > size/2) && (pos <= size));
    }

    private void swap(int srcPos, int destPos) {
        int valSrc  = array[srcPos];
        int valDest = array[destPos];

        // change Id
        index.get(valSrc).setId(destPos);
        index.get(valDest).setId(srcPos);

        // swap value
        int tmp = array[srcPos];
        array[srcPos] = array[destPos];
        array[destPos] = tmp;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (size > 0) {
            stringBuilder.append("root:" + getRoot() + ";");
        }

        stringBuilder.append("array:[");
        if (size > 0) {
            for (int i = 1; i < size; i++) {
                stringBuilder.append(array[i] + ",");
            }

            stringBuilder.append(array[size]);
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
