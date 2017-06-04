package com.a0x03.wythe.easytransport.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wythe on 2016/9/10.
 */
public class LocalSharedPref {

    private static LocalSharedPref mySharedPref;
    public  SharedPreferences mData;
    private SharedPreferences.Editor editor;
    Context mContext;

    private LocalSharedPref(Context context){
        mContext = context;
        mData = context.getSharedPreferences(Data.LOCAL_DATA,
                Context.MODE_PRIVATE);
    }

    static public LocalSharedPref getInstance(Context context){
        if(null == mySharedPref){
            mySharedPref = new LocalSharedPref(context);
        }
        return mySharedPref;
    }

    public boolean isChecked(){
        return mData.getBoolean
                ("checked", false);
    }

    public void setPhone(String phone){
        editor = mData.edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    public void setPasswd(String passwd){
        editor = mData.edit();
        editor.putString("passwd", passwd);
        editor.commit();
    }

    public void setName(String name){
        editor = mData.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public void setChecked(boolean checked){
        editor = mData.edit();
        editor.putBoolean("checked", checked);
        editor.commit();
    }

    public String getPasswd(){
        return mData.getString
                ("passwd","passwd");
    }

    public String getPhone(){
        return mData.getString
                ("phone","15659377591");
    }

    public String getName(){
        return mData.getString
                ("name","Jack");
    }
}
