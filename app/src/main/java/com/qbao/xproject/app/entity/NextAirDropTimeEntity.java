package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/18 下午4:42.
 */

public class NextAirDropTimeEntity {

    /**
     * beginTime : 2018-09-17T11:05:40.748Z
     * comment : string
     * id : 0
     * status : 0
     * totalAmount : 0
     * unitId : 0
     * unitName : string
     */

    private String beginTime;
    private String comment;
    private int id;
    private int status;
    private int totalAmount;
    private int unitId;
    private String unitName;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
