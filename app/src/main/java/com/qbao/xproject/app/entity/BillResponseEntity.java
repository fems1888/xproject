package com.qbao.xproject.app.entity;

import android.content.Context;

import com.qbao.xproject.app.R;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/20 下午7:04.
 */

public class BillResponseEntity {

    public static final int WITHDRAW = 0;
    public static final int NO_START = 0;
    public static final int CONFIRMING = 1;
    public static final int CONFIRMED = 2;
    public static final int FAIL = 3;

    /**
     * time : 2018-09-25
     * content : [{"id":176,"accountName":"用户19338997","address":"0x54e7914887f064971fa36cd9a7de09101989ca18","amount":0.1,"exchangeTime":"2018-09-25 11:53:05","isDeleted":false,"unit":101,"type":0,"accountNo":"19338997","status":0,"fee":0.0013,"feeUnit":101,"unitName":"ETH","typeContent":"提币","symbol":"-","icon":"withdraw.png"}]
     */

    private String time;
    private List<BillContentResponse> content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<BillContentResponse> getContent() {
        return content;
    }

    public void setContent(List<BillContentResponse> content) {
        this.content = content;
    }

    public static class BillContentResponse {
        /**
         * id : 176
         * accountName : 用户19338997
         * address : 0x54e7914887f064971fa36cd9a7de09101989ca18
         * amount : 0.1
         * exchangeTime : 2018-09-25 11:53:05
         * isDeleted : false
         * unit : 101
         * type : 0:代表提现
         * accountNo : 19338997
         * status :    0。未开始。1 确认中 2 已确认。3 失败
         * fee : 0.0013
         * feeUnit : 101
         * unitName : ETH
         * typeContent : 提币
         * symbol : -
         * icon : withdraw.png
         */

        private int id;
        private String accountName;
        private String address;
        private double amount;
        private String exchangeTime;
        private boolean isDeleted;
        private int unit;
        private int type;
        private String accountNo;
        private int status;
        private double fee;
        private int feeUnit;
        private String unitName;
        private String typeContent;
        private String symbol;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getExchangeTime() {
            return exchangeTime;
        }

        public void setExchangeTime(String exchangeTime) {
            this.exchangeTime = exchangeTime;
        }

        public boolean isIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public int getFeeUnit() {
            return feeUnit;
        }

        public void setFeeUnit(int feeUnit) {
            this.feeUnit = feeUnit;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getTypeContent() {
            return typeContent;
        }

        public void setTypeContent(String typeContent) {
            this.typeContent = typeContent;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getStatus(Context context) {
            if (status == NO_START) {
                return context.getString(R.string.no_start);
            } else if (status == CONFIRMING) {
                return context.getString(R.string.confirming);
            } else if (status == CONFIRMED) {
                return context.getString(R.string.confirmed);
            } else if (status == FAIL) {
                return context.getString(R.string.failed);
            }
            return "";
        }
    }
}
