package com.myh.red.envelope.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myh.red.envelope.constant.ChannelEnum;
import com.myh.zookeeper.lock.abstracts.Lock;

/**
 * 通过红包通道序号，获取指定的锁对象;
 * 
 * @author myh
 *
 */
public class LockFactory {

	private static Map<String, Lock> lock = new ConcurrentHashMap<String, Lock>();

	static {
		lock.put(ChannelEnum.A.getNo(), (Lock) SpringUtil.getBean("lockA"));
		lock.put(ChannelEnum.B.getNo(), (Lock) SpringUtil.getBean("lockB"));
	}

	public static Lock getLock(String type) {
		return lock.get(type);
	}

}
