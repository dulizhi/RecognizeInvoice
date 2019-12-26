package com.yhw.common.baidu.iocr;

import com.yhw.common.baidu.AuthService;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.baidu.util.HttpUtil;

import java.net.URLEncoder;

public class 通用文字识别高精度Util {
    static String host="https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    public static String get通用文字识别高精度Result(String imgStr) {
        String result=null;
        try {
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            params += "&"+URLEncoder.encode("detect_direction", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            result = HttpUtil.post(host, accessToken, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\档案识别图片\\合格证.png";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);

            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(host, accessToken, params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
