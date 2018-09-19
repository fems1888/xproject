package com.qbao.xproject.app.request_body;

/**
 * @author Created by jackieyao on 2018/9/13 下午5:51.
 */

public class UserLoginRequest {
    private String phone;
    private String captchaCode;
    /**
     * 注册接口使用  运维给的   我们不用管
     */
    private String registerCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
}
