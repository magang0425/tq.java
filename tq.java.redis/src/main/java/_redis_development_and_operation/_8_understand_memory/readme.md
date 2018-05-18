2018-05-10

## 理解内存

### 内存使用统计
1. info memory
    - used_memory_rss: 从操作系统显示Redis进程占用的物理内存总量 
    - used_memory: 内部存储的所有数据内存占用量
    - mem_fragmentation_ratio: used_memory_rss / used_memory
    - 当mem_fragmentation_ratio>1时， 说明used_memory_rss-used_memory多出
      的部分内存并没有用于数据存储， 而是被内存碎片所消耗， 如果两者相差很
      大， **说明碎片率严重**
    - 当mem_fragmentation_ratio<1时， 这**种情况一般出现在操作系统把Redis
      内存交换（Swap）** 到硬盘导致， 出现这种情况时要格外关注， 由于硬盘速
      度远远慢于内存， Redis性能会变得很差， 甚至僵死
2. 测试Redis  mem_fragmentation_ratio > 400???

### 内存消耗划分
1. 对象内存
    - 简单理解为 sizeof(key) + sizeof(value)
2. 缓冲内存
3. 内存碎片
    - 频繁的更新操作,
    - **大量的过期键删除**: 键对象过期删除之后, 释放的空间无法得到充分利用, 导致碎片率上升
    - 解决:
        - 数据对齐
        - 安全重启:主从切换, 安全重启

### 内存管理
1. 控制内存上限
    - maxmemory参数限制最大内存
        - 用于缓存场景, 超出内存上限, 使用 LRU 等删除策略释放空间
        - 防止所用内存超过服务器物理内存,
        - redis默认无限使用服务器内存, 防止极端情况下导致系统内存耗尽
2. 动态调整内存上限
    - config set maxmemory 6GB 
    - 
2. 回收策略实现
    - 删除到达过期时间的键对象
        - 精确删除消耗大量CPU, 成本太高
        - 惰性删除
            - 节省CPU
            - 问题: 单独使用这种方式, 当过期键一致没有访问, 将无法得到及时删除, 导致内存不释放--内存泄漏
        - 定时任务删除机制
            - 定时任务
            - 自适应算法, 根据键的过期比例, 使用快慢两种速率模式回收键
                - TODO
    - 内存达到maxmemory上限是触发内存溢出的控制策略
        - noeviction: 默认策略, **不会删除任何数据, 拒绝写入操作并返回错误**
        - volatile-lru: 根据LRU算法删除设置了超时属性（expire） 的键， 直
                       到腾出足够空间为止。 如果没有可删除的键对象， 回退到noeviction策略。
        - allkeys-lru： 根据LRU算法删除键， 不管数据有没有设置超时属性，直到腾出足够空间为止
        - allkeys-random： 随机删除所有键， 直到腾出足够空间为止。
        - volatile-random： 随机删除过期键， 直到腾出足够空间为止。
        - volatile-ttl： 根据键值对象的ttl属性， 删除最近将要过期数据。 如果
        没有， 回退到noeviction策略

### 内存优化
1. redisObject
    - type字段: 标识当前对象使用的数据类型--> type {key}
    - encoding字段: redis内部编码类型, 代表当前对象内部采用哪种数据结构实现
    - lru字段: 记录对象最后一次被访问的时间,当配置了maxmemory和 maxmemory-policy=volatile-lru或者allkeys-lru时，
     **用于辅助LRU算法删除键数据**
    - refcount字段: 当前对象被引用的次数, 帮助通过引用次数回收内存, 
        **当对象为整数且范围在[0-9999]时， Redis可以使用共享对象的方式来节省内存**
    - *ptr字段: 与对象的数据内容相关， **如果是整数， 直接存储数据**； 否则表示指向数据的指针
    
2. 缩减键值对象
    - 降低Redis内存使用最直接的方法就是 缩减 键 和 值 的长度
    - 压缩 value
3. 共享对象池
    - 共享对象池是指Redis内部维护[0-9999]的整数对象池
    - 当设置maxmemory并启用LRU相关淘汰策略如： volatile-lru， allkeys-lru时， Redis禁止使用共享对象
        - LRU算法根据对象的最后访问时间来删除数据
        - 使用共享对象, LRU也被共享
        - 无法确定对象的随后访问时间
        - 共享对象池 与 maxmemory+LRU策略冲突
    - ziplist编码的值对象, 内部数据为整数也无法使用对象池
        - TODO
    - 为什么只有整数对象池
        - 复用几率大
        - 整数比较算法时间复杂度 O(1)
        - 一万个防止对象池浪费
        - 字符串判断相等性 O(n)
        - 更复杂的数据结构 hash list 相等性判断 O(n2)
        - 
4. 字符串优化
    - **所有的键都是字符串类型， 值对象数据除了整数之外都使用字符串存储**
    - SDS
        - O(1)时间复杂度获取: 字符串长度, 已用长度, 未用长度
        - 可用于保存二进制数组
        - **内存空间预分配机制**, 降低内存在分配次数
        - **惰性删除策略**, 字符串缩减后的空间不释放, 作为预分配空间保留
    - 预分配规则
        - 第一次创建, len=实际大小, free=0
        - 修改后, 如果free<lM, 每次预分配一倍容量: len=60byte, free=0, 追加 60byte, 预分配120byte
        共使用: 60byte+60byte+120byte+1byte 
        - 修改后如果已有free空间不够且数据大于1MB， 每次预分配1MB数
          据
        - **尽量减少字符串频繁修改操作如append、 setrange， 改为直接使用set修
          改字符串， 降低预分配带来的内存浪费和内存碎片化**
          
### 编码优化
![](https://github.com/t734070824/tq.java/blob/master/tq.java.redis/src/main/java/_redis_development_and_operation/_8_understand_memory/1.jpg?raw=true)

1. //TODO  编码优化
    