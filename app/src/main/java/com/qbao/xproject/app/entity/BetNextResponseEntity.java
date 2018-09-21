package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/21 下午3:39.
 */

public class BetNextResponseEntity {


    /**
     * id : 0
     * accountNo : string
     * amount : 0
     * received : false
     */

    private int id;
    private String accountNo;
    private int amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
