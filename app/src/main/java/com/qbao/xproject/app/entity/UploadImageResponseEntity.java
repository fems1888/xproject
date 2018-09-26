package com.qbao.xproject.app.entity;

/**
 * @author Created by jackieyao on 2018/9/25 下午3:11.
 */

public class UploadImageResponseEntity {

    /**
     * attachment : string
     * content : string
     * id : 0
     * phone : string
     * sendFrom : string
     * sendTo : string
     */

    private String attachment;
    private String content;
    private int id;
    private String phone;
    private String sendFrom;
    private String sendTo;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
