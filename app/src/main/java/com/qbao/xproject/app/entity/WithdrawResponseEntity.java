package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/25 上午11:53.
 */

public class WithdrawResponseEntity {

    /**
     * accountNo : 19338997
     * toAddress : 0x54e7914887f064971fa36cd9a7de09101989ca18
     * amount : 0.1
     * fee : 0.0013
     * servicePool : 1
     * unit : 101
     * feeUnit : 101
     * feePerKb : 1
     */

    private String accountNo;
    private String toAddress;
    private double amount;
    private double fee;
    private int servicePool;
    private int unit;
    private int feeUnit;
    private double feePerKb;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getServicePool() {
        return servicePool;
    }

    public void setServicePool(int servicePool) {
        this.servicePool = servicePool;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getFeeUnit() {
        return feeUnit;
    }

    public void setFeeUnit(int feeUnit) {
        this.feeUnit = feeUnit;
    }

    public double getFeePerKb() {
        return feePerKb;
    }

    public void setFeePerKb(double feePerKb) {
        this.feePerKb = feePerKb;
    }
}
