package com.yhw.common.baidu.iocr;

import com.yhw.common.baidu.AuthService;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.baidu.util.HttpUtil;

import java.net.URLEncoder;

/**
 * @author wang.dingan
 * @title 行驶证识别
 * @description 行驶证识别
 * @date 2019/10/24
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class VehicleLicenseIocrUtil {
    static String host="https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license";

    /**
     * @param imgStr 图片Base64编码
     * @param vehicle_license_side 正面：front 反面：back
     * @return
     */
    public static String getVehicleLicense(String imgStr,String vehicle_license_side){
        String result=null;
        try {
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            params += "&"+URLEncoder.encode("detect_direction", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");
            params += "&"+URLEncoder.encode("vehicle_license_side", "UTF-8") + "=" + URLEncoder.encode(vehicle_license_side, "UTF-8");

            String accessToken = AuthService.getAuth();
            result = HttpUtil.post(host, accessToken, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\档案识别图片原件\\行驶证主页.png";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);

            String result = getVehicleLicense(imgStr,"front");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
