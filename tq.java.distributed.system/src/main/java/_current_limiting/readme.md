2019-01-09

## 接口限流

### 限流算法
1. 令牌桶
    - 一个存放固定容量令牌的桶，按照固定速率往桶里添加令牌
    - 步骤
        - 已固定速率往桶中添加令牌
        - 桶中最多存放 N 个令牌
        - 一个操作某次申请 X 个令牌
        - 桶中有 X 个令牌 则 操作被允许
        - 不足 X 个令牌, 操作被拒绝
2. 漏桶
    - 漏桶作为计量工具（The Leaky Bucket Algorithm as a Meter）时，
        可以用于流量整形（Traffic Shaping）和流量控制（TrafficPolicing）
    - 描述
        - 一个固定容量的漏桶，按照常量固定速率流出水滴；
        - 如果桶是空的，则不需流出水滴；
        - 可以以任意速率流入水滴到漏桶；
        - 如果流入水滴超出了桶的容量，则流入的水滴溢出了（被丢弃），而漏桶容量是不变的。
3. 计数器
    - 在时间节点处, 流量有超出的可能
4. 总结
    - 令牌桶 VS 漏桶 
        - 令牌桶
            - 限制的是平均流入速率
            - 并允许一定程度突发流量
        - 漏桶
            - 常量流出速率
            - 平滑突发流入速率；

### 应用级限流
1. 限流 总并发/连接/请求数
    - Tomcat
        - acceptCount：如果Tomcat的线程都忙于响应，新来的连接会进入队列排队，如果超出排队大小，则拒绝连接；
        - maxConnections： 瞬时最大连接数，超出的会排队等待；
        - maxThreads：Tomcat能启动用来处理请求的最大线程数，如果请求处理量一直远远大于最大线程数则可能会僵死。
2. 限流 总资源
    - 数据库连接
    - 线程
3. 限制某个接口的总并发/请求数
4. 限流某个接口的时间窗请求数
5. **平滑限流某个接口的请求数**
    - 缓解突发流量
    - 令牌桶, 漏桶

### 分布式限流
1. TODO https://jinnianshilongnian.iteye.com/blog/2305117