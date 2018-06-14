2018-05-23

## JVM 性能监控

### 重要的垃圾收集数据
1. 当前使用的垃圾收集器
2. java 堆大小
3. 新生代和老年代的大小
4. 永久代的大小
5. Minor GC 的持续时间
6. Minor GC 的频率
7. Minor GC 的空间回收量
8. Full GC 的持续时间
9. Full GC 的频率
10. 每个并发垃圾收集周期内的空间回收量
11. 垃圾收集前后Java堆的占用量
12. 垃圾收集前后新生代和老年代的占用量
13. 垃圾收集前后永久代的占用量
14. 是否老年代或永久代的占用量触发了 Full GC
15. 应用是否显示调用了 System.gc()