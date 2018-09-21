package com.qbao.xproject.app.request_body;

/**
 * @author Created by jackieyao on 2018/9/21 下午3:40.
 */

public class BetNextRequest {
    private int gambleId;
    private String accountNo;
    private String redBallFirst;
    private String redBallSecond;
    private String redBallThird;
    private String blueBallEnd;

    public int getGambleId() {
        return gambleId;
    }

    public void setGambleId(int gambleId) {
        this.gambleId = gambleId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
}
