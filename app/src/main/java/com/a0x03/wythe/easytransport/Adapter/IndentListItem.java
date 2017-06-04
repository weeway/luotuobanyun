package com.a0x03.wythe.easytransport.Adapter;

/**
 * Created by wythe on 2016/7/22.
 */
public class IndentListItem {
    private String mPrice;
    private String mIndentCode;
    private String mDate;
    private String mTime;
    private String mStartLoc;
    private String mDest;

    public void setmPrice(String price) {
        this.mPrice = price;
    }

    public String getmPrice(){
        return this.mPrice;
    }

    public void setmIndentCode(String mIndentCode) {
        this.mIndentCode = mIndentCode;
    }

    public String getmIndentCode(){
        return this.mIndentCode;
    }

    public void setmDate (String mDate) {
        this.mDate = mDate;
    }

    public String getmDate(){
        return this.mDate;
    }

    public void setmTime (String mTime){
        this.mTime = mTime;
    }

    public String getmTime(){
        return this.mTime;
    }

    public void setmStartLoc(String mStartLoc) {
        this.mStartLoc = mStartLoc;
    }

    public String getmStartLoc(){
        return this.mStartLoc;
    }

    public void setmDest(String mDest){
        this.mDest = mDest;
    }

    public String getmDest(){
        return this.mDest;
    }
}
