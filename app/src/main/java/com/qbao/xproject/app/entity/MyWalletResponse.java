package com.qbao.xproject.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Created by jackieyao on 2018/9/18 上午10:30.
 */

public class MyWalletResponse {

    private List<MyWalletList> result;

    public List<MyWalletList> getResult() {
        return result;
    }

    public void setResult(List<MyWalletList> result) {
        this.result = result;
    }

    public static class MyWalletList implements Parcelable{
        /**
         * header : null
         * accountNo : 103
         * unit : 300
         * amount : 33.947619
         * withdrowFee : 4.5713
         * unitName : XCOIN
         */

        private String header;
        private String icon;
        private String accountNo;
        private int unit;
        private double amount;
        private double withdrowFee;
        private String unitName;

        protected MyWalletList(Parcel in) {
            header = in.readString();
            icon = in.readString();
            accountNo = in.readString();
            unit = in.readInt();
            amount = in.readDouble();
            withdrowFee = in.readDouble();
            unitName = in.readString();
        }

        public static final Creator<MyWalletList> CREATOR = new Creator<MyWalletList>() {
            @Override
            public MyWalletList createFromParcel(Parcel in) {
                return new MyWalletList(in);
            }

            @Override
            public MyWalletList[] newArray(int size) {
                return new MyWalletList[size];
            }
        };

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getWithdrowFee() {
            return withdrowFee;
        }

        public void setWithdrowFee(double withdrowFee) {
            this.withdrowFee = withdrowFee;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(header);
            dest.writeString(icon);
            dest.writeString(accountNo);
            dest.writeInt(unit);
            dest.writeDouble(amount);
            dest.writeDouble(withdrowFee);
            dest.writeString(unitName);
        }
    }
}
