package com.myh.red.envelope.arithmetic.impl;

import java.util.ArrayList;
import java.util.List;

import com.myh.red.envelope.arithmetic.IRedPacketArithmetic;

/**
 * 策略2;<br>
 * 可根据小红包上限、下限金额、原始红包总金额、小红包的数量，进行拆分;
 * 
 * @author myh
 * @date 2019/11/19
 * @copyright copyright (c) 2019
 */
public class RedPacketArithmetic2 implements IRedPacketArithmetic {

	// 设置小红包的上下限金额大小，原始红包总金额,金额单位：元;
	private float minMoney, maxMoney, totalMoney, originalMoney;
	// 设置小红包的数量;
	private int count;

	public RedPacketArithmetic2(float minMoney, float maxMoney, int count, float totalMoney) {
		this.minMoney = minMoney;
		this.maxMoney = maxMoney;
		this.count = count;
		this.totalMoney = totalMoney;
		this.originalMoney = totalMoney;
	}

	private boolean isRight(float money, int count) {
		double avg = money / count;
		if (avg < minMoney) {
			return false;
		} else if (avg > maxMoney) {
			return false;
		}
		return true;
	}

	private float randomRedPacket(float money, float mins, float maxs, int count) {
		if (count == 1) {
			return (float) (Math.round(money * 100)) / 100;
		}
		if (mins == maxs) {
			return mins;// 如果最大值和最小值一样，就返回mins
		}
		float max = maxs > money ? money : maxs;
		float one = ((float) Math.random() * (max - mins) + mins);
		one = (float) (Math.round(one * 100)) / 100;
		float moneyOther = money - one;
		if (isRight(moneyOther, count - 1)) {
			return one;
		} else {
			// 重新分配
			float avg = moneyOther / (count - 1);
			if (avg < minMoney) {
				return randomRedPacket(money, mins, one, count);
			} else if (avg > maxMoney) {
				return randomRedPacket(money, one, maxs, count);
			}
		}
		return one;
	}

	private static final float TIMES = 2.1f;

	/**
	 * 如果输为null，说明设置的最大值和最小值不合理，与红包总金额和红包数量无法匹配。
	 * 
	 * @param money 红包原始金额大小;
	 * @param count 小红包个数;
	 * @return
	 */
	@Override
	public List<Float> splitRedPackets() {
		if (!isRight(totalMoney, count)) {
			return null;
		}
		List<Float> list = new ArrayList<Float>();
		float max = (float) (totalMoney * TIMES / count);

		max = max > maxMoney ? maxMoney : max;
		for (int i = 0; i < count; i++) {
			float one = randomRedPacket(totalMoney, minMoney, max, count - i);
			list.add(one);
			totalMoney -= one;
		}
		return list;
	}

	@Override
	public String toString() {
		return "RedPacketArithmetic2 [最小红包下限=" + minMoney + ", 最小红包上限=" + maxMoney + ", 原始红包总金额=" + originalMoney
				+ ", 小红包个数=" + count + "]";
	}

	public static void main(String[] args) {

		int number = 10;// 小红包个数;
		float totalMoney = 45f;// 原始红包总金额;
		float minMoney = 3.8f;// 小红包最小金额;
		float maxMoney = 4.8f;// 小红包最大金额;

		IRedPacketArithmetic rps = new RedPacketArithmetic2(minMoney, maxMoney, number, totalMoney);

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
