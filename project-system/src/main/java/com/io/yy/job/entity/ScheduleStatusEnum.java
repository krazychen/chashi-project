package com.io.yy.job.entity;

/**
 * Created by shirukai on 2018/9/4
 */
public enum ScheduleStatusEnum {
    ACTIVATED(0, "已激活"),
    INACTIVATED(1, "未激活");
    private int status;
    private String stateInfo;

    ScheduleStatusEnum(int status, String stateInfo) {
        this.status = status;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return status;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
