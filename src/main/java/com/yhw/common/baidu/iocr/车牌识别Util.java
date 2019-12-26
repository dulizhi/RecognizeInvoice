package com.yhw.common.baidu.iocr;

import com.yhw.common.baidu.AuthService;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.baidu.util.HttpUtil;

import java.net.URLEncoder;

public class 车牌识别Util {
    static String host="https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";

    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\证照图片\\检验标志\\4.jpg";
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
