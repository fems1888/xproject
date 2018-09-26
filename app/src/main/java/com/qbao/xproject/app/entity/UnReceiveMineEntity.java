package com.qbao.xproject.app.entity;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/20 下午1:49.
 */

public class UnReceiveMineEntity {

    /**
     * result : [{"id":35,"accountNo":"19338997","amount":31.578947,"received":false},{"id":65,"accountNo":"19338997","amount":31.578947,"received":false},{"id":70,"accountNo":"19338997","amount":40,"received":false},{"id":208,"accountNo":"19338997","amount":80,"received":false},{"id":210,"accountNo":"19338997","amount":100,"received":false},{"id":212,"accountNo":"19338997","amount":100,"received":false},{"id":214,"accountNo":"19338997","amount":100,"received":false},{"id":218,"accountNo":"19338997","amount":100,"received":false},{"id":220,"accountNo":"19338997","amount":100,"received":false},{"id":228,"accountNo":"19338997","amount":100,"received":false},{"id":230,"accountNo":"19338997","amount":100,"received":false},{"id":232,"accountNo":"19338997","amount":100,"received":false}]
     * systemTime : 1537932793492
     * nextMineCreateTime : 1537935300000
     */

    private long systemTime;
    private long nextMineCreateTime;
    private List<UnReceiveMineResult> result;

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

    public List<UnReceiveMineResult> getResult() {
        return result;
    }

    public void setResult(List<UnReceiveMineResult> result) {
        this.result = result;
    }

    public static class UnReceiveMineResult {
        /**
         * id : 35
         * accountNo : 19338997
         * amount : 31.578947
         * received : false
         */

        private int id;
        private String accountNo;
        private double amount;
        private boolean received;

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

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public boolean isReceived() {
            return received;
        }

        public void setReceived(boolean received) {
            this.received = received;
        }
    }
}
