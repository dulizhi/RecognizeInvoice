package com.yhw.common.utils.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.yhw.common.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * @author zhang.jiali4
 * @title OSSClientUtil
 * @description
 * @date 2019/10/23
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class OSSClientUtil {
    private static Log log = LogFactory.getLog(OSSClientUtil.class);
    private final static String END_POINT_VEHICLE = "http://oss-cn-shenzhen-internal.aliyuncs.com";
    private final static String ACCESS_KEY_ID = "LTAI8YrubOD7w4o6";
    private final static String ACCESS_KEY_SECRET = "uwStiPwFI6S1B7Z0TcPW20ixUoxXjV";
    private final static String BUCKET_NAME_VEHICLE = "oss-vehicle-wda";

    private OSSClient ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClient(END_POINT_VEHICLE, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(END_POINT_VEHICLE, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url, String filedir) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1], filedir);
        } catch (FileNotFoundException e) {
            throw new ImgException("图片上传失败");
        }
    }


    public UploadResultData uploadImg2Oss(MultipartFile file, String filedir) {
        //图片大于10M不能上传
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ImgException("图片太大无法上传");
        }
        UploadResultData uploadResultData = new UploadResultData();
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + "-" + DateUtils.dateTimeNow(DateUtils.YYYYMMDD) + substring;
        try {
            InputStream inputStream = file.getInputStream();
            String ETag = this.uploadFile2OSS(inputStream, name, filedir);
            uploadResultData.setFilename(name);
            uploadResultData.setETag(ETag);
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
        return uploadResultData;
    }

    /**
     * 获得图片url
     *
     * @param fileUrl
     * @param filedir
     * @return
     */
    public String getImgUrl(String fileUrl, String filedir) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @param filedir
     * @return
     */
    public String getImagKey(String fileUrl, String filedir) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return (filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName, String filedir) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKET_NAME_VEHICLE, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public String uploadAPK2OSS(InputStream instream, String fileName, String filedir) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("attachment;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKET_NAME_VEHICLE, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".apk") ||
                FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/x-download";
        }
        if (FilenameExtension.equalsIgnoreCase(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (FilenameExtension.equalsIgnoreCase(".xls")) {
            return "application/vnd.ms-excel";
        }

        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BUCKET_NAME_VEHICLE, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    public OSSObject getOssObject(String key){
        return ossClient.getObject(BUCKET_NAME_VEHICLE,key);
    }

    public String getIcarUrl(String key){
        return "http://icar.bydauto.com.cn/apis/oss"+"?key=" + key;
    }

    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest){
        return ossClient.deleteObjects(deleteObjectsRequest);
    }

    //key：filedir + fileName
    public void deleteVehicleObject(String key){
        ossClient.deleteObject(BUCKET_NAME_VEHICLE, key);
    }
}

