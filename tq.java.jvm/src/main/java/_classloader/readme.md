### 双亲委派
1. 当类加载器接收到加载class的请求的时候,先去查找自身缓存, 如果根据class的全限定名没有找到,就将加载class的任务
交给父加载器,逐级向上,当顶级(Boostrap)类加载器无法加载,就交给子类加载,逐级向下
2. 