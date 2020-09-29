
##### 1、需要多套redis实例,举例：如果抢红包通道希望是两个通道，就需要跟两个redis实例建立连接，使用同样的库号

如下示例：

```xml
# Redis实例1：建立用户、活动、与通道之间的关系缓存的位置;

# Redis实例2：抢红包A通道操作;

# Redis实例3：抢红包B通道操作;

```
..............也可以配置多个Redis实例通道。

备注说明：

Lock对象：lockA、lockB
RedisTemplate对象：channelARedisTemplate、channelBRedisTemplate

##### 2、redis涉及到的数据结构;

```java

# 当前活动，下一个用户应该分配的抢红包通道(string)：red_activity:117_channel

# 兑换入场劵成功能后，缓存用户对应的抢红包的通道(hash)：red_activity:117

# 针对本次活动，未分配的小红包集合(List)：red_activity:117:r

# 针对本次活动，已抢到小红包的用户集合(Set)：red_activity:117:u1

# 针对本次活动，已分配的小红包集合(List)：red_activity:117:r0


```

##### 3、为解决并发线程安全问题，需要使用分布式加锁;



##### 4、个人实现的设计思想梳理：
http://note.youdao.com/noteshare?id=8b83aeec540cbe8e246fb3c56a67f085&sub=9012DAA609824AD89F5B01EA03924853