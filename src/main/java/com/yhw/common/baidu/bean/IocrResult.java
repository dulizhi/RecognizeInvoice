/**
  * Copyright 2019 bejson.com 
  */
package com.yhw.common.baidu.bean;

/**
 * Auto-generated: 2019-10-31 17:6:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class IocrResult {

    private Data data;
    private int error_code;
    private String error_msg;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setError_msg(String error_msg) {
         this.error_msg = error_msg;
     }
     public String getError_msg() {
         return error_msg;
     }

}