package com.yhw.common.utils.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhang.jiali4
 * @title GetGpsReq 调用云服务接口参数
 * @description
 * @date 2019/11/1
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class GetGpsReq {
    private String appId;
    private String version;
    private String nonce;
    private String encodeData;
    private String source;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEncodeData() {
        return encodeData;
    }

    public void setEncodeData(String encodeData) {
        this.encodeData = encodeData;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("appId", this.appId);
        json.put("version", this.version);
        json.put("nonce", this.nonce);
        json.put("encodeData", this.encodeData);
        json.put("source", this.source);
        return json.toJSONString();
    }
}
