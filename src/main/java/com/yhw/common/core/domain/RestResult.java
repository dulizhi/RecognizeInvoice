package com.yhw.common.core.domain;

import com.alibaba.fastjson.JSONObject;
import com.yhw.common.constant.GlobalConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author zhang.jiali4
 * @title RestResult
 * @description 对外接口返回类型
 * @date 2019/10/18
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class RestResult {
    private String resultCode;
    private String resultDesc;
    private Object resultData;

    public RestResult() {
    }

    public RestResult(String resultDesc) {
        this.resultCode = GlobalConstants.resultCodeMap.get(resultDesc);;
        this.resultDesc = resultDesc;
    }

    public RestResult(String resultDesc, Object resultData) {
        this.resultCode = GlobalConstants.resultCodeMap.get(resultDesc);;
        this.resultDesc = resultDesc;
        this.resultData = resultData;
    }

    public RestResult(String resultCode, String resultDesc, Object resultData) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.resultData = resultData;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Object getResultData() {
        return resultData;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("resultCode", this.resultCode);
        if (this.resultDesc != null) {
            json.put("resultDesc", this.resultDesc);
        }
        if (this.resultData == null) {
        } else if (this.resultData instanceof String) {
            json.put("resultData", (String) this.resultData);
        } else {
            json.put("resultData", this.resultData.toString());
        }
        return json.toJSONString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("resultCode", resultCode)
                .append("resultDesc", resultDesc)
                .append("resultData", resultData)
                .toString();
    }
}
