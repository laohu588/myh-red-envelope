package com.myh.red.envelope.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.RedisTemplate;

import com.myh.red.envelope.constant.ChannelEnum;

/**
 * 通过红包通道序号，获取指定的通过redisTemplate
 * 
 * @author myh
 *
 */
@SuppressWarnings("rawtypes")
public class RedisTemplateFactory {

	private static Map<String, RedisTemplate> channel = new ConcurrentHashMap<String, RedisTemplate>();

	static {
		channel.put(ChannelEnum.A.getNo(), (RedisTemplate) SpringUtil.getBean("channelARedisTemplate"));
		channel.put(ChannelEnum.B.getNo(), (RedisTemplate) SpringUtil.getBean("channelBRedisTemplate"));
	}

	public static RedisTemplate getRedisTemplate(String type) {
		return channel.get(type);
	}

}
