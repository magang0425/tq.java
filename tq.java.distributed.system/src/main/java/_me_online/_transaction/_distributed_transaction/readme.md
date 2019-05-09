2018-04-08

## 综述
1. 分布式事务在微服务架构中，几乎可以说是无法避免
2. 分布式系统的核心就是处理各种异常情况，这也是分布式系统复杂的地方，因为分布式的网络环境很复杂，
    所以我们在做分布式系统的时候，最先考虑的就是这种情况。这些异常可能有 
    - 机器宕机、网络异常、消息丢失、消息乱序、数据错误、不可靠的TCP、存储数据丢失、其他异常等等
3. CAP定理
4. 数据库的两阶段提交(2PC, XA Transactions)
5. BASE理论

### BASE理论
1. Basically Available（基本可用）
1. Soft state（软状态）
1. Eventually consistent（最终一致性）
2. 核心思想:我们无法做到强一致，但每个应用都可以根据自身的业务特点，采用适当的方式来使系统达到最终一致性()Eventual consistency)



### 异常事件举例(本地事务数据库断电) 
1. SQL Server来举例
2. SQL Server 数据库是由两个文件组成的，一个数据库文件和一个日志文件
3. 通常情况下，日志文件都要比数据库文件大很多。数据库进行任何写入操作的时候都是要先写日志的
3. 执行事务的时候数据库首先会记录下这个事务的redo操作日志，然后才开始真正操作数据库，在操作之前首先会把日志文件写入磁盘
3. 当突然断电的时候，即使操作没有完成，在重新启动数据库时候，数据库会根据当前数据的情况进行undo回滚或者是redo前滚，这样就保证了数据的强一致性

### 两阶段提交
![](1.jpg)

1. 第一阶段：事务协调器要求每个涉及到事务的数据库预提交(precommit)此操作，并反映是否可以提交.
1. 第二阶段：事务协调器要求每个数据库提交数据
2. 优缺点: 
    - 优点： 尽量保证了数据的强一致，适合对数据强一致要求很高的关键领域。（其实也不能100%保证强一致）
    - 缺点： 实现复杂，牺牲了可用性，对性能影响较大，不适合高并发高性能场景，如果分布式系统跨接口调用，目前 .NET 界还没有实现方案。
    

### 补偿事务（TCC）
1. 核心思想: 针对每个操作，都要注册一个与其对应的确认和补偿（撤销）操作。它分为三个阶段：
    - Try 阶段主要是对业务系统做检测及资源预留
        - 资源预留
            - **在try阶段不去真实的做资源扣除, 只是需要一个字段作为 预留资源的标识**
    - Confirm 阶段主要是对业务系统做确认提交，Try阶段执行成功并开始执行 Confirm阶段时，默认 Confirm阶段是不会出错的。即：只要Try成功，Confirm一定成功。
        - 有开源的 TCC 分布式框架
            - ByteTC
            - himly
            - tcc-transaction
        - 更改资源状态, 做真正扣除
    - Cancel 阶段主要是在业务执行错误，需要回滚的状态下执行的业务取消，预留资源释放
2. 假入 Bob 要向 Smith 转账，思路大概是
    - 首先在 Try 阶段，要先调用远程接口把 Smith 和 Bob 的钱给冻结起来。
    - 在 Confirm 阶段，执行远程调用的转账的操作，转账成功进行解冻。
    - 如果第2步执行成功，那么转账成功，如果第二步执行失败，则调用远程冻结接口对应的解冻方法 (Cancel)。   
3. 优缺点:
    - 优点： 跟2PC比起来，实现以及流程相对简单了一些，但数据的一致性比2PC也要差一些
    - 缺点： 
        - **三个阶段的具体代码需要自行编写**
        - 缺点还是比较明显的，在2,3步中都有可能失败。TCC属于应用层的一种补偿方式，
        - 所以需要程序员在实现的时候多写很多补偿的代码，在一些场景中，一些业务流程可能用TCC不太好定义及处理
4. 其他
    - 如何保证之前没执行完的分布式事务继续执行的呢
        - 记录一些分布式事务的活动日志的，可以在磁盘上的日志文件里记录，也可以在数据库里记录。保存下来分布式事务运行的各个阶段和状态
    - 万一某个服务的Cancel或者Confirm逻辑执行一直失败怎么办呢
        - TCC事务框架会通过活动日志记录各个服务的状态, **会不停的重试调用他的Cancel或者Confirm逻辑，务必要他成功！**
