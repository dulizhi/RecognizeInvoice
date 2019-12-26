package com.yhw.common.utils.gps;

import com.alibaba.fastjson.JSONObject;
import com.yhw.common.utils.TextUtils;
import com.yhw.common.utils.bean.GetGpsReq;
import com.yhw.common.utils.bean.GpsEncodeDate;
import com.yhw.common.utils.http.JSONHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zhang.jiali4
 * @title AdderssOperation
 * @description 用户调用获取位置工具
 * @date 2019/11/1
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class AdderssOperation {
    private static final Logger logger = LoggerFactory.getLogger(AdderssOperation.class);
    //private final static String CLOUDHOST = "http://10.198.100.66:8080/cloudapi/vehicleinfo/";
    private final static String CLOUDHOST = "http://10.198.101.78:8080/dev/cloudapi/vehicleinfo/";
    private final static String APPID = "ca9049467084931072";//身份验证
    private final static String SECRET = "7092963665C89C90F2A470F2B15726F2";//加密参数
    private final static String VERSION = "1.0.0";//默认版本1.0.0
    private final static String SOURCE = "pc";  //发送请求端类型

    /**
     * 获取单车位置
     * @param vin
     * @return 云服务返回参数
     */
    public static String getVinLocation(String vin){
        logger.info("start into operation address,vin =" + vin);
        if(TextUtils.isEmpty(vin)){
            return null;
        }
        String gpsApi = "getGpsInfo";
        String url = CLOUDHOST + gpsApi;

        GpsEncodeDate encodeDate = new GpsEncodeDate();
        encodeDate.setSign(SignUtil.sign(VERSION,APPID,SECRET));
        encodeDate.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
        encodeDate.setVin(vin);

        GetGpsReq gpsReq = new GetGpsReq();
        gpsReq.setAppId(APPID);
        gpsReq.setVersion(VERSION);
        gpsReq.setSource(SOURCE);
        gpsReq.setNonce(UUID.randomUUID().toString().replace("-", ""));
        try{
            gpsReq.setEncodeData(AESUtils.encrypt2(encodeDate.toJSONString(), SECRET));
        }catch (Exception e){
            logger.info("getGps encrypt exception，vin=" + vin);
            e.printStackTrace();
        }
        try {
            logger.info("address,param =" + gpsReq.toJSONString());
            return JSONHttpUtil.PostRequest(url, gpsReq.toJSONString());
        } catch (Exception e) {
            logger.info("getGps api exception，vin=" + vin);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param vins 车架号集合
     * @return 包含位置信息的json字符串
     * @throws java.util.concurrent.TimeoutException
     * @author hu.yuhao
     * 批量获取车辆GPS信息，一次数据量为一千条左右
     */
    public static String getLocationList(List<String> vins) {
        if (vins == null || vins.size() == 0) {
            return null;
        }
        String url = CLOUDHOST + "findGpsInfoList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, String> hashMap = new HashMap<String, String>();
        //head
        hashMap.put("version", VERSION);
        hashMap.put("appId", APPID);
        hashMap.put("secret", SECRET);

        //请求参数
        JSONObject json = new JSONObject();
        json.put("sign", SignUtil.sign(hashMap));
        json.put("timestamp", Calendar.getInstance().getTimeInMillis() / 1000);
        json.put("vinList", vins);
        paramMap.put("appId", APPID);
        try {
            paramMap.put("encodeData", AESUtils.encrypt2(json.toJSONString(), SECRET));
        } catch (Exception e) {
            logger.info("getGps encrypt exception");
            e.printStackTrace();
        }
        paramMap.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        paramMap.put("source", SOURCE);
        paramMap.put("version", VERSION);
        String params = json.toJSONString(paramMap);
        try {
            logger.info("address,param =" + params);
            return JSONHttpUtil.PostRequest(url, params);
        } catch (Exception e) {
            logger.info("getLocationInfoList api exception");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取单车里程
     * @param vin
     * @return
     */
    public static String getVinMileage(String vin){
        if(TextUtils.isEmpty(vin)){
            return null;
        }
        String url = CLOUDHOST + "getMileageInfo";

        GpsEncodeDate encodeDate = new GpsEncodeDate();
        encodeDate.setSign(SignUtil.sign(VERSION,APPID,SECRET));
        encodeDate.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
        encodeDate.setVin(vin);

        GetGpsReq gpsReq = new GetGpsReq();
        gpsReq.setAppId(APPID);
        gpsReq.setVersion(VERSION);
        gpsReq.setSource(SOURCE);
        gpsReq.setNonce(UUID.randomUUID().toString().replace("-", ""));
        try{
            gpsReq.setEncodeData(AESUtils.encrypt2(encodeDate.toJSONString(), SECRET));
        }catch (Exception e){
            logger.info("getMileageInfo encrypt exception，vin=" + vin);
            e.printStackTrace();
        }
        try {
            logger.info("address,param =" + gpsReq.toJSONString());
            return JSONHttpUtil.PostRequest(url, gpsReq.toJSONString());
        } catch (Exception e) {
            logger.info("getMileageInfo api exception，vin=" + vin);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author hu.yuhao
     * 批量获取车辆里程信息
     */
    public static String getMileageList(List<String> vins) {
        if (vins == null || vins.size() == 0) {
            return null;
        }
        String url = CLOUDHOST + "findMileageInfoList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, String> hashMap = new HashMap<String, String>();
        //head
        hashMap.put("version", VERSION);
        hashMap.put("appId", APPID);
        hashMap.put("secret", SECRET);

        //请求参数
        JSONObject json = new JSONObject();
        json.put("sign", SignUtil.sign(hashMap));
        json.put("timestamp", Calendar.getInstance().getTimeInMillis() / 1000);
        json.put("vinList", vins);
        paramMap.put("appId", APPID);
        try {
            paramMap.put("encodeData", AESUtils.encrypt2(json.toJSONString(), SECRET));
        } catch (Exception e) {
            logger.info("getGps encrypt exception");
            e.printStackTrace();
        }
        paramMap.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        paramMap.put("source", SOURCE);
        paramMap.put("version", VERSION);
        String params = json.toJSONString(paramMap);
        try {
            logger.info("address,param =" + params);
            return JSONHttpUtil.PostRequest(url, params);
        } catch (Exception e) {
            logger.info("getMileageInfoList api exception");
            e.printStackTrace();
        }
        return null;
    }
}
