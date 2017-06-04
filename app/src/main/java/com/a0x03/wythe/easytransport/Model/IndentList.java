package com.a0x03.wythe.easytransport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wythe on 2016/7/28.
 */
public class IndentList {
   @SerializedName("result")
   @Expose
   private List<Result> result = new ArrayList<Result>();

   /**
    *
    * @return
    * The result
    */
   public List<Result> getResult() {
      return result;
   }

   /**
    *
    * @param result
    * The result
    */
   public void setResult(List<Result> result) {
      this.result = result;
   }
}
