package com.myh.red.envelope.constant;

public class RedPacketConstants {

    public static final String BIG_KEY_PREFIX = "red_activity:";

    // 红包活动，指定的用户对应的下一个红包通道的redis key;
    public static final String RED_ACTIVITY_USER_NEXT_CHANNEL = "red_activity_next_channel";

    // 抢红包通道锁的key的前缀;
    public static final String CHANNEL_LOCK_NODE = "channel_lock_";
}
