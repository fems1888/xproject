package com.qbao.xproject.app.request_body;

/**
 * @author Created by jackieyao on 2018/9/25 上午11:39.
 */

public class WithdrawRequest {
    private String amount;
    private String feePerKb;
    private String toAddress;
    private int unit;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFeePerKb() {
        return feePerKb;
    }

    public void setFeePerKb(String feePerKb) {
        this.feePerKb = feePerKb;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
