* cookie 是什么？
* cookie 的作用？
* 优势与不足？
* 其他
* token 存储在哪里呢？
  * 任务要求是将token放在cookie中，但是将其放在cookie中只是把 cookie 当作一个储存机制，而不是一种验证机制；所以放在Header中是否更好？这里应该怎么验证token，看了git上的一些代码是将token进行解密后与数据库中的数据进行比较，这样虽然解决了将token存放在session中的好处（减少服务器内存压力），但是又需要进行至少一次的查询，也会加大服务器压力吧。所以，将token放在缓存中会不会好一点？
  * 我觉得JWT加密不需要拿数据库查询，base64反编码头部和payload之后就可以使用密钥再次加密头部和payload看看是否和传过来的签名部分一致（当然这个签名也是经过base64编码的，方便传输），不一致就禁止访问，但是使用任务里要求的des加密用户名.登陆时间戳可能就需要拿数据库的数据做比对。
  * 我个人认为放在header可能更好，因为cookie仅仅是浏览器才拥有的，对于移动端不一定有，如果一个接口对应多种形式的前端，我认为只能存放在header或者session中，如果要实现无状态，我认为将token放在header会比较好
  * token是可以放在cookie和session中的，具体情况采用不同的方案设计。其次你觉得查数据库的压力会比一直插入数据的压力大吗？存放session主要是万一存放session的服务器挂了，怎么办？token只是颁发了一个令牌而已，并且数据库中的数据是可以用缓存的。
* 有了 cookie 为什么还要 Session?
* cookie 中存储用户重要的数据不安全，而且不宜传递大量数据。https://baijiahao.baidu.com/s?id=1651865969043048849&wfr=spider&for=pc
* Session 保存在哪了？ 
  * 容器里，Session是存储在Tomcat的容器中，所以如果后端机器是多台的话，因此多个机器间是无法共享Session的，此时可以使用Spring提供的分布式Session的解决方案，是将Session放在了Redis中 :https://baijiahao.baidu.com/s?id=1651865969043048849&wfr=spider&for=pc

* token，session，cookie 之间的对比？
  * Token相比较于Session的优点在于，当后端系统有多台时，由于是客户端访问时直接带着数据，因此无需做共享数据的操作。