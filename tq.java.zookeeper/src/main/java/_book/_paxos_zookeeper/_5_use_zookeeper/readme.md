2019-04-12

## 使用 Zookeeper

### 客户端脚本
1. 创建
    - create [-s] [-e] path set acl
        - -s -e 标识节点特性: 顺序或者临时节点, 默认是持久节点
        - acl: 权限控制, 缺省--不做任何权限控制
2. 读取
    - ls 
        - ls path [watch]
        - 列出指定节点下的所有子节点
    - get
        - 获取指定节点的数据内容和属性信息
        - get path [watch]
3. 更新
    - set 
        - 更新指定节点的数据内容
        - set path data [version]
4. 删除
    - delete
    - 删除指定节点
    - delete path [version]
    - **只允许删除叶子节点**
    
### Watcher
1. 监控节点信息变化
2. Watcher 通知是一次性的, 一旦触发一次通知后, 该 Watcher 失效, 需要返回注册 Watcher

### Version
1. setData(int version)
2. cas

### 权限控制
1. 删除节点
    - 当客户端对一个数据节点增加了权限信息后, 对于删除操作而言, 作用范围是 **其子节点**
    - **对一个数据节点增加权限控制之后, 依然可以自由的删除这个节点**
2. 也就是说 对数据节点 进行权限控制
    - 只保护节点的内容, 但并不是节点本省
        - 比如 data, stat, child
        
### 开源客户端
1. TODO