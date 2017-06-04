package com.a0x03.wythe.easytransport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wythe on 2016/7/29.
 */
public class Succee {
    @SerializedName("succee")
    @Expose
    private String succee;

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
