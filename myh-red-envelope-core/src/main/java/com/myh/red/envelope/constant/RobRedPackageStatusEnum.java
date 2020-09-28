package com.myh.red.envelope.constant;

public enum RobRedPackageStatusEnum {

    SNAG(0, "本次抢到红包"), 
    NO_SNAG(2, "表示没有抢到，因为红包已抢完"), 
    REPETITION_ROB(1, "表示用户曾抢到过1次红包，不能再抢");

    private int status;
    private String remark;

    private RobRedPackageStatusEnum(int status, String remark) {

        this.status = status;
        this.remark = remark;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
