2019-03-28

## Rabbitmq Trace

### 使用目的
1. 发现数据量和数据提供方的数据量对应不上
2. 数据发送方没有进行发送方确认
3. 寻找一个可以监控数据流出以及流入方式

### 启动过程
1. rabbitmq-plugins enable rabbitmq_tracing

### 调研结果
1. publish.#
    - publish.交换机: 所有发送到此交换机的数据
2. deliver.#
    - deliver.队列名字: 所有从这个队列消费的数据, 不管有没有被确认
    

### 使用失败
1. 准备创建一个新的队列来接受对应的数据, 不消费