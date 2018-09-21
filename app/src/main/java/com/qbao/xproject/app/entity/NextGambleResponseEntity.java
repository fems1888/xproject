package com.qbao.xproject.app.entity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/21 下午5:33.
 */

public class NextGambleResponseEntity {


    /**
     * awardAmount : 0
     * gambleDay : 2018-09-21T09:32:23.133Z
     * id : 0
     * joinAmount : 0
     * gambleNo : 0
     * redBallFirst : string
     * redBallSecond : string
     * redBallThird : string
     * blueBallEnd : string
     * amount : 0
     * awarded : false
     * closed : false
     * gambleJoinList : [{"accountNo":"string","awardExchangeId":0,"blueBallEnd":"string","gambleId":0,"id":0,"joinTime":"2018-09-21T09:32:23.133Z","payExchangeId":0,"redBallFirst":"string","redBallSecond":"string","redBallThird":"string","winner":true,"awardType":0,"rewardAmount":0,"rewardUnitName":"string"}]
     */

    private double awardAmount;
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
    private List<GambleJoinListBean> gambleJoinList;

    public double getAwardAmount() {
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

    public void setJoinAmount(int joinAmount) {
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

    public void setAmount(int amount) {
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

    public List<GambleJoinListBean> getGambleJoinList() {
        return gambleJoinList;
    }

    public void setGambleJoinList(List<GambleJoinListBean> gambleJoinList) {
        this.gambleJoinList = gambleJoinList;
    }

    public static class GambleJoinListBean {
        /**
         * accountNo : string
         * awardExchangeId : 0
         * blueBallEnd : string
         * gambleId : 0
         * id : 0
         * joinTime : 2018-09-21T09:32:23.133Z
         * payExchangeId : 0
         * redBallFirst : string
         * redBallSecond : string
         * redBallThird : string
         * winner : true
         * awardType : 0
         * rewardAmount : 0
         * rewardUnitName : string
         */

        private String accountNo;
        private int awardExchangeId;
        private String blueBallEnd;
        private int gambleId;
        private int id;
        private String joinTime;
        private int payExchangeId;
        private String redBallFirst;
        private String redBallSecond;
        private String redBallThird;
        private boolean winner;
        private int awardType;
        private double rewardAmount;
        private String rewardUnitName;

        public String getAccountNo() {
            return accountNo;
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

        public int getAwardType() {
            return awardType;
        }

        public void setAwardType(int awardType) {
            this.awardType = awardType;
        }

        public double getRewardAmount() {
            return rewardAmount;
        }

        public void setRewardAmount(int rewardAmount) {
            this.rewardAmount = rewardAmount;
        }

        public String getRewardUnitName() {
            return rewardUnitName;
        }

        public void setRewardUnitName(String rewardUnitName) {
            this.rewardUnitName = rewardUnitName;
        }
    }
}
