package com.github.roshan.special_stack;

/**
 * Created by roshan on 2017/4/7.
 */
public class Heap {
    private boolean isReverse = false; // default is min-heap
    private int[] array;
    private int capacity;
    private int min;
    private int max;
    private int size;


    public Heap(int capacity) {
        init(capacity);
    }

    public Heap(int capacity, boolean isReverse) {
        this.isReverse = isReverse;
        init(capacity);
    }

    private void init(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        size  = 0;
        if (!isReverse) {
            array[0] = Integer.MIN_VALUE;
        } else {
            array[0] = Integer.MAX_VALUE;
        }
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
    }

    public void insert(int node) {
        if (size >= capacity) {
            throw new RuntimeException("Heap is full");
        }

        array[++size] = node;
        max = maxFetch(node, max);
        min = minFetch(node, min);
        heapBottomUp();
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

        swap(1, size);
        size--;
        if(size != 0) {
            heapTopDown(1);
        }

        if (!isReverse) {
            min = array[1];
        } else {
            max = array[1];
        }

        return array[size + 1];
    }


    public void removeNode(int node) {
        boolean isFetchMinMax = false;
        if (!isReverse) {
            if (node == max)  {
                isFetchMinMax = true;
                max = Integer.MIN_VALUE;
            }
        } else {
            if (node == min) {
                isFetchMinMax = true;
                min = Integer.MAX_VALUE;
            }
        }

        for(int i = 1; i <= size;) {
            if (node == array[i]) {
                swap(i, size);
                size--;
                heapTopDown(i);
                continue;
            }

            if (isFetchMinMax) {
                if (!isReverse) {
                    max = maxFetch(array[i], max);
                } else {
                    min = minFetch(array[i], min);
                }
            }

            i++;
        }

        if (!isReverse) {
            min = getRoot();
        } else {
            max = getRoot();
        }
    }

    private int maxFetch(int a, int b) {
        return a > b ? a: b;
    }

    private int minFetch(int a, int b) {
        return a < b ? a: b;
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

    private void heapBottomUp() {
        int current = size;
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

    private void swap(int src, int dest) {
        int tmp = array[src];
        array[src] = array[dest];
        array[dest] = tmp;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (size > 0) {
            stringBuilder.append("max:" + max + ";");
            stringBuilder.append("min:" + min + ";");
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
