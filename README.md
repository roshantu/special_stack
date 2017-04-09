### 思路

考虑到max, min, median的操作频率比push, pop高，并且性能的重要性，因此max, min, median的复杂度必须是o(1)级别。

如果所有数据是有序的，那么max, min, median能做到o(1)，但是要在push,pop操作之后，对数据进行排序，比如使用堆的排序算法，push和pop的时间复杂度也只能到log(n)*n

为了提高性能，在实现stack的push,pop操作的同时，能够支持到median的o(1)复杂度，可以额外用一个大顶堆leftHeap和一个小顶堆rightHeap将数据划分成2部分，主要算法：
同时增加leftHeap的辅助heap来获取leftHeap的最小元素
增加rightHeap的辅助heap获取rightheap的最大元素

```
1. leftHeap的根结点就是median
2. push(x): 
if (x < leftHeap.getRoot()) {
    leftHeap.insert(x);
    // 堆调整
    if ((leftHeap.size() -1 ) > rightHeap.size()) {
        int removeNode = leftHeap.remoteRoot();
        rightHeap.insert(removeNode);
    }
} else {
    rightHeap.insert(x);
    // 堆调整
    if ((rightHeap.size() - 1) > leftHeap.size()) {
       int remoteNode = rightHeap.removeRoot();
       leftHeap.insert(remoteNode);
    }
}

3. pop(x):
if (x < leftHeap.getRoot()) {
    leftHeap.remoteNode(x);
} else {
    rightHeap.remoteNode(x);
}
```

### 复杂度分析

- push(x): 插入元素leftHeap或者rightHeap会做一次或者两次堆调整，时间复杂度时o(log(n))
- pop(x):  删除元素，利用hashmap记录删除位置，时间复杂度o(log(n))
- top():  o(1)
- max():  o(1), 在push或者pop操作的同时，就记录max
- min():  o(1), 原理同max
- median: o(1), 通过leftHeap和rightHeap的机制，media就是leftHeap的root

### 程序目录说明
- Heap和SpecialStack
- test目录下为测试用例
![目录图片](./images/dir)


### 程序运行
- 编译: 安装gradle, 执行gradle clean shadowJar
- 运行: 在项目根目录，运行java -jar build/libs/special-stack-1.0.0-all.jar


### 参考
- 算法：<http://stackoverflow.com/questions/11361320/data-structure-to-find-median/>
- heap实现：<http://cs.usfca.edu/~galles/cs245/lecture/MinHeap.java.html/>
- o(1) for get max from min-heap <https://www.linkedin.com/pulse/finding-minimum-element-o1-complexity-from-maxheap-karumanchi/>


