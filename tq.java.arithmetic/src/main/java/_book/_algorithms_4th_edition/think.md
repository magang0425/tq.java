2018-09-12

## 思考
1. 选择排序 -- N²/2次比较, N次交换
    - (n-1) + (n-2) + ... + 2 + 1 = n(n-1)/2 ≈ N²/2
    
1. 如何大幅度提高插入排序的速度
    - 在内循环中将较大的元素都向右移动而不是总是交换两个元素???

2. 思考希尔排序
    - 既然是间隔h来实现数组的部分有序, 如何保证数组的最终有序
    
3. 理解快速排序的切分算法中, 如何将 切分点放入正确的位置??
    - 很有意思

4. 结果返回是一个增量, 逐步返回的过程
    - 那么单次返回的数据量由什么来决定呢
        - TODO
5. 平衡二叉树
    - 删除一个节点 是否会造成树的不平衡
        - 一边较长
    - 理解删除如何恢复
        - 删除之后, 找一个左子树的最大或者右子树的最小来替换, 然后递归删除
    - 思考 3种遍历 各自的特点, 以及树的还原

