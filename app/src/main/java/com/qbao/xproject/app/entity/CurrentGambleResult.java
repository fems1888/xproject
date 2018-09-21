package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/21 上午10:44.
 */

public class CurrentGambleResult {

    /**
     * awardAmount : 0
     * gambleDay : 2018-09-20T15:49:03.134Z
     * id : 0
     * joinAmount : 0
     * gambleNo : 0       期号 ,
     * redBallFirst : string   红球1 ,
     * redBallSecond : string    红球2
     * redBallThird : string   红球3
     * blueBallEnd : string    蓝球
     * amount : 0   每注金额
     * awarded : false  是否已开奖
     * closed : false    是否已关闭。已关闭就是不能投注了
     */

    private int awardAmount;
    private String gambleDay;
    private int id;
    private double joinAmount;
    private int gambleNo;
    private String redBallFirst;
    private String redBallSecond;
    private String redBallThird;
    private String blueBallEnd;
    private double amount;
    private boolean awarded;
    private boolean closed;

    public int getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(int awardAmount) {
        this.awardAmount = awardAmount;
    }

    public String getGambleDay() {
        return gambleDay;
    }

    public void setGambleDay(String gambleDay) {
        this.gambleDay = gambleDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getJoinAmount() {
        return joinAmount;
    }

    public void setJoinAmount(double joinAmount) {
        this.joinAmount = joinAmount;
    }

    public int getGambleNo() {
        return gambleNo;
    }

    public void setGambleNo(int gambleNo) {
        this.gambleNo = gambleNo;
    }

    public String getRedBallFirst() {
        return redBallFirst;
    }

    public void setRedBallFirst(String redBallFirst) {
        this.redBallFirst = redBallFirst;
    }

    public String getRedBallSecond() {
        return redBallSecond;
    }

    public void setRedBallSecond(String redBallSecond) {
        this.redBallSecond = redBallSecond;
    }

    public String getRedBallThird() {
        return redBallThird;
    }

    public void setRedBallThird(String redBallThird) {
        this.redBallThird = redBallThird;
    }

    public String getBlueBallEnd() {
        return blueBallEnd;
    }

    public void setBlueBallEnd(String blueBallEnd) {
        this.blueBallEnd = blueBallEnd;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAwarded() {
        return awarded;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
