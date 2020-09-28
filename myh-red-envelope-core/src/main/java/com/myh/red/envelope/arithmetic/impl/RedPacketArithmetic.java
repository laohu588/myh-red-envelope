package com.myh.red.envelope.arithmetic.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.myh.red.envelope.arithmetic.IRedPacketArithmetic;

/**
 * 策略1;<br>
 * 可根据小红包下限金额、原始红包总金额、小红包的数量，进行拆分;
 * 
 * @author myh
 * @date 2019/11/19
 * @copyright copyright (c) 2019
 */
public class RedPacketArithmetic implements IRedPacketArithmetic {

    // 设置小红包的数量;
    private int count;
    // 设置小红包的下限金额大小，原始红包总金额,金额单位：元;
    private float totalMoney, minMoney, originalMoney;

    public RedPacketArithmetic() {

    }

    public RedPacketArithmetic(float minMoney, float totalMoney, int count) {
        this.minMoney = minMoney;
        this.totalMoney = totalMoney;
        this.count = count;
        this.originalMoney = totalMoney;
    }

    /**
     * 检查输入的参数是否合法;
     * 
     * @param money
     * @param count
     * @param minMoney
     * @return
     */
    private boolean isValidity() {
        double avg = totalMoney / count;
        if (avg < minMoney) {
            return false;
        }
        return true;
    }

    /**
     * 原始红包，拆分成指定数量的小红包;
     * 
     * @param number 小红包个数;
     * @param total 原始红包总金额
     * @param min 最小红包金额;
     * @return
     */
    @Override
    public List<Float> splitRedPackets() {

        boolean result = isValidity();

        if (!result) {
            return null;
        }

        float money;
        double max;// 红包的最大值;
        int i = 1;
        List<Float> math = new ArrayList<Float>();
        DecimalFormat df = new DecimalFormat("###.##");
        while (i < count) {
            // 保证即使一个红包是最大的了,后面剩下的红包,每个红包也不会小于最小值
            max = totalMoney - minMoney * (count - i);
            int k = (int)(count - i) / 2;
            // 保证最后两个人拿的红包不超出剩余红包
            if (count - i <= 2) {
                k = count - i;
            }
            // 最大的红包限定的平均线上下
            max = max / k;
            // 保证每个红包大于最小值,又不会大于最大值
            money = (int)(minMoney * 100 + Math.random() * (max * 100 - minMoney * 100 + 1));
            money = (float)money / 100;
            // 保留两位小数
            money = Float.parseFloat(df.format(money));
            totalMoney = (int)(totalMoney * 100 - money * 100);
            totalMoney = totalMoney / 100;
            math.add(money);
            i++;
            // 最后一个人拿走剩下的红包
            if (i == count) {
                math.add(totalMoney);
            }
        }
        return math;
    }

    @Override
    public String toString() {
        return "RedPacketArithmetic [小红包个数=" + count + ", 最小红包下限=" + minMoney + ", 原始红包总金额=" + originalMoney + "]";
    }

    public static void main(String[] args) {

        int number = 10;// 小红包个数;
        float totalMoney = 45f;// 原始红包总金额;
        float minMoney = 3.8f;// 小红包最小金额;

        IRedPacketArithmetic rps = new RedPacketArithmetic(minMoney, totalMoney, number);

        List<Float> result = rps.splitRedPackets();

        if (result != null) {
            System.out.println("" + rps.toString());
            System.out.println("------------------------------------");
            System.out.println(result);
        } else {
            System.out.println("设置的参数不合法,请重新分配!");
        }

    }

}
