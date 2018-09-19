package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/18 下午12:45.
 */

public class Account {

    /**
     * id : 6
     * createTime : 2018-09-14 18:52:02
     * accountName : 用户19338997
     * accountNo : 19338997
     * header : default.png
     * sourceType : 0
     * shareCode : AUQFZX
     * phone : 13023151465
     * countryId : +86
     * loginTime : 2018-09-18 12:41:58
     */

    private int id;
    private String createTime;
    private String accountName;
    private String accountNo;
    private String header;
    private String sourceType;
    private String shareCode;
    private String phone;
    private String countryId;
    private String loginTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
