package com.yhw.common.baidu.iocr;

import com.yhw.common.baidu.AuthService;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.baidu.util.HttpUtil;

import java.net.URLEncoder;

/**
 * @author wang.dingan
 * @title 合格证识别
 * @description 合格证识别
 * @date 2019/10/24
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class VehicleCertificateIocrUtil {
    static String host="https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_certificate";

    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\档案识别图片原件\\合格证.png";
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
