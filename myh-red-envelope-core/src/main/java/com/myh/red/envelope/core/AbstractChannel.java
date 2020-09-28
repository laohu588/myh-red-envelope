package com.myh.red.envelope.core;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.myh.zookeeper.lock.abstracts.Lock;

/**
 * 红包抽象业务处理;
 * 
 * @author myh
 * @date 2019/11/18
 * @copyright copyright (c) 2019
 */
public abstract class AbstractChannel {

    // A、添加原始红包部分小红包(单条),未抢的红包队列;
    // |_1、系统获取一个原始红包，根据红包金额、红包数量、红包拆分规则，将原始红包拆分为一定数量的未分配的小红包(每一个小红包都会有一个小红包号)。使用redis list数据结构存储。
    // |_2、将若干个小红包，缓存到redis 指定库的节点上。
    public abstract boolean addSmallHb(String channelNumber, RedisTemplate redisTemplate, String bigHbId,
        List<String> smallHbs);

    // B、用户抢原始红包的小红包;
    public abstract Map<String, Object> robHb(Lock lock, RedisTemplate redisTemplate, String bigHbId, String userId,
        String channelNo);

    // C、使用机器人来抢红包;
    public abstract void robotRobRedPackage(String bigHbId);

    /**
          * 检查某一个用户，对某一场活动是否抢过红包。
     * @param redisTemplate
     * @param userId
     * @param redActivityId
     * @return
     */
    public abstract boolean checkUserRobRedPacket(RedisTemplate redisTemplate, String userId, String redActivityId);

}
