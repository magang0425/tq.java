2019-04-19

## 技术内幕
1. 系统模型
2. 序列化与协议
3. 客户端工作原理
4. 会话
4. 服务端工作原理
4. 数据存储


### 系统模型
1. 数据模型(ZNode)
    - 树
    - 事务id: 
        - 改变 Zookeeper 服务器状态的操作
2. 节点类型
    - 持久
    - 持久顺序
    - 临时
    - 临时顺序
3. 状态信息
    - cZxid = 0x1a
        - Create
    - ctime = Fri Apr 19 11:47:23 CST 2019
    - mZxid = 0x35
        - Modified
    - mtime = Sun Apr 21 09:58:17 CST 2019
    - pZxid = 0x1a
        - **子节点列表最后一次修改的事务id**
        - 只关心子节点列表, 不关心子节点内容
    - cversion = 0
        - child
    - dataVersion = 5
    - aclVersion = 0
    - ephemeralOwner = 0x0
        - 创建该节点的会话的 Sessionid, 如果该节点是永久节点 =0
    - dataLength = 1
    - numChildren = 0
        - 孩子节点的数量
4. 版本
    - 分布式数据原子操作
    - CAS
5. Watcher--数据变更通知
    - [](watcher.md)
6. ACL--保证数据安全
    - TODO
    
### 序列化
1. TODO

### 协议
1. len + 请求头/响应头 + 请求体/响应体
2. 请求协议解析
    - len(0-3): 整个请求包的数据包长度
    - xid(4-7): 客户端请求的发起序号
    - type(8-11): 客户端请求类型
        - 根据type的不同, 内容也不同, 以 getData 为例
        - len(12-15): 节点路径的长度
        - path(xx-xx): 节点路径(根据 节点路径的长度 来)
        - watch(最后一个): 是否注册 Watcher
2. 响应洗衣解析
    - len(0-3): 整个请求包的数据包长度
    - xid(4-7): 客户端请求的发起序号
    - zxid(8-15): 当前服务器处理过的最新的 ZXID
    - err(16-19): 错误码
    - len(20-23): getData 就标识 节点数据内容的长度
        - ...
3. 具体分析
    - 请求
        - 00 00 00 18 / 00 00 00 0a / 00 00 00 04 /  00 00 00 0b / 2f 74 61 6e 67 71 69 6e 67 2f 31 00
        - 00 00 00 18 --> 24 len
        - 00 00 00 0a --> 10 xid
        - 00 00 00 04 --> 4  type
        - 00 00 00 0b --> 11 len
        - 2f 74 61 6e 67 71 69 6e 67 2f 31 --> path
        - 00 --> 0 
    - 响应
        - 00 00 00 59 / 00 00 00 0a / 00 00 00 00 00 00   
          00 3b / 00 00 00 00 /  00 00 00 01 / 34 /
          00 00 00 00 00 00 00 1a 
          00 00 00 00 00 00 00 35 
          00 00 01 6a 33 b4 b9 a0 
          00 00 01 6a 3d 9d 90 89 
          00 00 00 05 
          00 00 00 00 
          00 00 00 00 
          00 00 00 00 00 00 00 00 
          00 00 00 01 
          00 00 00 00 
          00 00 00 00 00 00 00 1a      
        - 00 00 00 59 --> 89 len
        - 00 00 00 0a --> 10 xid
        - 00 00 00 00 00 00 00 3b --> 59 zxid
        - 00 00 00 00 --> 0 err
        - 00 00 00 01 --> 1 len
        - 34 --> 4 data
        - 00 00 00 00 00 00 00 1a --> 1a 8位 czxid
        - 00 00 00 00 00 00 00 35 --> 35 8位 mzxid
        - 00 00 01 6a 33 b4 b9 a0 --> 8位 创建时间 ctime
        - 00 00 01 6a 3d 9d 90 89 --> 8位 最后一次修改时间 mtime
        - 00 00 00 05 --> version 节点内容版本号
        - 00 00 00 00 --> cversion
        - 00 00 00 00 --> aversion
        - 00 00 00 00 00 00 00 00 --> ephemeralOwner
        - 00 00 00 01 --> datalength
        - 00 00 00 00 --> 子节点个数
        - 00 00 00 00 00 00 00 1a --> pzxid
        
### Client
1. TODO

### 会话
[](session.md)

### Leader 选举
[](leader.md)