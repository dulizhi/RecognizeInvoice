package com.yhw.common.utils.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhang.jiali4
 * @title GpsEncodeDate 代用云服务接口加密参数
 * @description
 * @date 2019/11/1
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class GpsEncodeDate {
    private String sign;
    private String timestamp;
    private String vin;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("sign", this.sign);
        json.put("timestamp", this.timestamp);
        json.put("vin", this.vin);
        return json.toJSONString();
    }
}
