package com.a0x03.wythe.easytransport.Model;

import android.os.Bundle;
import android.util.StringBuilderPrinter;

import com.a0x03.wythe.easytransport.Utils.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wythe on 2016/7/28.
 */
public class Result {
//    @SerializedName("id_indnet")
//    @Expose
//    private String idIndnet;
//    @SerializedName("status")
//    @Expose
//    private Integer status;
//    @SerializedName("cost")
//    @Expose
//    private String cost;
//    @SerializedName("publish_date")
//    @Expose
//    private String publishDate;
//    @SerializedName("publish_time")
//    @Expose
//    private String setoutTime;
//
//
//
//    @SerializedName("setout_date")
//    @Expose
//    private String setoutDate;
//    @SerializedName("setout_time")
//    @Expose
//    private String publishTime;
//    @SerializedName("start_loc")
//    @Expose
//    private StartLoc startLoc;
//    @SerializedName("end_loc")
//    @Expose
//    private EndLoc endLoc;
//
//    /**
//     *
//     * @return
//     * The idIndnet
//     */
//    public String getIdIndnet() {
//        return idIndnet;
//    }
//
//    /**
//     *
//     * @param idIndnet
//     * The id_indnet
//     */
//    public void setIdIndnet(String idIndnet) {
//        this.idIndnet = idIndnet;
//    }
//
//    /**
//     *
//     * @return
//     * The status
//     */
//    public Integer getStatus() {
//        return status;
//    }
//
//    /**
//     *
//     * @param status
//     * The status
//     */
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    /**
//     *
//     * @return
//     * The cost
//     */
//    public String getCost() {
//        return cost;
//    }
//
//    /**
//     *
//     * @param cost
//     * The cost
//     */
//    public void setCost(String cost) {
//        this.cost = cost;
//    }
//
//    /**
//     *
//     * @return
//     * The publishDate
//     */
//    public String getPublishDate() {
//        return publishDate;
//    }
//
//    /**
//     *
//     * @param publishDate
//     * The publish_date
//     */
//    public void setPublishDate(String publishDate) {
//        this.publishDate = publishDate;
//    }
//
//    /**
//     *
//     * @return
//     * The publishTime
//     */
//    public String getPublishTime() {
//        return publishTime;
//    }
//
//    /**
//     *
//     * @param publishTime
//     * The publish_time
//     */
//    public void setPublishTime(String publishTime) {
//        this.publishTime = publishTime;
//    }
//
//    /**
//     *
//     * @return
//     * The startLoc
//     */
//    public StartLoc getStartLoc() {
//        return startLoc;
//    }
//
//    /**
//     *
//     * @param startLoc
//     * The start_loc
//     */
//    public void setStartLoc(StartLoc startLoc) {
//        this.startLoc = startLoc;
//    }
//
//    /**
//     *
//     * @return
//     * The endLoc
//     */
//    public EndLoc getEndLoc() {
//        return endLoc;
//    }
//
//    /**
//     *
//     * @param endLoc
//     * The end_loc
//     */
//    public void setEndLoc(EndLoc endLoc) {
//        this.endLoc = endLoc;
//    }
//
//    public String getSetoutDate() {
//        return setoutDate;
//    }
//
//    public void setSetoutDate(String setoutDate) {
//        this.setoutDate = setoutDate;
//    }
//
//    public String getSetoutTime() {
//        return setoutTime;
//    }
//
//    public void setSetoutTime(String setoutTime) {
//        this.setoutTime = setoutTime;
//    }

    @SerializedName("cost")
    @Expose
    private Double cost;
    @SerializedName("end_loc")
    @Expose
    private EndLoc endLoc;
    @SerializedName("id_indnet")
    @Expose
    private String idIndnet;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("publish_time")
    @Expose
    private String publishTime;
    @SerializedName("setout_time")
    @Expose
    private String setoutTime;
    @SerializedName("setout_date")
    @Expose
    private String setoutDate;
    @SerializedName("start_loc")
    @Expose
    private StartLoc startLoc;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     *
     * @return
     * The cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     * The endLoc
     */
    public EndLoc getEndLoc() {
        return endLoc;
    }

    /**
     *
     * @param endLoc
     * The end_loc
     */
    public void setEndLoc(EndLoc endLoc) {
        this.endLoc = endLoc;
    }

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
     * The publishDate
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     *
     * @param publishDate
     * The publish_date
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     *
     * @return
     * The publishTime
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     *
     * @param publishTime
     * The publish_time
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     *
     * @return
     * The setoutTime
     */
    public String getSetoutTime() {
        return setoutTime;
    }

    /**
     *
     * @param setoutTime
     * The setout_time
     */
    public void setSetoutTime(String setoutTime) {
        this.setoutTime = setoutTime;
    }

    /**
     *
     * @return
     * The setoutDate
     */
    public String getSetoutDate() {
        return setoutDate;
    }

    /**
     *
     * @param setoutDate
     * The setout_date
     */
    public void setSetoutDate(String setoutDate) {
        this.setoutDate = setoutDate;
    }

    /**
     *
     * @return
     * The startLoc
     */
    public StartLoc getStartLoc() {
        return startLoc;
    }

    /**
     *
     * @param startLoc
     * The start_loc
     */
    public void setStartLoc(StartLoc startLoc) {
        this.startLoc = startLoc;
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

    public Bundle getDataBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(Data.INDENT.IDINDENT, getIdIndnet());
        bundle.putDouble(Data.INDENT.COST, getCost());
        bundle.putString(Data.INDENT.PUBLISH_DATE, getPublishDate());
        bundle.putString(Data.INDENT.PUBLISH_TIME, getPublishTime());
        bundle.putString(Data.INDENT.SETOUTDATE, getSetoutDate());
        bundle.putString(Data.INDENT.SETOUTTIME, getSetoutTime());
        bundle.putString(Data.INDENT.STARTLOC, getStartLoc().getAddress());
        bundle.putString(Data.INDENT.SLAT, getStartLoc().getLatitude());
        bundle.putString(Data.INDENT.SLON, getStartLoc().getLongitude());
        bundle.putString(Data.INDENT.ENDLOC, getEndLoc().getAddress());
        bundle.putString(Data.INDENT.ELAT, getEndLoc().getLatitude());
        bundle.putString(Data.INDENT.ELON, getEndLoc().getLongitude());
        bundle.putInt(Data.INDENT.STATUS,getStatus());
        return bundle;
    }
}
