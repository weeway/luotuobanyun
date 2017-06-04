package com.a0x03.wythe.easytransport.Utils;

/**
 * Created by wythe on 2016/9/19.
 */
public class NotificationData {

    public NotificationData(String msg,String title, String time) {
        this.msg = msg;
        this.time = time;
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msg;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title;
}
