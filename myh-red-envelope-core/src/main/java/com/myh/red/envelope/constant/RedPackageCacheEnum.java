package com.myh.red.envelope.constant;

public enum RedPackageCacheEnum {

    SNAGUSERKEY(":u1", "针对本次活动,已抢到小红包的用户集合"), NOALLOCATIONKEY(":r", "从未分配红包队列中取出队列头部的红包;"),
    ALLOCATIONKEY(":r0", "已分配的小红包集合"), SMALLHBKEY("s_", "小红包编号中间含有的key"),RULE("rule","小红包生成对应的规则");

    private String remark;
    private String key;

    private RedPackageCacheEnum(String key, String remark) {
        this.remark = remark;
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
