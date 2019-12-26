package com.yhw.common.utils.gps;

import com.alibaba.fastjson.JSONObject;
import com.yhw.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hu.yuhao
 * 调用百度webApi
 * 全球逆地理编码服务，将坐标点转为对应位置信息
 */
public class GeocoderUtils {
    //接口地址
    private static final String BAIDU_WEBAPI_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";

    //身份验证
    private static final String AK = "fTwkwE6TZ5vuKp5OXUoGlITc518OZbAZ";

    //输出格式
    private static final String OUTPUT = "json";

    private static final Logger log = LoggerFactory.getLogger(GeocoderUtils.class);

    /**
     * @param x 经度
     * @param y 纬度
     * @return String json，包含address，province，city三个字段
     */
    public static String getCurrentPosition(Double x, Double y) {
        if (x == null || y == null) {
            return parseData(null);
        } else {
            StringBuffer sb = new StringBuffer();
            String params = sb.append("ak=").append(AK).append("&")
                    .append("output=").append(OUTPUT).append("&")
                    .append("location=").append(y + "," + x).toString();
            String result = HttpUtils.sendGet(BAIDU_WEBAPI_URL, params);
            return parseData(result);
        }
    }

    /**
     * @param point 百度系经纬度字符串，通过GpsUtils获取
     * @return String json，包含address，province，city三个字段
     */
    public static String getCurrentPosition(double[] point) {
        if (point == null || point.length < 2) {
            return parseData(null);
        } else {
            return getCurrentPosition(point[0], point[1]);
        }
    }

    /**
     * 解析数据
     */
    public static String parseData(String data) {
        Map<String, String> addressInfo = new HashMap<String, String>();
        if (data == null || "".equals(data)) {
            return null;
        }
        try {
            Map<String, Object> mydata = (Map<String, Object>) JSONObject.parse(data);
            JSONObject json = new JSONObject(mydata);
            JSONObject result1 = json.getJSONObject("result");
            JSONObject location = result1.getJSONObject("addressComponent");
            String my_formatted_address = (String) result1.get("formatted_address");
            String mycity = (String) location.get("city");
            if (my_formatted_address == null || mycity == null) {
                return null;
            } else {
                addressInfo.put("address", my_formatted_address);
                addressInfo.put("city", mycity);
                return JSONObject.toJSONString(addressInfo);
            }
        } catch (Exception e) {
            log.error("百度数据解析异常！" + data);
            return null;
        }
    }
}
