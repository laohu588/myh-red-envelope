package com.myh.red.envelope.core;

import com.myh.red.envelope.constant.RedPackageCacheEnum;
import com.myh.red.envelope.constant.RedPacketConstants;
import com.myh.red.envelope.constant.RobRedPackageStatusEnum;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelExecuteUtils {

    // A、添加原始红包部分小红包(单条),未抢的红包队列;
    // |_1、系统获取一个原始红包，根据红包金额、红包数量、红包拆分规则，将原始红包拆分为一定数量的未分配的小红包(每一个小红包都会有一个小红包号)。使用redis list数据结构存储。
    // |_2、将若干个小红包，缓存到redis 指定库的节点上。
    public static boolean addSmallHb(int ruleNo, String channelNumber, RedisTemplate redisTemplate, String bigHbId,
        List<String> smallHbs) {

        String smallHbId = null;

        try {
            System.out.println(">>> 拆的红包列表,规则：" + ruleNo + ":::" + smallHbs.toString());
            for (int i = 0; i < smallHbs.size(); i++) {

                smallHbId = RedPackageCacheEnum.RULE.getKey() + ruleNo + "_" + RedPackageCacheEnum.SMALLHBKEY.getKey()
                    + channelNumber + "_" + smallHbs.get(i);// 小红包生成的值;

                // 定义：针对本次活动，未分配的小红包集合List
                String noAllocationKey =
                    RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.NOALLOCATIONKEY.getKey();

                redisTemplate.opsForList().leftPush(noAllocationKey, smallHbId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public static boolean addSmallHb(String channelNumber, RedisTemplate redisTemplate, String bigHbId,
        List<String> smallHbs) {

        String smallHbId = null;

        try {

            for (int i = 0; i < smallHbs.size(); i++) {

                String ruleNo = smallHbs.get(i).split(":")[0];
                String money = smallHbs.get(i).split(":")[1];

                smallHbId = RedPackageCacheEnum.RULE.getKey() + ruleNo + "_" + RedPackageCacheEnum.SMALLHBKEY.getKey()
                    + channelNumber + "_" + money;// 小红包生成的值;

                // 定义：针对本次活动，未分配的小红包集合List
                String noAllocationKey =
                    RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.NOALLOCATIONKEY.getKey();

                redisTemplate.opsForList().leftPush(noAllocationKey, smallHbId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * 用户抢红包操作;<br>
     * 0表示 抢到红包;<br>
     * 2表示没有抢到，因为红包已抢完<br>
     * 1表示用户曾抢到过1次红包，不能再抢;
     */

    public static Map<String, Object> robHb(RedisTemplate redisTemplate, String bigHbId, String userId,
        String channelNo) {

        Map<String, Object> robDetail = new HashMap<>();// 声明抢红包的详情;

        robDetail.put("userId", userId);// 用户信息;
        robDetail.put("redId", bigHbId);// 红包活动ID;

        // 定义：针对本次活动,已抢到小红包的用户集合(set)
        String snagUserKey = RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.SNAGUSERKEY.getKey();

        // 1、从已抢到红包的用户集合中，判断当前要抢红包的用户是否抢到过红包;
        if (redisTemplate.opsForSet().add(snagUserKey, userId) == 1) {

            String noAllocationKey =
                RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.NOALLOCATIONKEY.getKey();
            // 2、从未分配红包队列中取出队列头部的红包;
            Object smallHbId = redisTemplate.opsForList().leftPop(noAllocationKey);// 返回小红包对象;

            if (null != smallHbId) {

                // 定义：已分配的小红包集合
                String allocationKey =
                    RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.ALLOCATIONKEY.getKey();

                // 3、将用户号和小红包号插入已分配红包队列的队尾;
                redisTemplate.opsForList().leftPush(allocationKey, userId + "_" + smallHbId);
                System.out.println("用户：" + userId + ",抢到红包：" + smallHbId);

                robDetail.put("money", Float.parseFloat(smallHbId.toString().split("_")[3]));// 抢到的红包金额;
                robDetail.put("status", RobRedPackageStatusEnum.SNAG.getStatus());// 状态;

                return robDetail;

            } else {
                System.out.println("用户:" + userId + ",没有抢到红包，因为红包已经抢完！");
                redisTemplate.opsForSet().remove(snagUserKey, userId);

                robDetail.put("money", 0.0f);// 抢到的红包金额;
                robDetail.put("status", RobRedPackageStatusEnum.NO_SNAG.getStatus());// 状态;

                return robDetail;
            }

        } else {
            System.out.println("用户：" + userId + ",曾抢到过1次红包，不能再抢.");
            // 查询抢到的红包金额;
            // String snagSamllIdKey =
            // RedPacketConstants.BIG_KEY_PREFIX + bigHbId + RedPackageCacheEnum.ALLOCATIONKEY.getKey();

            robDetail.put("money", 0.0f);// 抢到的红包金额;
            robDetail.put("status", RobRedPackageStatusEnum.REPETITION_ROB.getStatus());// 状态;

            return robDetail;
        }

    }

}
