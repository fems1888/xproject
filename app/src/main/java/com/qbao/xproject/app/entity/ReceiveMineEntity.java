package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/21 上午11:02.
 */

public class ReceiveMineEntity {

    /**
     * result : {"id":0,"accountNo":"string","amount":0,"received":false}
     * systemTime : 1537455908159
     * nextMineCreateTime : 1537460100000
     */

    private UnReceiveMineEntity.UnReceiveMineResult result;
    private long systemTime;
    private long nextMineCreateTime;

    public UnReceiveMineEntity.UnReceiveMineResult getResult() {
        return result;
    }

    public void setResult(UnReceiveMineEntity.UnReceiveMineResult result) {
        this.result = result;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public long getNextMineCreateTime() {
        return nextMineCreateTime;
    }

    public void setNextMineCreateTime(long nextMineCreateTime) {
        this.nextMineCreateTime = nextMineCreateTime;
    }

}
