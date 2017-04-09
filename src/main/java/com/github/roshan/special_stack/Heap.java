package com.github.roshan.special_stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roshan on 2017/4/7.
 */
public class Heap {
    private boolean isReverse = false; // default is min-heap
    private int[] array;
    private int capacity;
    private int size;
    private Map<Integer, List<Integer>> elemPosition = new HashMap<>();

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
    }

    public void insert(int node) {
        if (size >= capacity) {
            throw new RuntimeException("Heap is full");
        }

        array[++size] = node;
        recordPosition(node, size);
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

        int rootNode = getRoot();
        removePosition(rootNode, 1);
        swap(1, size);
        size--;
        if(size != 0) {
            heapTopDown(1);
        }

        return array[size + 1];
    }

    public void removeNode(int node) {
        Integer position = getNodePosition(node);
        if (position != null) {
            removePosition(node, position);
            if (position != size) {
                swap(position, size);
                size--;
                heapTopDown(position);
            } else {
                size--;
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
        changeNodePosition(array[src], src, dest);
        changeNodePosition(array[dest], dest, src);
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

    private void recordPosition(Integer elem, Integer pos) {
        if (elemPosition.containsKey(elem)) {
            elemPosition.get(elem).add(pos);
        } else {
            elemPosition.put(elem, new ArrayList(){{add(pos);}});
        }
    }

    private void removePosition(Integer elem, Integer pos) {
        if (elemPosition.containsKey(elem)) {
            List<Integer> positionList = elemPosition.get(elem);
            for(Integer posNode: positionList) {
                if (posNode.equals(pos)) {
                    positionList.remove(pos);
                    break;
                }
            }
        }
    }

    private Integer getNodePosition(Integer node) {
        if (elemPosition.containsKey(node)) {
            return elemPosition.get(node).get(0);
        } else {
            return null;
        }
    }

    private void changeNodePosition(Integer srcNode, Integer srcPos, Integer destPos) {
        removePosition(srcNode, srcPos);
        recordPosition(srcNode, destPos);
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
