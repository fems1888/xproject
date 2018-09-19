package com.qbao.xproject.app.entity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/18 下午4:06.
 */

public class UnReceiveAirDropEntity {


    private List<UnReceiveAirDrop> result;

    public List<UnReceiveAirDrop> getResult() {
        return result;
    }

    public void setResult(List<UnReceiveAirDrop> result) {
        this.result = result;
    }

    public static class UnReceiveAirDrop {
        /**
         * id : 6
         * accountNo : 103
         * amount : 30
         * activityId : 39
         * received : false
         * avialable : true
         */

        private int id;
        private String accountNo;
        private String unitName;
        private double amount;
        private int activityId;
        private int unit;
        private boolean received;
        private boolean avialable;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public boolean isReceived() {
            return received;
        }

        public void setReceived(boolean received) {
            this.received = received;
        }

        public boolean isAvialable() {
            return avialable;
        }

        public void setAvialable(boolean avialable) {
            this.avialable = avialable;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }
    }
}
