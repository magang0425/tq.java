2017-12-25
### 单例模式和多线程(懒汉，恶汉，双重校验锁，静态内部类，枚举)
1. 恶汉：因为加载类的时候就创建实例，所以线程安全(多个ClassLoader存在时例外)。缺点是不能延时加载。
2. 懒汉：需要加锁才能实现多线程同步，但是效率会降低。优点是延时加载。
3. 双重校验锁：麻烦，在当前Java内存模型中不一定都管用，某些平台和编译器甚至是错误的，因为instance = new MaYun()这种代码在不同编译器上的行为和实现方式不可预知。
4. 静态内部类：延迟加载，减少内存开销。因为用到的时候才加载，避免了静态field在单例类加载时即进入到堆内存的permanent代而永远得不到回收的缺点(大多数垃圾回收算法是这样)。
5. 枚举：很好，不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。但是失去了类的一些特性，没有延迟加载，用的人也太少了～～

### 