package com.yhw.common.baidu.iocr;

import com.alibaba.fastjson.JSONObject;
import com.yhw.domain.InvoiceData;
import com.yhw.domain.TaxInvoiceExport;
import com.yhw.domain.TaxInvoice;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.utils.exportUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author yang.hongwei
 * @title 发票识别
 * @description 发票识别
 * @date 2019/10/24
 *
 */
public class VehicleInvoiceIocrUtil {
    static String host="https://aip.baidubce.com/rest/2.0/ocr/v1/vat_invoice";

    public static void main(String[] args) {
        // 本地图片路径
        String filePath = "D:\\project\\Utils\\测试数据\\20190402173523397.jpg";
        List<TaxInvoiceExport> list = new ArrayList<>();
        try {
            System.out.println("baoyou");
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            TaxInvoice<InvoiceData> taxInvoice = InvoiceRecognize.getInvoiceResult(imgStr);
            System.out.println(JSONObject.toJSONString(taxInvoice));
            for (int i = 0; i < taxInvoice.getCommodityName().size(); i++){
                TaxInvoiceExport taxInvoiceExport = new TaxInvoiceExport();
                taxInvoiceExport.setInvoiceNum(taxInvoice.getInvoiceNum());
                taxInvoiceExport.setProductName(taxInvoice.getCommodityName().get(i).getWord());
                taxInvoiceExport.setProductNum(taxInvoice.getCommoditNum().get(i).getWord());
                taxInvoiceExport.setProductPrice(taxInvoice.getCommodityPrice().get(i).getWord());
                taxInvoiceExport.setProductAmount(taxInvoice.getCommodityAmount().get(i).getWord());
                list.add(i,taxInvoiceExport);
            }
            LinkedHashMap<String, String> invoiceList = new LinkedHashMap<>();
            invoiceList.put("invoiceNum", "发票号码");
            invoiceList.put("productName", "商品名称");
            invoiceList.put("productNum", "商品数量");
            invoiceList.put("productPrice", "商品单价");
            invoiceList.put("productAmount", "总金额");
            InputStream is = null;
            OutputStream outputStream = null;
            try {
                is = exportUtils.customExport(list,invoiceList,"发票");
                outputStream = new FileOutputStream(new File("D://project//发票.xlsx"));
                byte[] bytes = new byte[1024];
                while (is.read(bytes) != -1){
                    outputStream.write(bytes);
                    outputStream.flush();
                }
                System.out.println("导出成功");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                is.close();
                outputStream.close();
            }
            /*String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            *//**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             *//*
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(host, accessToken, params);
            System.out.println("rsult: " + result);
            //String result = "{\"log_id\": 2497315603985005684, \"direction\": 0, \"words_result_num\": 31, \"words_result\": {\"InvoiceNum\": \"04180702\", \"SellerName\": \"石家庄市金利源石油经销有限公司\", \"CommodityTaxRate\": [{\"word\": \"13%\", \"row\": \"1\"}], \"SellerBank\": \"河北银行股份有限公司高新支行644012015000002385\", \"Checker\": \"\", \"NoteDrawer\": \"的\", \"CommodityAmount\": [{\"word\": \"88.50\", \"row\": \"1\"}], \"InvoiceDate\": \"2019年04月01日\", \"CommodityTax\": [{\"word\": \"11.50\", \"row\": \"1\"}], \"PurchaserName\": \"栾城区润兴帆布厂\", \"InvoiceTypeOrg\": \"河北增值税专用发票\", \"CommodityNum\": [{\"word\": \"15.479876\", \"row\": \"1\"}], \"PurchaserBank\": \"中国农业银行股份有限公司栾城治河分理处50320601040002170\", \"Remarks\": \"\", \"Password\": \"\", \"SellerAddress\": \"石家庄市桥西区师范街131号0311-85135365\", \"PurchaserAddress\": \"栾城区北留营村15833923512\", \"InvoiceCode\": \"1300172130\", \"CommodityUnit\": [{\"word\": \"天\", \"row\": \"1\"}], \"Payee\": \"\", \"PurchaserRegisterNum\": \"92130124MA08P7X267\", \"CommodityPrice\": [{\"word\": \"5.7168141593\", \"row\": \"1\"}], \"TotalAmount\": \"88.50\", \"AmountInWords\": \"壹佰圆整\", \"AmountInFiguers\": \"100.00\", \"TotalTax\": \"11.50\", \"InvoiceType\": \"专用发票\", \"SellerRegisterNum\": \"911301047984082159\", \"CommodityName\": [{\"word\": \"*乙醇汽油*92#汽油\", \"row\": \"1\"}], \"CommodityType\": [], \"CheckCode\": \"\"}}";
            Map<String, Object> resultMap = (Map<String, Object>)JSON.parse(result);
            JSONObject jsonObject = new JSONObject(resultMap);
            JSONObject wordsResult = jsonObject.getJSONObject("words_re sult");
            String invoiceType = wordsResult.getString("InvoiceType");
            JSONArray commodityName = wordsResult.getJSONArray("CommodityName");
            List<InvoiceData> invoiceDatas = commodityName.toJavaList(InvoiceData.class);
            System.out.println(JSON.toJSONString(invoiceDatas));
            System.out.println(invoiceType);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
