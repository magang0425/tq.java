### 分布式/高并发
1. Redis为什么可以实现分布式锁，memcached 可以实现分布式锁么？实现分布式锁的方式有很多种，为什么选择redis分布式锁？
    - Redis为什么可以实现分布式锁
        - 互斥性: 单线程, set nx
        - 安全性: 只有加锁的服务才能有解锁权限
        - 避免死锁: 锁过期
        - 保证加锁与解锁操作是原子性操作: set nx, lua
    - memcached 可以实现分布式锁么
        - TODO
2. dubbo的底层负载均衡，容错机制都是怎么实现的
    - TODO
3. 分布式Session设置
    - Session Sticky: session 固定在一个服务器
    - Session Replication
    - Session Cache
4. 分布式接口的幂等性(版本号)
    - 自带幂等性
    - 本地消息表
    - 唯一键
    - 状态机
5. 设计一个对外服务的接口的实现类, 在1,2,3这三个主机(对应不同的IP) 上实现 负载均衡和顺序轮训机制(考虑并发)
6. 缓存的雪崩以及穿透的理解
    - 穿透
        - 缓存空对象
        - 布隆过滤器
    - 雪崩
        - 随机过期时间
        - 不过期
        - 依赖隔离组件为厚点限流并降级
7. 开发一个大型网站会考虑哪些问题
    - 流程:
        - http
            - 静态资源
            - CDN
            - 响应时间
            - 每秒并发量
            - 负载均衡
            - 高可用
        - 处理
            - 并发处理
            - 缓存
            - 数据竞争
        - 入库
            - 事务
            - 读写分离
            - 分库分表
    - 其他
        - 降级与单点故障
        - 安全部署
        - 监控, 告警
8. 分布式锁
    - 数据库, 缓存, zk
9. 设计一个秒杀的场景
    - 考虑一个真实用户进入了服务器
    - 库存缓存
    - 分段加锁
    - 循环获取锁
    - 异步更新数据库
10. 分布式系统怎么做服务治理 ???
    - 服务治理只要包括 性能监控, 日志处理, 审计统计, 限流熔断, 扩缩容
    - 常用的治理思路
        - 微服务层 Spring Cloud
        - 网格计算 lstio
        - 容器化解决方案 Docker+K8S
11. 缓存机器增删如何对系统影响最小
    - 一致性hash
    - 虚拟槽
12. 一致性哈希的实现
    - 2^31组成一个环
    - 每个节点 hash/2^32
    - 数据 hash, 顺时针, 找到第一个比他大的节点, 放在他前面
    - 虚拟槽

14. 一万个人抢100个红包，如何实现（不用队列），如何保证2个人不能抢到同一个红包，可用分布式锁
    - 分布式锁
    - 桶
    - 数组index
15. 5台服务器如何选出leader(选举算法)
    - TODO
16. 主从复制
    - redis
        - RDB
            - 备份
            - 异步
        - AOF
            - 实时
            - 同步
    - mysql
    - 增加可用性
17. 分布式事务（JTA）
    - TODO
18. 高并发下，如何做到安全的修改同一行数据？
    - update xxx set field = xx where field = xx and version = xx
19. A服务调用B服务多接口，响应时间最短方案；
    - 批量
    - 队列 一次请求, 一次返回
    - 队列 多次请求, 请求唯一id,  一次返回
    
20. A系统给B系统转100块钱，如何实现？
    - 状态机
    - TODO
    - A:扣除和写入日志表 事务, **状态**
    - A:发送请求
    - A:重试(幂等性)
    - B:
        - 直接更新:update xxx set field = xx where field = xx and version = xx
        - 插入一条记录, 查询的时候在 加:insert into xxx add=xxx
    - B: 成功通知, A
    - B: 重试
    - A: 接收, 更改状态
    
21. 多线程下读概率远远大于写概率.如何解决并发问题？
    - 读写锁
    - 主从
    - 分区
    - 写时复制
    - 读写分离
22. 你是怎么控制缓存的更新？(被动方式/主动方式/增量/全量)？
    - TODO
24. 如何做限流策略. 令牌桶和漏斗算法的使用场景？
    - _current_limiting/readme.md
26. 数据一致性
    - CAP, BASE
    - 人工接入
        - 数据对账
        - 实时数据检验以及报警
27. 集群, 负载均衡, 分布式, 数据一致性的区别和关系
    - 当单个实例无法满足请求需求时候, 需要部署多台实例
    - 多台实例如何获取流量, 如何控制每个实例获取流量的份额, 需要负载聚恒
    - 多台时候的时候就需要有多个数据库实例, 引入分布式
    - 多台数据存储实例的时候, 就会引入 数据一致性
----------------20190508--------------
28. redis, Kafka, Dubbo, 设计原理 应用场景
    - TODO
1. 说说ACID, CAP理论和BASE理论？
    - ACID
        - 原子
        - 一致
        - 隔离
        - 持久
    - CAP
        - 一致
        - 可用
        - 分区容忍
    - BASE
        - 基本可用
        - 软状态
        - 最终一致性
        
1. 什么是最终一致性？最终一致性实现方式？
    - 数据在一定时间之后达到数据一致的状态
    - 实现方式
        - 本地更新记录表, 嵌入本地事务, 添加执行状态, 保证幂等性, 重试
        - 异步消息, 
            - 业务逻辑保证幂等
            - 如果业务逻辑无法保证幂等，则要增加一个去重表或者类似的实现。
            - 事务同步
    
1. 讲讲分布式事务？
    - 数据分布在多个节点下, 网络通讯, 保证数据一致
1. 如何保证消息的一致性?
    - 冗余备份
    - 消息幂等性
    - 重试
1. 消息中间件如何解决消息丢失问题？
    - RabbitMq
        - 生产者
            - 事务
            - 确认机制
        - 消费者
            - autoack
        - 至于到达队列之前以及消费之后 就没有办法了
    - Kafka
        - TODO
        - 生产者
            - acks: 有几个节点同步完成
        - 消费者
            - kafka自己记录了每次消费的offset数值，下次继续消费的时候，接着上次的offset进行消费即可。

1. Zookeeper的用途,选举的原理是什么?
    - 配置管理
    - leader选举
    - 分布式锁
    - 命名服务
    - 选举
        - 类似于分布式锁
        - 临时节点, Watcher通知
1. zookeeper原理和适用场景？
    - 树, 读任意, 写leader, ZXID, 事务日志, 快照, 临时/有序 节点
1. zookeeper watch机制？
    - client znode watcher事件, 变化server通知client, 队列, 请勿阻塞, 一次, 重复注册
    - 原理 TODO
1. redis/zk节点宕机如何处理？
    - redis
        - salve节点
        - 一致性hash
    - zk
        - Leader
            - 不对外提供服务
            - 重选leader
        - Follower
            - 宕机好了
            - 重启之后重新加入集群
1. 分布式集群下如何做到唯一序列号？
    - 数据库 多个, 固定步长
    - 单节点批量
    - uuid
    - redis
    - snowFlake
        - 41 时间
        - 10 机器
        - 12 序列号
1. 用过哪些MQ,怎么用的,和其他mq比较有什么优缺点,MQ的连接是线程安全的吗？
    - conn
    - channel

1. 突发流量如何 动态扩容
    - 如果采用服务容器化, 可以自动扩缩容
    - 否则, 事前预估, 事中削峰, 事后处理
    - 最后才是: 紧急人肉扩容, 风险高
1.RMQ消息堆积, 延迟怎么处理
    - 紧急扩容
    - 异步多写快速消费(消息log)
    - 尽量让消息中间件恢复正常, 然后在处理log
1. 高并发下如何处理热点问题
    - 永不过期
        - 数据不一致
        - 消息系统更新
    - 逻辑过期
        - 发现过期key, 后台线程更新
1. 分布式和集群的区别
    - TODO
1. 分布式架构和微服务的关系
2. 二段, 三段, TCC, 优缺点 
1. 注册中心宕机了怎么办
1. RPC 过程...    
1. 负载均衡的理解？
1. 正向代理和反向代理？
1. CDN实现原理？
1. 怎么提升系统的QPS和吞吐？
1. Dubbo的底层实现原理和机制？
1. 描述一个服务从发布到被消费的详细过程？
1. 分布式系统怎么做服务治理？
1. Dubbo的服务请求失败怎么处理？
1. 如何实现负载均衡,有哪些算法可以实现?
1. 讲讲数据的垂直拆分水平拆分？
1. 列举出能想到的数据库分库分表策略？
1. 一个分布式系统中, 本地函数 int getTradingAverage(int[] uids)表示传入一个用户id数组用于求用户消费的平均值, 
    远程服务器提供了接口: int getTrading(int uid)表示查询用户uid的消费金额, 请利用远程服务编程实现高效的本地函数.
    - fork/join