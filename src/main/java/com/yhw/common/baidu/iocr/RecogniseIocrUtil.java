package com.yhw.common.baidu.iocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yhw.common.baidu.AuthService;
import com.yhw.common.baidu.bean.Data;
import com.yhw.common.baidu.bean.IocrResult;
import com.yhw.common.baidu.bean.Ret;
import com.yhw.common.baidu.bean.invoice.DeductionInvoiceInfo;
import com.yhw.common.baidu.bean.invoice.InvoiceInfo;
import com.yhw.common.baidu.bean.invoice.TaxInvoiceInfo;
import com.yhw.common.baidu.util.FormatUtil;
import com.yhw.common.baidu.util.HttpUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.List;

import static com.yhw.common.baidu.util.IocrConstants.*;
import static com.yhw.common.constant.GlobalConstants.*;

/**
 * @author wang.dingan
 * @title 自定义模板识别
 * @description 自定义模板识别
 * @date 2019/10/24
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class RecogniseIocrUtil {
    static String recogniseUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";
    static Logger logger = LoggerFactory.getLogger(RecogniseIocrUtil.class);

    /**
     * @param imgStr      图片Base64编码
     * @param licenseType 证照类型
     * @return
     */
    private static String getRecogniseResult(String imgStr, String licenseType) {
        String result = null;
        try {
            /**
             * 行驶证主页
             */
            if (LICENSE_VEHICLE_HOME.equalsIgnoreCase(licenseType)) {
                result = VehicleLicenseIocrUtil.getVehicleLicense(imgStr, "front");
            } else if (LICENSE_VEHICLE_SUB.equalsIgnoreCase(licenseType)) {
                result = VehicleLicenseIocrUtil.getVehicleLicense(imgStr, "back");
            } else if(LICENSE_NETCAR_CERTIFICATE.equalsIgnoreCase(licenseType)){
                String classifierParams = "classifierId=" + classifierIdMap.get(licenseType) + "&image=" + URLEncoder.encode(imgStr, "UTF-8");
                String accessToken = AuthService.getAuth();
                result = HttpUtil.post(recogniseUrl, accessToken, classifierParams);
            } else {
                String recogniseParams = "templateSign=" + templateSignMap.get(licenseType) + "&image=" + URLEncoder.encode(imgStr, "UTF-8");
                String accessToken = AuthService.getAuth();
                result = HttpUtil.post(recogniseUrl, accessToken, recogniseParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发票识别
     *
     * @param imgStr 图片Base64编码
     * @return
     */
    public static InvoiceInfo getInvoiceResult(String imgStr) {
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        String result = getRecogniseResult(imgStr, LICENSE_INVOICE);
        IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

        if (iocrResult != null) {
            if (iocrResult.getError_code() == 0) {
                Data data = iocrResult.getData();
                List<Ret> retList = data.getRet();
                if (retList != null && !retList.isEmpty()) {
                    for (Ret ret : retList) {
                        if (证照_发票_开票日期.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set开票日期(ret.getWord());
                        }
                        if (证照_发票_发票号码.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set发票号码(ret.getWord());
                        }
                        if (证照_发票_发票代码.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set发票代码(ret.getWord());
                        }
                        if (证照_发票_购买方名称.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set购买方名称(ret.getWord());
                        }
                        if (证照_发票_购买方号码.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set购买方号码(ret.getWord());
                        }
                        if (证照_发票_纳税人识别号.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set纳税人识别号(ret.getWord());
                        }
                        if (证照_发票_车辆类型.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set车辆类型(ret.getWord());
                        }
                        if (证照_发票_厂牌型号.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set厂牌型号(ret.getWord());
                        }
                        if (证照_发票_合格证号.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set合格证号(ret.getWord());
                        }
                        if (证照_发票_发动机号码.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set发动机号码(ret.getWord());
                        }
                        if (证照_发票_车架号.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set车架号(FormatUtil.车架号_提取(ret.getWord()));
                        }
                        if (证照_发票_销货单位名称.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set销货单位名称(ret.getWord());
                        }
                        if (证照_发票_机器编码.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set机器编码(ret.getWord());
                        }
                        if (证照_发票_税价合计.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set价税合计(ret.getWord());
                        }
                        if (证照_发票_税价合计小写.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set价税合计小写(ret.getWord());
                        }
                        if (证照_发票_税额.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set税额(ret.getWord());
                        }
                        if (证照_发票_不含税价格.equalsIgnoreCase(ret.getWord_name())) {
                            invoiceInfo.set不含税价格(ret.getWord());
                        }
                    }
                }
            } else {
                logger.info("getInvoiceResult error_msg:" + iocrResult.getError_msg());
            }
        }
        logger.info(JSONObject.toJSONString(invoiceInfo));
        return invoiceInfo;
    }

    /**
     * 报税联识别
     *
     * @param imgStr 图片Base64编码
     * @return
     */
    public static TaxInvoiceInfo getTaxInvoiceResult(String imgStr) {
        TaxInvoiceInfo taxInvoiceInfo = new TaxInvoiceInfo();
        String result = getRecogniseResult(imgStr, LICENSE_TAX);
        IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

        if (iocrResult != null) {
            if (iocrResult.getError_code() == 0) {
                Data data = iocrResult.getData();
                List<Ret> retList = data.getRet();
                if (retList != null && !retList.isEmpty()) {
                    for (Ret ret : retList) {
                        if (证照_发票_开票日期.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set开票日期(ret.getWord());
                        }
                        if (证照_发票_发票号码.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set发票号码(ret.getWord());
                        }
                        if (证照_发票_发票代码.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set发票代码(ret.getWord());
                        }
                        if (证照_发票_购买方名称.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set购买方名称(ret.getWord());
                        }
                        if (证照_发票_购买方号码.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set购买方号码(ret.getWord());
                        }
                        if (证照_发票_纳税人识别号.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set纳税人识别号(ret.getWord());
                        }
                        if (证照_发票_车辆类型.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set车辆类型(ret.getWord());
                        }
                        if (证照_发票_厂牌型号.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set厂牌型号(ret.getWord());
                        }
                        if (证照_发票_合格证号.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set合格证号(ret.getWord());
                        }
                        if (证照_发票_发动机号码.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set发动机号码(ret.getWord());
                        }
                        if (证照_发票_车架号.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set车架号(FormatUtil.车架号_提取(ret.getWord()));
                        }
                        if (证照_发票_销货单位名称.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set销货单位名称(ret.getWord());
                        }
                        if (证照_发票_机器编码.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set机器编码(ret.getWord());
                        }
                        if (证照_发票_税价合计.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set价税合计(ret.getWord());
                        }
                        if (证照_发票_税价合计小写.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set价税合计小写(ret.getWord());
                        }
                        if (证照_发票_税额.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set税额(ret.getWord());
                        }
                        if (证照_发票_不含税价格.equalsIgnoreCase(ret.getWord_name())) {
                            taxInvoiceInfo.set不含税价格(ret.getWord());
                        }
                    }
                }
            } else {
                logger.info("getTaxInvoiceResult error_msg:" + iocrResult.getError_msg());
            }
        }
        logger.info(JSONObject.toJSONString(taxInvoiceInfo));
        return taxInvoiceInfo;
    }

    /**
     * 抵扣联识别
     *
     * @param imgStr 图片Base64编码
     * @return
     */
    public static DeductionInvoiceInfo getDeductionInvoiceResult(String imgStr) {
        DeductionInvoiceInfo deductionInvoiceInfo = new DeductionInvoiceInfo();
        String result = getRecogniseResult(imgStr, LICENSE_DEDUCT);
        IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

        if (iocrResult != null) {
            if (iocrResult.getError_code() == 0) {
                Data data = iocrResult.getData();
                List<Ret> retList = data.getRet();
                if (retList != null && !retList.isEmpty()) {
                    for (Ret ret : retList) {
                        if (证照_发票_开票日期.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set开票日期(ret.getWord());
                        }
                        if (证照_发票_发票号码.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set发票号码(ret.getWord());
                        }
                        if (证照_发票_发票代码.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set发票代码(ret.getWord());
                        }
                        if (证照_发票_购买方名称.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set购买方名称(ret.getWord());
                        }
                        if (证照_发票_购买方号码.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set购买方号码(ret.getWord());
                        }
                        if (证照_发票_纳税人识别号.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set纳税人识别号(ret.getWord());
                        }
                        if (证照_发票_车辆类型.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set车辆类型(ret.getWord());
                        }
                        if (证照_发票_厂牌型号.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set厂牌型号(ret.getWord());
                        }
                        if (证照_发票_合格证号.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set合格证号(ret.getWord());
                        }
                        if (证照_发票_发动机号码.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set发动机号码(ret.getWord());
                        }
                        if (证照_发票_车架号.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set车架号(FormatUtil.车架号_提取(ret.getWord()));
                        }
                        if (证照_发票_销货单位名称.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set销货单位名称(ret.getWord());
                        }
                        if (证照_发票_机器编码.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set机器编码(ret.getWord());
                        }
                        if (证照_发票_税价合计.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set价税合计(ret.getWord());
                        }
                        if (证照_发票_税价合计小写.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set价税合计小写(ret.getWord());
                        }
                        if (证照_发票_税额.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set税额(ret.getWord());
                        }
                        if (证照_发票_不含税价格.equalsIgnoreCase(ret.getWord_name())) {
                            deductionInvoiceInfo.set不含税价格(ret.getWord());
                        }
                    }
                }
            } else {
                logger.info("getDeductionInvoiceResult error_msg:" + iocrResult.getError_msg());
            }
        }
        logger.info(JSONObject.toJSONString(deductionInvoiceInfo));
        return deductionInvoiceInfo;
    }

}
/*
 *//**
 * 行驶证主页识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static LicenseFrontInfo getLicenseFrontResult(String imgStr) {
        LicenseFrontInfo licenseFrontInfo = new LicenseFrontInfo();
        String result = getRecogniseResult(imgStr, LICENSE_VEHICLE_HOME);
        LicenseIocrResult licenseIocrResult = JSON.parseObject(result, LicenseIocrResult.class);

        if (licenseIocrResult != null) {
            Words_result words_result = licenseIocrResult.getWords_result();
            if (words_result != null) {
                licenseFrontInfo.set车辆识别代号(words_result.get车辆识别代号().getWords());
                licenseFrontInfo.set住址(words_result.get住址().getWords());
                licenseFrontInfo.set品牌型号(words_result.get品牌型号().getWords());
                licenseFrontInfo.set发证日期(words_result.get发证日期().getWords());
                licenseFrontInfo.set发动机号码(words_result.get发动机号码().getWords());
                licenseFrontInfo.set所有人(words_result.get所有人().getWords());
                licenseFrontInfo.set号牌号码(words_result.get号牌号码().getWords());
                licenseFrontInfo.set注册日期(words_result.get注册日期().getWords());
                licenseFrontInfo.set使用性质(words_result.get使用性质().getWords());
                licenseFrontInfo.set车辆类型(words_result.get车辆类型().getWords());
            }
        }
        return licenseFrontInfo;
    }

    *//**
 * 行驶证副页识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static LicenseBackInfo getLicenseBackResult(String imgStr) {
        LicenseBackInfo licenseBackInfo = new LicenseBackInfo();
        logger.info("getLicenseBackResult start");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getRecogniseResult(imgStr, LICENSE_VEHICLE_SUB);
                LicenseIocrResult licenseIocrResult = JSON.parseObject(result, LicenseIocrResult.class);

                if (licenseIocrResult != null) {
                    Words_result words_result = licenseIocrResult.getWords_result();
                    if (words_result != null) {
                        String v_plate = words_result.get号牌号码().getWords();
                        if (!TextUtils.isEmpty(v_plate) && (TextUtils.isEmpty(licenseBackInfo.get号牌号码()) || v_plate.length() > licenseBackInfo.get号牌号码().length())) {
                            licenseBackInfo.set号牌号码(v_plate);
                        }
                        if(!TextUtils.isEmpty(words_result.get整备质量().getWords())) {
                            licenseBackInfo.set整备质量(words_result.get整备质量().getWords());
                        }
                        if(!TextUtils.isEmpty(words_result.get核定载人数().getWords())) {
                            licenseBackInfo.set核定载人数(words_result.get核定载人数().getWords());
                        }
                        if(!TextUtils.isEmpty(words_result.get外廓尺寸().getWords())) {
                            licenseBackInfo.set外廓尺寸(words_result.get外廓尺寸().getWords());
                        }
                        if(!TextUtils.isEmpty(words_result.get总质量().getWords())) {
                            licenseBackInfo.set总质量(words_result.get总质量().getWords());
                        }
                        String v_no = words_result.get档案编号().getWords();
                        if (!TextUtils.isEmpty(v_no)&&(TextUtils.isEmpty(licenseBackInfo.get档案编号())||v_no.length()>licenseBackInfo.get档案编号().length())) {
                            licenseBackInfo.set档案编号(v_no);
                        }
                        if(!TextUtils.isEmpty(words_result.get检验记录().getWords())) {
                            //licenseBackInfo.set检验记录(words_result.get检验记录().getWords());
                        }
                        if(TextUtils.isEmpty(licenseBackInfo.get有效期())||licenseBackInfo.get有效期().startsWith("--")) {
                            //licenseBackInfo.set有效期(FormatUtil.合格证_有效期_提取(words_result.get检验记录().getWords()));
                        }
                        if(!TextUtils.isEmpty(words_result.get燃油类型().getWords())) {
                            licenseBackInfo.set燃油类型(words_result.get燃油类型().getWords());
                        }
                        if(!TextUtils.isEmpty(words_result.get备注().getWords())) {
                            licenseBackInfo.set备注(words_result.get备注().getWords());
                        }
                    }
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject != null) {
                    String words_result = jsonObject.getString("words_result");
                    if(TextUtils.isEmpty(licenseBackInfo.get编号())||licenseBackInfo.get编号().startsWith("--")) {
                        String 编号=FormatUtil.行驶证副本_编号_提取(words_result);
                        if(!TextUtils.isEmpty(编号)) {
                            licenseBackInfo.set编号(编号);
                        }
                    }
                    if(TextUtils.isEmpty(licenseBackInfo.get档案编号())||licenseBackInfo.get档案编号().startsWith("--")) {
                        String 档案编号=FormatUtil.行驶证副本_档案编号_提取(words_result);
                        if(!TextUtils.isEmpty(档案编号)) {
                            licenseBackInfo.set档案编号(档案编号);
                        }
                    }
                    if(TextUtils.isEmpty(licenseBackInfo.get号牌号码())||licenseBackInfo.get号牌号码().startsWith("--")) {
                        String 号牌号码=FormatUtil.合格证_号码号牌_提取(words_result);
                        if(!TextUtils.isEmpty(号牌号码)) {
                            licenseBackInfo.set号牌号码(号牌号码);
                        }
                    }
                    String num = FormatUtil.合格证_有效期_提取(words_result);
                    if (!TextUtils.isEmpty(num)) {
                        licenseBackInfo.set有效期(num);
                    }
                    通用文字识别高精度Result 识别Result = JSON.parseObject(result, 通用文字识别高精度Result.class);
                    if (识别Result != null) {
                        List<WordsResult> wordsResults = 识别Result.getWords_result();
                        if (wordsResults != null && !wordsResults.isEmpty()) {
                            words_result="";
                            for (WordsResult wordsResult : wordsResults) {
                                words_result+=wordsResult.getWords();
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(words_result)) {
                        if(TextUtils.isEmpty(licenseBackInfo.get整备质量())||licenseBackInfo.get整备质量().startsWith("--")) {
                            String 整备质量=FormatUtil.行驶证副本_整备质量_提取(words_result);
                            if(!TextUtils.isEmpty(整备质量)) {
                                licenseBackInfo.set整备质量(整备质量);
                            }
                        }
                        if(TextUtils.isEmpty(licenseBackInfo.get核定载人数())||licenseBackInfo.get核定载人数().startsWith("--")) {
                            String 核定载人数=FormatUtil.行驶证副本_核定载人数_提取(words_result);
                            if(!TextUtils.isEmpty(核定载人数)) {
                                licenseBackInfo.set核定载人数(核定载人数);
                            }
                        }
                        if(TextUtils.isEmpty(licenseBackInfo.get外廓尺寸())||licenseBackInfo.get外廓尺寸().startsWith("--")) {
                            String 外廓尺寸=FormatUtil.行驶证副本_外廓尺寸_提取(words_result);
                            if(!TextUtils.isEmpty(外廓尺寸)) {
                                licenseBackInfo.set外廓尺寸(外廓尺寸);
                            }
                        }
                        if(TextUtils.isEmpty(licenseBackInfo.get总质量())||licenseBackInfo.get总质量().startsWith("--")) {
                            String 总质量=FormatUtil.行驶证副本_总质量_提取(words_result);
                            if(!TextUtils.isEmpty(总质量)) {
                                licenseBackInfo.set总质量(总质量);
                            }
                        }
                    } else {
                        logger.info("getLicenseBackResult fail:");
                    }
                }
            }
        });
        t2.start();
        while (true) {
            if (t1.getState().equals(Thread.State.TERMINATED) && t2.getState().equals(Thread.State.TERMINATED)) {
                break; // 两个线程都结束，退出循环
            }
        }
        logger.info(JSONObject.toJSONString(licenseBackInfo));
        return licenseBackInfo;
    }

    *//**
 * 保险单识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static InsuranceInfo getInsuranceResult(String imgStr) {
        InsuranceInfo insuranceInfo = new InsuranceInfo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getRecogniseResult(imgStr, LICENSE_INSURE);
                IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

                if (iocrResult != null) {
                    if (iocrResult.getError_code() == 0) {
                        Data data = iocrResult.getData();
                        List<Ret> retList = data.getRet();
                        if (retList != null && !retList.isEmpty()) {
                            for (Ret ret : retList) {
                                if (证照_保险标识_保险单号.equalsIgnoreCase(ret.getWord_name())) {
                                    String no = FormatUtil.保险标识_保险单号_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(no)) {
                                        insuranceInfo.set保险单号(no);
                                    }
                                }
                                if (证照_保险标识_保险公司.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set保险公司(ret.getWord());
                                }
                                if (证照_保险单_被保险人.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set被保险人(ret.getWord());
                                }
                                if (证照_保险单_保险期间.equalsIgnoreCase(ret.getWord_name())) {
                                    //insuranceInfo.set保险期间(ret.getWord());
                                }
                                if (证照_保险单_使用性质.equalsIgnoreCase(ret.getWord_name())) {
                                    //insuranceInfo.set使用性质(ret.getWord());
                                }
                                if (证照_保险单_机动车种类.equalsIgnoreCase(ret.getWord_name())) {
                                    //insuranceInfo.set机动车种类(ret.getWord());
                                }
                                if (证照_保险单_车架号.equalsIgnoreCase(ret.getWord_name())) {
                                    String vin = FormatUtil.车架号_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(vin) && TextUtils.isEmpty(insuranceInfo.get车架号())) {
                                        insuranceInfo.set车架号(vin);
                                    }
                                }
                                if (证照_保险单_发动机号.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set发动机号(ret.getWord());
                                }
                                if (证照_保险单_登记日期.equalsIgnoreCase(ret.getWord_name())) {
                                    String regDate = DateUtils.fomatDate(ret.getWord());
                                    if (!TextUtils.isEmpty(regDate)) {
                                        insuranceInfo.set登记日期(regDate);
                                    } else {
                                        if (!TextUtils.isEmpty(insuranceInfo.get签单日期())) {
                                            insuranceInfo.set登记日期(insuranceInfo.get签单日期());
                                        }
                                    }
                                }
                                if (证照_保险单_公司名称.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set公司名称(ret.getWord());
                                }
                                if (证照_保险单_公司地址.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set公司地址(ret.getWord());
                                }
                                if (证照_保险单_签单日期.equalsIgnoreCase(ret.getWord_name())) {
                                    String signDate = DateUtils.fomatDate(ret.getWord());
                                    if (!TextUtils.isEmpty(signDate)) {
                                        insuranceInfo.set签单日期(signDate);
                                        if (!TextUtils.isEmpty(insuranceInfo.get登记日期())) {
                                            insuranceInfo.set登记日期(signDate);
                                        }
                                    }
                                }
                                if (证照_保险单_厂牌型号.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceInfo.set厂牌型号(ret.getWord());
                                }
                                if (证照_保险标识_保险期间.equalsIgnoreCase(ret.getWord_name())) {
                                    String[] insurancePeriodDate = FormatUtil.保险_起止期_提取(ret.getWord());
                                    if (insurancePeriodDate != null && insurancePeriodDate.length > 1) {
                                        if (TextUtils.isEmpty(insuranceInfo.get保险起期())) {
                                            insuranceInfo.set保险起期(insurancePeriodDate[0]);
                                        }
                                        if (TextUtils.isEmpty(insuranceInfo.get保险止期())) {
                                            insuranceInfo.set保险止期(insurancePeriodDate[1]);
                                        }
                                        if (!TextUtils.isEmpty(insurancePeriodDate[0]) && !TextUtils.isEmpty(insurancePeriodDate[1])) {
                                            if (TextUtils.isEmpty(insuranceInfo.get保险期间())) {
                                                insuranceInfo.set保险期间(ret.getWord());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("getInsuranceResult error_msg:" + iocrResult.getError_msg());
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject != null) {
                    String words_result = jsonObject.getString("words_result");
                    if (!TextUtils.isEmpty(words_result)) {
                        String no = FormatUtil.保险标识_保险单号_提取(words_result);
                        if (!TextUtils.isEmpty(no)) {
                            insuranceInfo.set保险单号(no);
                        }
                        通用文字识别高精度Result 识别Result = JSON.parseObject(result, 通用文字识别高精度Result.class);
                        if (识别Result != null) {
                            List<WordsResult> wordsResults = 识别Result.getWords_result();
                            if (wordsResults != null && !wordsResults.isEmpty()) {
                                for (WordsResult wordsResult : wordsResults) {
                                    if (wordsResult.getWords().contains(证照_保险单_使用性质)) {
                                        String prop = FormatUtil.网约证_属性_提取(wordsResult.getWords(), 证照_保险单_使用性质);
                                        if (!TextUtils.isEmpty(prop) && TextUtils.isEmpty(insuranceInfo.get使用性质())) {
                                            insuranceInfo.set使用性质(prop);
                                        }
                                    }
                                    if (wordsResult.getWords().contains(证照_保险单_机动车种类)) {
                                        String vehicleType = FormatUtil.网约证_属性_提取(wordsResult.getWords(), 证照_保险单_机动车种类);
                                        if (!TextUtils.isEmpty(vehicleType) && vehicleType.contains(证照_保险单_使用性质)) {
                                            String[] vehicleTypes = vehicleType.split(证照_保险单_使用性质);
                                            if (vehicleTypes != null && vehicleTypes.length > 1) {
                                                vehicleType = vehicleTypes[0];
                                            }
                                        }
                                        if (!TextUtils.isEmpty(vehicleType)) {
                                            insuranceInfo.set机动车种类(vehicleType);
                                        }
                                    }
                                    if (wordsResult.getWords().contains(证照_保险单_保险期间)) {
                                        String[] insurancePeriodDate = FormatUtil.保险_起止期_提取(wordsResult.getWords());
                                        if (insurancePeriodDate != null && insurancePeriodDate.length > 1) {
                                            if (TextUtils.isEmpty(insuranceInfo.get保险起期())) {
                                                insuranceInfo.set保险起期(insurancePeriodDate[0]);
                                            }
                                            if (TextUtils.isEmpty(insuranceInfo.get保险止期())) {
                                                insuranceInfo.set保险止期(insurancePeriodDate[1]);
                                            }
                                            if (!TextUtils.isEmpty(insurancePeriodDate[0]) && !TextUtils.isEmpty(insurancePeriodDate[1])) {
                                                if (TextUtils.isEmpty(insuranceInfo.get保险期间())) {
                                                    insuranceInfo.set保险期间(wordsResult.getWords());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        String vin = FormatUtil.车架号_提取(words_result);
                        if (!TextUtils.isEmpty(vin)) {
                            insuranceInfo.set车架号(vin);
                        }
                    } else {
                        logger.info("getInsuranceResult fail:");
                    }
                }
            }
        });
        t2.start();
        while (true) {
            if (t1.getState().equals(Thread.State.TERMINATED) && t2.getState().equals(Thread.State.TERMINATED)) {
                break; // 两个线程都结束，退出循环
            }
        }
        logger.info(JSONObject.toJSONString(insuranceInfo));
        return insuranceInfo;
    }

    *//**
 * 保险标识识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static InsuranceSignInfo getInsuranceSignResult(String imgStr) {
        InsuranceSignInfo insuranceSignInfo = new InsuranceSignInfo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getRecogniseResult(imgStr, LICENSE_INSURE_SIGN);
                IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

                if (iocrResult != null) {
                    if (iocrResult.getError_code() == 0) {
                        Data data = iocrResult.getData();
                        List<Ret> retList = data.getRet();
                        if (retList != null && !retList.isEmpty()) {
                            for (Ret ret : retList) {
                                if (证照_保险标识_保险单号.equalsIgnoreCase(ret.getWord_name())) {
                                    String no = FormatUtil.保险标识_保险单号_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(no)) {
                                        insuranceSignInfo.set保险单号(no);
                                    }
                                }
                                if (证照_保险标识_号码号牌.equalsIgnoreCase(ret.getWord_name())) {
                                    String plate = FormatUtil.保险标识_号码号牌_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(plate)) {
                                        insuranceSignInfo.set号码号牌(plate);
                                    }
                                }
                                if (证照_保险标识_保险公司.equalsIgnoreCase(ret.getWord_name())) {
                                    if (!TextUtils.isEmpty(ret.getWord())) {
                                        insuranceSignInfo.set保险公司(ret.getWord());
                                    }
                                }
                                if (证照_保险标识_保险期间.equalsIgnoreCase(ret.getWord_name())) {
                                    insuranceSignInfo.set保险期间(ret.getWord());
                                    String[] insurancePeriodDate = FormatUtil.保险_起止期_提取(ret.getWord());
                                    if (insurancePeriodDate != null && insurancePeriodDate.length > 1) {
                                        insuranceSignInfo.set保险起期(insurancePeriodDate[0]);
                                        insuranceSignInfo.set保险止期(insurancePeriodDate[1]);
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("getInsuranceSignResult error_msg:" + iocrResult.getError_msg());
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(insuranceSignInfo.get保险起期()) || TextUtils.isEmpty(insuranceSignInfo.get保险止期()) || Integer.valueOf(insuranceSignInfo.get保险起期()) > Integer.valueOf(insuranceSignInfo.get保险止期()) || !insuranceSignInfo.get保险公司().endsWith("承保")) {
                    String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject != null) {
                        String words_result = jsonObject.getString("words_result");
                        通用文字识别高精度Result 识别Result = JSON.parseObject(result, 通用文字识别高精度Result.class);
                        if (识别Result != null) {
                            List<WordsResult> wordsResults = 识别Result.getWords_result();
                            if (wordsResults != null && !wordsResults.isEmpty()) {
                                for (WordsResult wordsResult : wordsResults) {
                                    if (wordsResult.getWords().endsWith("承保")) {
                                        insuranceSignInfo.set保险公司(wordsResult.getWords());
                                    }
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(words_result)) {
                            if (TextUtils.isEmpty(insuranceSignInfo.get保险单号())) {
                                String no = FormatUtil.保险标识_保险单号_提取(words_result);
                                if (!TextUtils.isEmpty(no)) {
                                    insuranceSignInfo.set保险单号(no);
                                }
                            }
                            if (TextUtils.isEmpty(insuranceSignInfo.get号码号牌())) {
                                String plate = FormatUtil.保险标识_号码号牌_提取(words_result);
                                if (!TextUtils.isEmpty(plate)) {
                                    insuranceSignInfo.set号码号牌(plate);
                                }
                            }
                            String[] insurancePeriodDate = FormatUtil.保险_起止期_提取(words_result);
                            if (insurancePeriodDate != null && insurancePeriodDate.length > 1) {
                                insuranceSignInfo.set保险起期(insurancePeriodDate[0]);
                                insuranceSignInfo.set保险止期(insurancePeriodDate[1]);
                            }
                        } else {
                            logger.info("getInsuranceSignResult fail:");
                        }
                    }
                }
            }
        });
        t2.start();
        while (true) {
            if (t1.getState().equals(Thread.State.TERMINATED) && t2.getState().equals(Thread.State.TERMINATED)) {
                break; // 两个线程都结束，退出循环
            }
        }
        logger.info(JSONObject.toJSONString(insuranceSignInfo));
        return insuranceSignInfo;
    }

    *//**
 * 完税证明识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static TaxCertificateInfo getTaxCertificateResult(String imgStr) {
        TaxCertificateInfo taxCertificateInfo = new TaxCertificateInfo();
        String result = getRecogniseResult(imgStr, LICENSE_DUTY_PAID);
        IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

        if (iocrResult != null) {
            if (iocrResult.getError_code() == 0) {
                Data data = iocrResult.getData();
                List<Ret> retList = data.getRet();
                if (retList != null && !retList.isEmpty()) {
                    for (Ret ret : retList) {
                        if (证照_完税证明_车架号.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set车架号(FormatUtil.车架号_提取(ret.getWord()));
                        }
                        if (证照_完税证明_纳税人.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set纳税人(ret.getWord());
                        }
                        if (证照_完税证明_发动机号.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set发动机号(ret.getWord());
                        }
                        if (证照_完税证明_厂牌型号.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set厂牌型号(ret.getWord());
                        }
                        if (证照_完税证明_经办人.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set经办人(ret.getWord());
                        }
                        if (证照_完税证明_征收机关名称.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set征收机关名称(ret.getWord());
                        }
                        if (证照_完税证明_编号.equalsIgnoreCase(ret.getWord_name())) {
                            taxCertificateInfo.set编号(ret.getWord());
                        }
                    }
                }
            } else {
                logger.info("getTaxCertificateResult error_msg:" + iocrResult.getError_msg());
            }
        }
        return taxCertificateInfo;
    }

    *//**
 * 合格证识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static CertificateInfo getCertificateResult(String imgStr) {
        CertificateInfo certificateInfo = new CertificateInfo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getRecogniseResult(imgStr, LICENSE_QUALITY);
                IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

                if (iocrResult != null) {
                    if (iocrResult.getError_code() == 0) {
                        Data data = iocrResult.getData();
                        List<Ret> retList = data.getRet();
                        if (retList != null && !retList.isEmpty()) {
                            for (Ret ret : retList) {
                                if (证照_合格证_合格标志号.equalsIgnoreCase(ret.getWord_name())) {
                                    String no = FormatUtil.合格证_标志号_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(no)) {
                                        certificateInfo.set合格标志号(no);
                                    }
                                }
                                if (证照_合格证_有效期.equalsIgnoreCase(ret.getWord_name())) {
                                    String period = FormatUtil.合格证_有效期_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(period)) {
                                        certificateInfo.set有效期(period);
                                    }
                                    String plate = FormatUtil.合格证_号码号牌_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(plate)) {
                                        certificateInfo.set号牌号码(plate);
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("getCertificateResult error_msg:" + iocrResult.getError_msg());
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(certificateInfo.get合格标志号())) {
                    String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject != null) {
                        String words_result = jsonObject.getString("words_result");
                        if (!TextUtils.isEmpty(words_result)) {
                            String no = FormatUtil.合格证_标志号_提取(words_result);
                            if (!TextUtils.isEmpty(no)) {
                                certificateInfo.set合格标志号(no);
                            }
                            String period = FormatUtil.合格证_有效期_提取(words_result);
                            if (!TextUtils.isEmpty(period)) {
                                certificateInfo.set有效期(period);
                            }
                            String plate = FormatUtil.合格证_号码号牌_提取(words_result);
                            if (!TextUtils.isEmpty(plate)) {
                                certificateInfo.set号牌号码(plate);
                            }
                        } else {
                            logger.info("getCertificateResult fail:");
                        }
                    }
                }
            }
        });
        t2.start();
        while (true) {
            if (t1.getState().equals(Thread.State.TERMINATED) && t2.getState().equals(Thread.State.TERMINATED)) {
                break; // 两个线程都结束，退出循环
            }
        }
        logger.info(JSONObject.toJSONString(certificateInfo));
        return certificateInfo;
    }

    *//**
 * 网约车证识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static NetCarCertificateInfo getNetCarCertificateResult(String imgStr) {
        NetCarCertificateInfo netCarCertificateInfo = new NetCarCertificateInfo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getRecogniseResult(imgStr, LICENSE_NETCAR_CERTIFICATE);
                IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

                if (iocrResult != null) {
                    if (iocrResult.getError_code() != 0) {
                        result = getRecogniseResult(imgStr, LICENSE_NETCAR_CERTIFICATE2);
                        iocrResult = JSON.parseObject(result, IocrResult.class);
                    }
                    if (iocrResult.getError_code() == 0) {
                        Data data = iocrResult.getData();
                        List<Ret> retList = data.getRet();
                        if (retList != null && !retList.isEmpty()) {
                            for (Ret ret : retList) {
                                if (证照_网约车证_交运管许可号.equalsIgnoreCase(ret.getWord_name())) {
                                    String no = FormatUtil.网约证_许可号_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(no) && (TextUtils.isEmpty(netCarCertificateInfo.get交运管许可号())||(no.length()>netCarCertificateInfo.get交运管许可号().length()))) {
                                        netCarCertificateInfo.set交运管许可号(no);
                                    }
                                }
                                if (证照_网约车证_车辆号牌.equalsIgnoreCase(ret.getWord_name())) {
                                    String plate = FormatUtil.合格证_号码号牌_提取(ret.getWord());
                                    if (!TextUtils.isEmpty(plate) && (TextUtils.isEmpty(netCarCertificateInfo.get车辆号牌())||(plate.length()>=netCarCertificateInfo.get车辆号牌().length()))) {
                                        netCarCertificateInfo.set车辆号牌(plate);
                                    }
                                }
                                if (证照_网约车证_车辆所有人.equalsIgnoreCase(ret.getWord_name())) {
                                    String owner=ret.getWord();
                                    if (!TextUtils.isEmpty(owner)&&(TextUtils.isEmpty(netCarCertificateInfo.get车辆所有人())||(owner.length()>netCarCertificateInfo.get车辆所有人().length()))) {
                                        netCarCertificateInfo.set车辆所有人(owner);
                                    }
                                }
                                if (证照_网约车证_车辆类型.equalsIgnoreCase(ret.getWord_name())) {
                                    String type = ret.getWord();
                                    if (!TextUtils.isEmpty(type) && (TextUtils.isEmpty(netCarCertificateInfo.get车辆类型())||(type.length()>netCarCertificateInfo.get车辆类型().length()))) {
                                        netCarCertificateInfo.set车辆类型(type);
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("getNetCarCertificateResult error_msg:" + iocrResult.getError_msg());
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject != null) {
                    String words_result = jsonObject.getString("words_result");

                    if (!TextUtils.isEmpty(words_result)) {
                        String no = FormatUtil.网约证_许可号_提取(words_result);
                        if (!TextUtils.isEmpty(no) && (TextUtils.isEmpty(netCarCertificateInfo.get交运管许可号())||(no.length()>netCarCertificateInfo.get交运管许可号().length()))) {
                            netCarCertificateInfo.set交运管许可号(no);
                        }
                        通用文字识别高精度Result 识别Result = JSON.parseObject(result, 通用文字识别高精度Result.class);
                        if (识别Result != null) {
                            List<WordsResult> wordsResults = 识别Result.getWords_result();
                            if (wordsResults != null && !wordsResults.isEmpty()) {
                                for (WordsResult wordsResult : wordsResults) {
                                    if (wordsResult.getWords().contains(证照_网约车证_车辆号牌)) {
                                        String plate=FormatUtil.合格证_号码号牌_提取(words_result);
                                        if(TextUtils.isEmpty(plate)){
                                            plate = FormatUtil.网约证_属性_提取(wordsResult.getWords(), 证照_网约车证_车辆号牌);
                                        }
                                        if (!TextUtils.isEmpty(plate)&& (TextUtils.isEmpty(netCarCertificateInfo.get车辆号牌())||(plate.length()>netCarCertificateInfo.get车辆号牌().length()))) {
                                            netCarCertificateInfo.set车辆号牌(plate);
                                        }
                                    }
                                    if (wordsResult.getWords().contains(证照_网约车证_车辆类型)) {
                                        String type=FormatUtil.网约证_车辆类型_提取(words_result);
                                        if(TextUtils.isEmpty(type)) {
                                            type = FormatUtil.网约证_属性_提取(wordsResult.getWords(), 证照_网约车证_车辆类型);
                                        }
                                        if (!TextUtils.isEmpty(type)&& (TextUtils.isEmpty(netCarCertificateInfo.get车辆类型())||(type.length()>netCarCertificateInfo.get车辆类型().length()))) {
                                            netCarCertificateInfo.set车辆类型(type);
                                        }
                                    }
                                    if (wordsResult.getWords().contains(证照_网约车证_车辆所有人)) {
                                        String owner = FormatUtil.网约证_属性_提取(wordsResult.getWords(), 证照_网约车证_车辆所有人);
                                        if (!TextUtils.isEmpty(owner)&&(TextUtils.isEmpty(netCarCertificateInfo.get车辆所有人())||(owner.length()>netCarCertificateInfo.get车辆所有人().length()))) {
                                            netCarCertificateInfo.set车辆所有人(owner);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("getNetCarCertificateResult fail:");
                    }
                }
            }
        });
        t2.start();
        while (true) {
            if (t1.getState().equals(Thread.State.TERMINATED) && t2.getState().equals(Thread.State.TERMINATED)) {
                break; // 两个线程都结束，退出循环
            }
        }
        logger.info(JSONObject.toJSONString(netCarCertificateInfo));
        return netCarCertificateInfo;
    }
    *//**
 * 登记证识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static RegistrationCertificateInfo getRegistrationCertificateResult(String imgStr) {
        RegistrationCertificateInfo registrationCertificateInfo = new RegistrationCertificateInfo();
        String result = getRecogniseResult(imgStr, LICENSE_REGIST);
        IocrResult iocrResult = JSON.parseObject(result, IocrResult.class);

        if (iocrResult != null) {
            if (iocrResult.getError_code() == 0) {
                Data data = iocrResult.getData();
                List<Ret> retList = data.getRet();
                if (retList != null && !retList.isEmpty()) {
                    for (Ret ret : retList) {
                        if (证照_登记证_车架号.equalsIgnoreCase(ret.getWord_name())) {
                            String vin = FormatUtil.车架号_提取(ret.getWord());
                            if (!TextUtils.isEmpty(vin)) {
                                registrationCertificateInfo.set车架号(vin.toUpperCase());
                            }
                        }
                        if (证照_登记证_编号.equalsIgnoreCase(ret.getWord_name())) {
                            String no = ret.getWord();
                            if (!TextUtils.isEmpty(no)) {
                                no = no.replace(" ", "");
                                registrationCertificateInfo.set编号(no.toUpperCase());
                            }
                        }
                        if (证照_登记证_机动车所有人.equalsIgnoreCase(ret.getWord_name())) {
                            String owner = ret.getWord();
                            if (!TextUtils.isEmpty(owner)) {
                                String[] params = owner.split("/");
                                if (params != null && params.length > 1) {
                                    registrationCertificateInfo.set机动车所有人(params[0]);
                                    registrationCertificateInfo.set身份证明名称(params[1]);
                                    if (params.length > 2) {
                                        String owner_num = params[2];
                                        if (!TextUtils.isEmpty(owner_num)) {
                                            owner_num = owner_num.replace(" ", "");
                                            registrationCertificateInfo.set身份证明号码(owner_num.toUpperCase());
                                        }
                                    }
                                }
                            }
                        }
                        if (证照_登记证_身份证明名称.equalsIgnoreCase(ret.getWord_name())) {
                            //registrationCertificateInfo.set身份证明名称(ret.getWord());
                        }
                        if (证照_登记证_身份证明号码.equalsIgnoreCase(ret.getWord_name())) {
                            //registrationCertificateInfo.set身份证明号码(ret.getWord());
                        }
                        if (证照_登记证_登记日期.equalsIgnoreCase(ret.getWord_name())) {
                            String regDate = ret.getWord();
                            if (!TextUtils.isEmpty(regDate)) {
                                registrationCertificateInfo.set登记日期(FormatUtil.登记证_登记日期_提取(regDate));
                                registrationCertificateInfo.set机动车登记编号(FormatUtil.网约证_属性_提取(regDate, 证照_登记证_机动车登记编号));
                            }
                        }
                        if (证照_登记证_机动车登记编号.equalsIgnoreCase(ret.getWord_name())) {
                            //registrationCertificateInfo.set机动车登记编号(ret.getWord());
                        }
                        if (证照_登记证_车辆型号.equalsIgnoreCase(ret.getWord_name())) {
                            String type = FormatUtil.登记证_车辆型号_提取(ret.getWord());
                            if (!TextUtils.isEmpty(type)) {
                                registrationCertificateInfo.set车辆型号(type.toUpperCase());
                            }
                        }
                        if (证照_登记证_发动机号.equalsIgnoreCase(ret.getWord_name())) {
                            registrationCertificateInfo.set发动机号(FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_发动机号));
                        }
                        if (证照_登记证_发动机型号.equalsIgnoreCase(ret.getWord_name())) {
                            String engineType = FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_发动机型号);
                            if (!TextUtils.isEmpty(engineType)) {
                                registrationCertificateInfo.set发动机型号(engineType.toUpperCase());
                            }
                        }
                        if (证照_登记证_使用性质.equalsIgnoreCase(ret.getWord_name())) {
                            registrationCertificateInfo.set使用性质(FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_使用性质));
                        }
                        if (证照_登记证_车身颜色.equalsIgnoreCase(ret.getWord_name())) {
                            registrationCertificateInfo.set车身颜色(FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_车身颜色));
                        }
                        if (证照_登记证_车辆类型.equalsIgnoreCase(ret.getWord_name())) {
                            registrationCertificateInfo.set车辆类型(FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_车辆类型));
                        }
                        if (证照_登记证_出厂日期.equalsIgnoreCase(ret.getWord_name())) {
                            String facDate = DateUtils.fomatDate(ret.getWord());
                            if (!TextUtils.isEmpty(facDate)) {
                                registrationCertificateInfo.set出厂日期(facDate);
                            }
                        }
                        if (证照_登记证_发证日期.equalsIgnoreCase(ret.getWord_name())) {
                            String issueDate = DateUtils.fomatDate(ret.getWord());
                            if (!TextUtils.isEmpty(issueDate)) {
                                registrationCertificateInfo.set发证日期(issueDate);
                            }
                        }
                        if (证照_登记证_车辆品牌.equalsIgnoreCase(ret.getWord_name())) {
                            registrationCertificateInfo.set车辆品牌(FormatUtil.网约证_属性_提取(ret.getWord(), 证照_登记证_车辆品牌));
                        }
                    }
                }
            } else {
                logger.info("getRegistrationCertificateResult error_msg:" + iocrResult.getError_msg());
            }
        }
        logger.info(JSONObject.toJSONString(registrationCertificateInfo));
        return registrationCertificateInfo;
    }

    *//**
 * 车架号识别
 *
 * @param imgStr 图片Base64编码
 * @return
 *//*
    public static String getVinResult(String imgStr) {
        String result = 通用文字识别高精度Util.get通用文字识别高精度Result(imgStr);
        String vin = "";
        if (!TextUtils.isEmpty(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject != null) {
                String words_result = jsonObject.getString("words_result");
                if (!TextUtils.isEmpty(words_result)) {
                    vin = FormatUtil.车架号_提取(words_result);
                } else {
                    logger.info("getVinResult fail:");
                }
            }
        }
        return vin;
    }

    public static void batchTest() {
        String path = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\证照图片\\行驶证反面\\";
        try {
            List<String> files = new ArrayList<String>();
            File file = new File(path);
            File[] tempList = file.listFiles();
            //第一步创建workbook
            HSSFWorkbook wb = new HSSFWorkbook();
            //第二步创建sheet
            HSSFSheet summarySheet = wb.createSheet("Summary");
            HSSFRow summaryRow = null;
            HSSFCell summaryCell = null;
            HSSFSheet sheet=null;
            HSSFRow row = null;
            HSSFCell cell = null;
            long totalCount=0;
            long passedCount=0;
            int summaryRowCount=2;

            summaryRow = summarySheet.createRow(0);
            summaryCell = summaryRow.createCell(0);
            summaryCell.setCellValue("测试时间");
            summaryCell = summaryRow.createCell(1);
            summaryCell.setCellValue(DateUtils.getTime());
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    files.add(tempList[i].toString());
                    byte[] imgData = FileUtil.readFileByBytes(tempList[i].toString());
                    String imgStr = Base64Util.encode(imgData);
                    LicenseBackInfo licenseBackInfo=getLicenseBackResult(imgStr);
                    //第二步创建sheet
                    sheet = wb.createSheet(tempList[i].getName());

                    HashMap<String, String> hashMap=JSON.parseObject(JSONObject.toJSONString(licenseBackInfo), HashMap.class);
                    if(hashMap!=null&&!hashMap.isEmpty()){
                        Iterator iter = hashMap.entrySet().iterator();
                        int j=0;
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            Object key = entry.getKey();
                            Object val = entry.getValue();
                            row = sheet.createRow(j++);
                            cell = row.createCell(0);
                            cell.setCellValue(key+"");
                            totalCount++;

                            cell = row.createCell(1);
                            cell.setCellValue(val+"");
                            if(!TextUtils.isEmpty(val+"")){
                                passedCount++;
                            }else {
                                summaryRow = summarySheet.createRow(summaryRowCount++);
                                summaryCell = summaryRow.createCell(0);
                                summaryCell.setCellValue(tempList[i].getName());
                                summaryCell = summaryRow.createCell(1);
                                summaryCell.setCellValue(key+"");
                            }
                        }
                    }
                    summaryRow = summarySheet.createRow(1);
                    summaryCell = summaryRow.createCell(0);
                    summaryCell.setCellValue("识别结果");
                    summaryCell = summaryRow.createCell(1);
                    summaryCell.setCellValue(passedCount+"/"+totalCount);

                    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                    //anchor主要用于设置图片的属性
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 5, 0, (short) 13,16);
                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                    patriarch.createPicture(anchor, wb.addPicture(imgData, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    try {
                        FileOutputStream fout = new FileOutputStream("D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\证照图片\\识别结果测试\\行驶证反面识别结果.xls");
                        wb.write(fout);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void singleTest() {
        //陕AD02432 行驶证附本页.jpg
        //陕AD02465 行驶证附本页.jpg
        //陕AD03421 行驶证附本页.jpg
        //陕AD04053 行驶证附本页.jpg
        String path = "D:\\Workspace\\项目资料\\资产管理系统\\证照识别图片\\证照图片\\行驶证反面\\陕AD04342 行驶证附本页.jpg";
        try {
            byte[] imgData = FileUtil.readFileByBytes(path);
            String imgStr = Base64Util.encode(imgData);
            LicenseBackInfo netCarCertificateInfo=getLicenseBackResult(imgStr);
            //第一步创建workbook
            HSSFWorkbook wb = new HSSFWorkbook();
            //第二步创建sheet
            HSSFSheet sheet = wb.createSheet("网约证2识别结果");
            //第三步创建行row:添加表头0行
            HSSFRow row = sheet.createRow(0);

            HSSFCell cell = row.createCell(0);

            HashMap<String, String> hashMap=JSON.parseObject(JSONObject.toJSONString(netCarCertificateInfo), HashMap.class);
            if(hashMap!=null&&!hashMap.isEmpty()){
                Iterator iter = hashMap.entrySet().iterator();
                int i=0;
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    row = sheet.createRow(i++);
                    cell = row.createCell(0);

                    cell.setCellValue(key+"");
                    cell = row.createCell(1);
                    cell.setCellValue(val+"");
                }
            }
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 5, 0, (short) 13,16);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            patriarch.createPicture(anchor, wb.addPicture(imgData, HSSFWorkbook.PICTURE_TYPE_JPEG));
            try {
                FileOutputStream fout = new FileOutputStream("D:\\网约证2识别结果.xls");
                wb.write(fout);
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        singleTest();
    }*/