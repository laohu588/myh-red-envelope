package com.myh.red.envelope.test;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.myh.red.envelope.constant.RedPacketConstants;
import com.myh.red.envelope.core.AbstractChannel;
import com.myh.red.envelope.core.LockFactory;
import com.myh.red.envelope.core.RedisTemplateFactory;
import com.myh.zookeeper.lock.abstracts.Lock;

/**
 * 示例;
 * 
 * @author myh
 *
 */
public class Test {

	@Autowired
	private AbstractChannel channel;

	@Resource(name = "channelRedisTemplate")
	private RedisTemplate channelRedisTemplate;// 查询用户抢红包通道的操作;

	public static void main(String[] args) {

	}

	public Map<String, Object> robRedPackage(String userId, String redActivityId) {

		// 1、筛选用户抢红包通道;
		String key = RedPacketConstants.BIG_KEY_PREFIX + redActivityId;
		Object userChannel = channelRedisTemplate.opsForHash().get(key, "j" + userId);// 用户与通道之间的关系的redis 实例;
		if (null != userChannel) {

			// 获取使用通道redisTemplate;
			// 当为1的时候，使用a通道;
			// 当为2的时候，使用b通道;
			RedisTemplate redisTemplate = RedisTemplateFactory.getRedisTemplate(userChannel.toString());

			/**
			 * 通过通道序号，区分使用那一个通道的锁;
			 */
			Lock lock = LockFactory.getLock(userChannel.toString());

			// 2、使用指定的通道进行抢红包;
			// * 用户抢红包操作;<br>
			// * 0表示 抢到红包;<br>
			// * 1表示用户曾抢到过1次红包，不能再抢;
			// * 2表示没有抢到，因为红包已抢完<br>
			Map<String, Object> robResult = channel.robHb(lock, redisTemplate, redActivityId, userId,
					userChannel.toString());// 执行抢红包操作;

			return robResult;

		}

		return null;

	}

}
