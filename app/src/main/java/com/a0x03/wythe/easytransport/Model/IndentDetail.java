package com.a0x03.wythe.easytransport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wythe on 2016/7/28.
 */
public class IndentDetail {
    @SerializedName("id_indnet")
    @Expose
    private String idIndnet;
    @SerializedName("taken_date")
    @Expose
    private String takenDate;
    @SerializedName("taken_time")
    @Expose
    private String takenTime;
    @SerializedName("done_date")
    @Expose
    private String doneDate;
    @SerializedName("done_time")
    @Expose
    private String doneTime;
    @SerializedName("car_type")
    @Expose
    private String carType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("id_payment")
    @Expose
    private String idPayment;
    @SerializedName("phone_driver")
    @Expose
    private String phoneDriver;

    /**
     *
     * @return
     * The idIndnet
     */
    public String getIdIndnet() {
        return idIndnet;
    }

    /**
     *
     * @param idIndnet
     * The id_indnet
     */
    public void setIdIndnet(String idIndnet) {
        this.idIndnet = idIndnet;
    }

    /**
     *
     * @return
     * The takenDate
     */
    public String getTakenDate() {
        return takenDate;
    }

    /**
     *
     * @param takenDate
     * The taken_date
     */
    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

    /**
     *
     * @return
     * The takenTime
     */
    public String getTakenTime() {
        return takenTime;
    }

    /**
     *
     * @param takenTime
     * The taken_time
     */
    public void setTakenTime(String takenTime) {
        this.takenTime = takenTime;
    }

    /**
     *
     * @return
     * The doneDate
     */
    public String getDoneDate() {
        return doneDate;
    }

    /**
     *
     * @param doneDate
     * The done_date
     */
    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    /**
     *
     * @return
     * The doneTime
     */
    public String getDoneTime() {
        return doneTime;
    }

    /**
     *
     * @param doneTime
     * The done_time
     */
    public void setDoneTime(String doneTime) {
        this.doneTime = doneTime;
    }

    /**
     *
     * @return
     * The carType
     */
    public String getCarType() {
        return carType;
    }

    /**
     *
     * @param carType
     * The car_type
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

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
     * The idPayment
     */
    public String getIdPayment() {
        return idPayment;
    }

    /**
     *
     * @param idPayment
     * The id_payment
     */
    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    /**
     *
     * @return
     * The phoneDriver
     */
    public String getPhoneDriver() {
        return phoneDriver;
    }

    /**
     *
     * @param phoneDriver
     * The phone_driver
     */
    public void setPhoneDriver(String phoneDriver) {
        this.phoneDriver = phoneDriver;
    }
}
