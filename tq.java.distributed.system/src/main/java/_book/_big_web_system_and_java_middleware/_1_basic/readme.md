2019-01-10

## 基础知识

### 分布系统中的难点
1. 缺乏全局时钟
    - 需要全局时钟的目的是为了多个动作的顺序性, 而不是为了知道准确的时间
    - 把工作交给一个单独的集群来区分多个动作的顺序性
2. 故障独立性
    - 整个系统的一部分有问题而其他部分正常运行
3. 处理单点故障
    - 某个角色或者功能只有某台单机支撑
    - 单点备份, 自动恢复
    - 降低单点故障的影响范围
4. 事务的挑战
    - 2PC
    - 最终一致
    - BASE
    - CAP
    - Paxos