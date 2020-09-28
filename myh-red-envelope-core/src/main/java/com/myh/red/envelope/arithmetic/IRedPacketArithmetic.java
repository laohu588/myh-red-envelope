package com.myh.red.envelope.arithmetic;

import java.util.List;

/**
 * 拆分红包算法接口;
 * 
 * @author myh
 * @date 2019/11/19
 * @copyright copyright (c) 2019
 */
public interface IRedPacketArithmetic {

    /**
     * 针对原始红包，拆分成若干个小红包.
     * 
     * @return
     */
    public List<Float> splitRedPackets();
}
