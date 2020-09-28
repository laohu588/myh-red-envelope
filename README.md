
# 1、需要多套redis实例,举例：如果抢红包通道希望是两个通道，就需要跟两个redis实例建立连接，使用同样的库号

如下示例：

## 建立用户、活动、与通道之间的关系缓存的位置;
spring.common.redis.objectName=redis-redpackage
spring.common.redis.database=0

## 抢红包A通道操作;
spring.channela.redis.objectName=redis-redpackage
spring.channela.redis.database=1

## 抢红包B通道操作;
spring.channelb.redis.objectName=redis-redpackage
spring.channelb.redis.database=2


..............也可以配置多个通道。

# 2、为保证并发所导致的数据不一致问题，需要使用分布式加锁;


# 3、个人实现的设计思想梳理：
http://note.youdao.com/noteshare?id=8b83aeec540cbe8e246fb3c56a67f085&sub=9012DAA609824AD89F5B01EA03924853