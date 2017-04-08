package com.github.roshan.special_stack;

import org.junit.Test;

/**
 * Created by roshan on 2017/4/8.
 */
public class HeapTest {
    @Test
    public void minHeapTest() {
        Heap heap = new Heap(10000);
        System.out.println("empty heap|" + heap);
        heap.insert(3);
        heap.insert(1);
        heap.insert(2);
        heap.insert(4);
        System.out.println("after insert 3, 1, 2, 4|" + heap);
        heap.removeRoot();
        System.out.println("remove root 1|" +  heap);
        heap.removeNode(2);
        System.out.println("remove node 2|" + heap);
    }

    @Test
    public void maxHeapTest() {
        Heap heap = new Heap(10000, true);
        System.out.println("empty heap|" + heap);
        heap.insert(3);
        heap.insert(1);
        heap.insert(2);
        heap.insert(4);
        System.out.println("after insert 3, 1, 2, 4|" + heap);
        heap.removeRoot();
        System.out.println("remove root 1|" +  heap);
        heap.removeNode(1);
        System.out.println("remove node 2|" + heap);
    }
}
