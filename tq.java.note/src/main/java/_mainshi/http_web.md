### HTTP/WEB
1. Session, Cookie, 格式 传输内容
    - 都是为了标记用户状态
    - session
        - 服务端实现, 服务器中的一块内存
        - Set-Cookies: JSESSIONID
    - Cookie
        - 存储在浏览器中
        - 请求是发送个服务器
        - 服务器根据 Sessionid 查找用户状态    
2. Filter Servlet Listener
3. get post 基本区别
4. Servlet的生命周期
5. 报文
6. http协议格式，get和post的区别
    - 大小
    - 可以获取
7. 浅析Http和https的三次握手有什么区别。
    - http
        - TCP
    - HTTPS
        - TCP
        - client: 协议版本 client random 加密算法
        - server: server randon 确认加密算法
        - client: 确认公钥, Premaster secret 公钥加密
        - server: 私钥解密, Premaster secret
        - 双方 根据加密算法, 三个随机数, 生成对称加密密钥
        - 对称机密通讯
        
8. 谈谈Session/cookie机制. 如何实现会话跟踪？
    - TODO
