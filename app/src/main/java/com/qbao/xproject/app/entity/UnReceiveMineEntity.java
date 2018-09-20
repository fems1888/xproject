package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/20 下午1:49.
 */

public class UnReceiveMineEntity {

    /**
     * id : 0
     * accountNo : string
     * amount : 0
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
