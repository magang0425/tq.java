2019-01-23

## 思考
1. next-key-locking(间隙锁)
    - TODO
2. 插入缓冲(insert buffer), 二次写(double write), 自适应哈希索引(adaptive hash index), 预读(read ahead)
    - TODO
2. 脏页
    - TODO
1. ACID
    - 如何保证
    - TODO
2. 什么是页, 大小是多少, 怎么分布的
2. 先修改缓冲池中的页, 再以一定的频率刷新到磁盘上
    - 如何刷线 syncFile?
    - 那就有数据丢失的可能, 如何解决
        - log??
        - TODO
2. midpoint insertion strategy
    - 5/8处
    - 为什么 TODO
    
3. Flush List
    - 作用??? TODO
    - 页时怎么被放入Fulsh List
        - 变为 脏页的时候??
        - 那为什么不会和 LRU 中的脏页 刷新回磁盘的时候冲突???
1. LRU List
    - 不刷新回磁盘??
1. 脏页即存在与 Flush  列表中, 也可能存在于 LRU 列表中(在FLU List上的页面一定在LRU List上，但是反之则不成立)
    - 理解这句话TODO
2. binlog redolog 的一致性以及顺序一致性
    - TODO
1. 脏页 delete/update vs 插入缓冲
    - TODO
2. 如果页本身发生了损坏, 重做没有意义
    - TODO
2. 数据即索引, 索引即数据
    - B+Tree
1. 数据库索引采用最左原则
    - B+Tree 聚合索引
    - **单纯的第二列 不一定有序**
    - 如果以 (a,b,c) 为索引, where a=xx order by c=xxx 无法使用索引
        - a-->b-->c 的顺序来排序
1. latch 为什么没有死锁检测
    - TODO
1. 死锁如何检测
    - TODO
    - 锁的方向 有向循环图
1. 一致性非锁定读 mvcc
    - 是否和 数据一致性 冲突
    - 还是说 只是因为 读 这个操作
1. 对一个不存在的行加锁 会怎么样
    - 锁表??
    - TODO
1. Next-key locking
    - 锁的是哪一段
    - 第一个比他小的 到 第一个比他大的?
1. 原子性并不能够完全保证一致性