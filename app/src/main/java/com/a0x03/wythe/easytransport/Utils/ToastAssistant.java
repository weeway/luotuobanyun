package com.a0x03.wythe.easytransport.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wythe on 2016/8/2.
 */
public class ToastAssistant {
    public static void showToast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
