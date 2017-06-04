package com.a0x03.wythe.easytransport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wythe on 2016/7/28.
 */
public class Status {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("succee")
    @Expose
    private String succee;

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The succee
     */
    public String getSuccee() {
        return succee;
    }

    /**
     *
     * @param succee
     * The succee
     */
    public void setSuccee(String succee) {
        this.succee = succee;
    }

}
