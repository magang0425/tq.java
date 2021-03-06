2018-10-16

## JDBC 实践

### JDBC的缺陷
1. 资源关闭
    - 大量资源需要使用后关闭
    - 关闭的顺序
2. 异常捕捉
    - SqlException 包括一切数据库访问异常, 缺少层次化的处理
    - **ErrorCode由具体的数据库来决定**
        - 应该有JDBC统一定义

### JdbcTemplate 的诞生
1. **封装所有基于JDBC的数据访问代码, 以统一的格式和规范来使用JDBC API**
2. **对SqlException的异常在框架内进行统一的转译, 纳入Spring自身的异常管理体系中**
3. 模板方法模式
    - 将多个类存在类似的方法或者逻辑提取到模板类中
    - 相应的子类实现自身的特殊逻辑
    - AbsJdbcTemplate
        - 需要实现多个子类
        - 优化
            - 引入 StatementCallback
            - JdbcTemplate
                - 作用
                    - 将提供资源的(JdbcTemplate) 以及 和 使用资源的(StatementCallback)分离
                    - 使用资源的不用关心资源的释放
4. Spring-JdbcTemplate
    - JdbcAccessor
        - DataSource
            - 替代 java.sql.DriverManager
            - ConnectionFactory
                - **数据库连接的缓冲池**
                - **分布式事务的支持**
        - SQLExceptionTranslator
            - 异常转译
    - JdbcOperations
        - 可以使用的JDBC操作集合
    - 模板方法
        - 面向 Connection 的模板方法
            - 自由度最大
            - 避免使用
        - 面向 Statement 的模板方法
            - 静态sql访问
        - PreparedStatement
            - 动态sqL
            - 防止sql注入
        - CallableStaement
            - 存储过程
    - 控制 JdbcTemplate 的行为
        - 最大结果集
        - 超时时间
    - 异常转译
        - 继承SQLErrorCodeSQLExceptionTranslator
        - 在classpath下增加 sql-error-codes.xml
4. 查询接口回调
    - **对查询结果返回进行定制**
    - ResultSetExtractor
        - RowCallbackhandler
        - RowMapper
5. 更新
    - 批量更新
    
### DataSource
1. ConnectionFactory
2. 分类
    - 简单的实现
        - DriverManagerDataSource
            - 每一次都是返回新的连接
            - 没有池化的功能
            - 测试环境使用
        - SingleConnectionDataSource
            - 总是返回同一个 Connection
    - 拥有连接缓冲池的DataSource实现
        - DPCP
        - C3P0
    - 支持分布式事务的 DataSource 实现
        - XADataSource

### JdbcDaoSupport
1. 直接继承就可以使用了
    - 终于想起了以前为什么那么做
    
### 基于操作对象的JDBC使用方式
1. RdbmsOperation
    - 查询操作对象
        - SqlQuery
            - SqlFunction
                - 更纯粹的查询
                - 常用 返回单一的结果
    - 更新操作对象
        - SqlUpdate
    - 存储过程对象
        - StoredProceure
    - declareParameter
        - 确定参数类型
    - **compile**
        - 必须的参数检查和设置
2. UpdatableSqlQuery
    - **可更新的结果集**: 对查询结果进行更新
3. 更新
    - SqlUpdate
        - compile
    - BatchSqlUpdate
        - 批量执行多个sql语句
        - 仍然是借助于 JdbcTemplate 