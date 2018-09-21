package com.qbao.xproject.app.entity;

import android.content.Context;

import com.qbao.xproject.app.R;

/**
 * @author Created by jackieyao on 2018/9/21 上午11:31.
 */

public class JoinGambleResponseEntity {

    public static final int GRAND_PRIZE = 0;
    public static final int FIRST_PRIZE = 1;
    public static final int SECOND_PRIZE = 2;
    public static final int THRID_PRIZE = 3;
    /**
     * accountNo : string
     * awardExchangeId : 0
     * awardType : 0     中奖类型：0：特等奖 1：一等奖 2：二等奖 3：三等奖 ,
     * blueBallEnd : string
     * gambleId : 0
     * id : 0
     * joinTime : 2018-09-21T02:54:55.660Z
     * payExchangeId : 0
     * redBallFirst : string
     * redBallSecond : string
     * redBallThird : string
     * winner : true
     * rewardAmount (number, optional): 中奖金额 ,
     *  rewardUnitName (string, optional): 中奖币种名
     */

    private String accountNo;
    private int awardExchangeId;
    private int awardType;
    private String blueBallEnd;
    private int gambleId;
    private int id;
    private String joinTime;
    private int payExchangeId;
    private String redBallFirst;
    private String redBallSecond;
    private String redBallThird;
    private boolean winner;

    private double rewardAmount;

    private String rewardUnitName;
    public String getAccountNo() {
        return accountNo;
    }

    public double getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getRewardUnitName() {
        return rewardUnitName;
    }

    public void setRewardUnitName(String rewardUnitName) {
        this.rewardUnitName = rewardUnitName;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getAwardExchangeId() {
        return awardExchangeId;
    }

    public void setAwardExchangeId(int awardExchangeId) {
        this.awardExchangeId = awardExchangeId;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public String getBlueBallEnd() {
        return blueBallEnd;
    }

    public void setBlueBallEnd(String blueBallEnd) {
        this.blueBallEnd = blueBallEnd;
    }

    public int getGambleId() {
        return gambleId;
    }

    public void setGambleId(int gambleId) {
        this.gambleId = gambleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public int getPayExchangeId() {
        return payExchangeId;
    }

    public void setPayExchangeId(int payExchangeId) {
        this.payExchangeId = payExchangeId;
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

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public  String getAwardType(Context context){
        if (awardType == GRAND_PRIZE){
            return context.getString(R.string.grand_prize);
        }else if (awardType == FIRST_PRIZE){
            return context.getString(R.string.first_prize);
        }else if (awardType == SECOND_PRIZE){
            return context.getString(R.string.second_prize);
        }else if (awardType == THRID_PRIZE){
            return context.getString(R.string.thrid_prize);
        }else {
            return "很遗憾";
        }
    }
}
