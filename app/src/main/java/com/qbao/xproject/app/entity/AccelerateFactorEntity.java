package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/19 下午6:02.
 */

public class AccelerateFactorEntity {


    /**
     * id : 76
     * receiveTime : 1537342204000
     * accountNo : 45537476
     * taskType : 0
     * speedAdd : 1.0
     */

    private int id;
    private long receiveTime;
    private String accountNo;
    private int taskType;
    private double speedAdd;
    private double speed;
    private boolean canReceive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public double getSpeedAdd() {
        return speedAdd;
    }

    public void setSpeedAdd(double speedAdd) {
        this.speedAdd = speedAdd;
    }

    public boolean isCanReceive() {
        return canReceive;
    }

    public void setCanReceive(boolean canReceive) {
        this.canReceive = canReceive;
    }

    public double getSpeedExpect() {
        return speed;
    }

    public void setSpeedExpect(double speedExpect) {
        this.speed = speedExpect;
    }
}
