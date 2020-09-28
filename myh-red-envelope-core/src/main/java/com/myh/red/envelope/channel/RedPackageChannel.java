package com.myh.red.envelope.channel;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.myh.red.envelope.constant.RedPackageCacheEnum;
import com.myh.red.envelope.constant.RedPacketConstants;
import com.myh.red.envelope.core.AbstractChannel;
import com.myh.red.envelope.core.ChannelExecuteUtils;
import com.myh.zookeeper.lock.abstracts.Lock;

/**
 *   通道实现抢红包操作;
 * 
 * @author myh
 * @date 2019/11/18
 * @copyright copyright (c) 2019
 */
@SuppressWarnings({"rawtypes"})
public class RedPackageChannel extends AbstractChannel {

    public RedPackageChannel() {

    }
    
    // A、添加原始红包部分小红包(单条),未抢的红包队列;
    // |_1、系统获取一个原始红包，根据红包金额、红包数量、红包拆分规则，将原始红包拆分为一定数量的未分配的小红包(每一个小红包都会有一个小红包号)。使用redis list数据结构存储。
    // |_2、将若干个小红包，缓存到redis 指定库的节点上。
    @Override
    public boolean addSmallHb(String channelNumber, RedisTemplate redisTemplate, String bigHbId,
        List<String> smallHbs) {
        return ChannelExecuteUtils.addSmallHb(channelNumber, redisTemplate, bigHbId, smallHbs);
    }

    /**
     * 用户抢红包操作;<br>
     * 0表示 抢到红包;<br>
     * 2表示没有抢到，因为红包已抢完<br>
     * 1表示用户曾抢到过1次红包，不能再抢;
     */
    @Override
    public Map<String, Object> robHb(Lock lock, RedisTemplate redisTemplate, String bigHbId, String userId,
        String channelNo) {
        try {
            lock.lock();
            return ChannelExecuteUtils.robHb(redisTemplate, bigHbId, userId, channelNo);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void robotRobRedPackage(String bigHbId) {

    }

    /**
     * 检查某一个用户，针对某一场活动是否抢过红包
     */
    @Override
    public boolean checkUserRobRedPacket(RedisTemplate redisTemplate, String userId, String redActivityId) {

        // 定义：针对本次活动,已抢到小红包的用户集合(set)
        String snagUserKey =
            RedPacketConstants.BIG_KEY_PREFIX + redActivityId + RedPackageCacheEnum.SNAGUSERKEY.getKey();

        // 1、从已抢到红包的用户集合中，判断当前要抢红包的用户是否抢到过红包;
        boolean result = redisTemplate.opsForSet().isMember(snagUserKey, userId);

        return result;
    }

}
