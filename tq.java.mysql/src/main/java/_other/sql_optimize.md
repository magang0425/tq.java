2019-05-17

## SQL 优化

### 分析阶段

### 设计阶段
1. 规范: 1NF 2NF 3NF
    - 减少数据冗余
2. 合理的冗余
    - 减少表的连接, 提高效率
2. 主键设计
    - 重复率低, 单独或者组合查询可能性大的字段放前面
1. 外键设计
    - TODO
1. 字段的设计
    - 数字型
    - 尽量小
    - 不要null
    - 慎用自增字段
1. 索引设计
    - 数据量
    - 使用频率
    - 不要太多索引
1. 编码
    - 只返回需要的数据
        - where
        - select *
        - having
    - 少做重复的工作
        - 同一个语句执行多次
        - 减少数据转换
    - 注意事务和锁
        - 事务尽量小, 注意拆分
        - 事务中减少交互
        - 事务中按照相同的顺序访问资源
1. 注意临时表和表变量的用法
   - 如何语句太复杂, 连接太多, 可以考虑临时表
   - 多次使用同一份数据, 临时表
   - 临时表做 中间 汇总
1. 尽量使用索引