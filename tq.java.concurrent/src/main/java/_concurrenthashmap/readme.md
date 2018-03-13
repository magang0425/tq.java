2018-03-01
## Concurrenthashmap 1.8
1. http://blog.csdn.net/u010723709/article/details/48007881

### 1.8 VS 1.6
1. 它摒弃了Segment（锁段）的概念，而是启用了一种全新的方式实现,利用CAS算法
2. 沿用了与它同时期的HashMap版本的思想，底层依然由“数组”+链表+红黑树的方式思想
3. 为了做到并发，又增加了很多辅助的类，例如TreeBin，Traverser等对象内部类

### sizeCtl 属性
1. 负数代表正在进行初始化或扩容操作
2. -1代表正在初始化
3. -N 表示有N-1个线程正在进行扩容操作
4. 正数或0代表hash表还没有被初始化，这个数值表示初始化或下一次进行扩容的大小，这一点类似于扩容阈值的概念。
还后面可以看到，它的值始终是当前ConcurrentHashMap容量的0.75倍，这与loadfactor是对应的

### Node 内部类
1. 这个Node内部类与HashMap中定义的Node类很相似，但是有一些差别  
2. 它对value和next属性设置了volatile同步锁  
3. 它不允许调用setValue方法直接改变Node的value域  
4. 它增加了find方法辅助map.get()方法 

### TreeNode
1. 当链表长度过长的时候，会转换为TreeNode
2. 与HashMap不相同的是，它并不是直接转换为红黑树，而是把这些结点包装成TreeNode放在TreeBin对象中，由TreeBin完成对红黑树的包装
3. 而且TreeNode在ConcurrentHashMap集成自Node类，而并非HashMap中的集成自LinkedHashMap.Entry<K,V>类，
也就是说TreeNode带有next指针，这样做的目的是方便基于TreeBin的访问

### TreeBin
1. 类并不负责包装用户的key、value信息，而是包装的很多TreeNode节点
2. 替了TreeNode的根节点，也就是说在实际的ConcurrentHashMap“数组”中，存放的是TreeBin对象，而不是TreeNode对象，这是与HashMap的区别

### ForwardingNode 
1. 一个用于连接两个table的节点类
2. 它包含一个nextTable指针，用于指向下一张表
3. 而且这个节点的key value next指针全部为null，它的hash值为-1. 
这里面定义的find的方法是从nextTable里进行查询节点，而不是以自身为头节点进行查找

### 三个核心方法
1. 
```javascript
@SuppressWarnings("unchecked")  
   //获得在i位置上的Node节点  
   static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {  
       return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);  
   }  
    //利用CAS算法设置i位置上的Node节点。之所以能实现并发是因为他指定了原来这个节点的值是多少  
    //在CAS算法中，会比较内存中的值与你指定的这个值是否相等，如果相等才接受你的修改，否则拒绝你的修改  
    //因此当前线程中的值并不是最新的值，这种修改可能会覆盖掉其他线程的修改结果  有点类似于SVN  
   static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,  
                                       Node<K,V> c, Node<K,V> v) {  
       return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);  
   }  
    //利用volatile方法设置节点位置的值  
   static final <K,V> void setTabAt(Node<K,V>[] tab, int i, Node<K,V> v) {  
       U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);  
   } 
```

### 初始化方法initTable
1. 对于ConcurrentHashMap来说，调用它的构造方法仅仅是设置了一些参数而已
2. 而整个table的初始化是在向ConcurrentHashMap中插入元素的时候发生的。
如调用put、computeIfAbsent、compute、merge等方法的时候，调用时机是检查table==null
3. 初始化方法主要应用了关键属性sizeCtl 如果这个值〈0，表示其他线程正在进行初始化，就放弃这个操作(Thread.yield())
4. 如果获得了初始化权限，就用CAS方法将sizeCtl置为-1，防止其他线程进入
5. 初始化数组后，将sizeCtl的值改为0.75*n

### 扩容方法 transfer
1. 支持并发扩容
2. 支持多线程进行扩容操作，而并没有加锁