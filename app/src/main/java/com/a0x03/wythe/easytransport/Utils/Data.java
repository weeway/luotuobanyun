package com.a0x03.wythe.easytransport.Utils;


/**
 * Created by wythe on 2016/5/20.
 */
public interface Data {
    String LOCAL_DATA = "localData";
    interface INDIVIDUAL{
        String NICKNAME = "nickname";
        String PASSWD = "passwd";
        String GENDER = "gender";
        String PHONE = "phone";
    }

    interface INDENT{
        String IDINDENT = "idIndent";
        String COST = "cost";
        String STARTLOC = "startLoc";
        String SLAT = "sLat";
        String SLON = "sLon";
        String ENDLOC = "endLoc";
        String ELAT = "eLat";
        String ELON = "eLon";
        String PUBLISH_DATE = "publishDate";
        String PUBLISH_TIME = "publishTime";
        String SETOUTDATE = "setoutDate";
        String SETOUTTIME = "setoutTime";
        String CARLEN = "carLen";
        String CARWEI = "carWei";
        String GOODSWEI = "goodsWei";
        String GOODSVOL = "goodsVol";
        String GOODSTYPE = "goodsType";
        String LOADORNOT = "isNeedUpload";
        String CARTYPE = "typeType";
        String TAKENTIME = "takeTime";
        String TAKEDATE = "takeDate";
        String DONETIME = "doneTime";
        String DONEDATE = "doneDate";
        String STATUS = "status";
        String IDPAYMENT = "idPayment";
        String PHONEDRIVER = "phoneDriver";
    }

    interface DATASETNAME{
        String BUNDLECONVERTED = "bundle";
    }
}
