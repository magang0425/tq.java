2018-01-29

### 类.this
1. 当在一个类的内部类中，如果需要访问外部类的方法或者成员域的时候，
如果使用  this.成员域(与 内部类.this.成员域 没有分别) 调用的显然是内部类的域 ，
如果我们想要访问外部类的域的时候，就要必须使用  外部类.this.成员域
2. 那就是-->使 使用意图更加的清楚