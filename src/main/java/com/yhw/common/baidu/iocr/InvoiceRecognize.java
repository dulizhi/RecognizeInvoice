package com.yhw.common.baidu.iocr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yhw.common.baidu.AuthService;
import com.yhw.domain.InvoiceData;
import com.yhw.domain.TaxInvoice;
import com.yhw.common.baidu.util.HttpUtil;

import java.net.URLEncoder;
import java.util.Map;

/**
 * author yang.hongwei
 * date 2019/12/19
 * 发票识别工具
 * */
public class InvoiceRecognize {
    static String recognizeUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/vat_invoice";

    /**
     * 发票识别
     *
     * @param imgStr 图片Base64编码
     * @return
     */
    public static TaxInvoice getInvoiceResult(String imgStr){
        TaxInvoice<InvoiceData> taxInvoice = new TaxInvoice();
        String result = null;
        /*try {
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            String accessToken = AuthService.getAuth();
            result = HttpUtil.post(recognizeUrl, accessToken, params);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       result = "{\"log_id\": 2497315603985005684, \"direction\": 0, \"words_result_num\": 31, \"words_result\": {\"InvoiceNum\": \"04180702\", \"SellerName\": \"石家庄市金利源石油经销有限公司\", \"CommodityTaxRate\": [{\"word\": \"13%\", \"row\": \"1\"}], \"SellerBank\": \"河北银行股份有限公司高新支行644012015000002385\", \"Checker\": \"\", \"NoteDrawer\": \"的\", \"CommodityAmount\": [{\"word\": \"88.50\", \"row\": \"1\"}], \"InvoiceDate\": \"2019年04月01日\", \"CommodityTax\": [{\"word\": \"11.50\", \"row\": \"1\"}], \"PurchaserName\": \"栾城区润兴帆布厂\", \"InvoiceTypeOrg\": \"河北增值税专用发票\", \"CommodityNum\": [{\"word\": \"15.479876\", \"row\": \"1\"}], \"PurchaserBank\": \"中国农业银行股份有限公司栾城治河分理处50320601040002170\", \"Remarks\": \"\", \"Password\": \"\", \"SellerAddress\": \"石家庄市桥西区师范街131号0311-85135365\", \"PurchaserAddress\": \"栾城区北留营村15833923512\", \"InvoiceCode\": \"1300172130\", \"CommodityUnit\": [{\"word\": \"天\", \"row\": \"1\"}], \"Payee\": \"\", \"PurchaserRegisterNum\": \"92130124MA08P7X267\", \"CommodityPrice\": [{\"word\": \"5.7168141593\", \"row\": \"1\"}], \"TotalAmount\": \"88.50\", \"AmountInWords\": \"壹佰圆整\", \"AmountInFiguers\": \"100.00\", \"TotalTax\": \"11.50\", \"InvoiceType\": \"专用发票\", \"SellerRegisterNum\": \"911301047984082159\", \"CommodityName\": [{\"word\": \"*乙醇汽油*92#汽油\", \"row\": \"1\"}], \"CommodityType\": [], \"CheckCode\": \"\"}}";

        Map<String,Object> resultMap = (Map<String,Object>)JSON.parse(result);
        if (resultMap != null && !resultMap.isEmpty()){
            JSONObject jsonObject = new JSONObject(resultMap);
            JSONObject wordsResult = jsonObject.getJSONObject("words_result");
            try {
                taxInvoice.setInvoiceType(wordsResult.getString("InvoiceType"));
                taxInvoice.setInvoiceNum(wordsResult.getString("InvoiceNum"));
                taxInvoice.setInvoiceCode(wordsResult.getString("InvoiceCode"));
                taxInvoice.setInvoiceDate(wordsResult.getString("InvoiceDate"));
                taxInvoice.setNoteDrawer(wordsResult.getString("NoteDrawer"));
                taxInvoice.setPayee(wordsResult.getString("Payee"));
                taxInvoice.setTotalAmount(wordsResult.getString("TotalAmount"));
                taxInvoice.setTotalTax(wordsResult.getString("TotalTax"));
                taxInvoice.setCommodityType(wordsResult.getJSONArray("CommodityType").toJavaList(InvoiceData.class));
                taxInvoice.setCommoditNum(wordsResult.getJSONArray("CommodityNum").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityAmount(wordsResult.getJSONArray("CommodityAmount").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityName(wordsResult.getJSONArray("CommodityName").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityPrice(wordsResult.getJSONArray("CommodityPrice").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityTax(wordsResult.getJSONArray("CommodityTax").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityTaxRate(wordsResult.getJSONArray("CommodityTaxRate").toJavaList(InvoiceData.class));
                //taxInvoice.setCommodityType(wordsResult.getJSONArray("CommodityType").toJavaList(InvoiceData.class));
                taxInvoice.setCommodityUnit(wordsResult.getJSONArray("CommodityUnit").toJavaList(InvoiceData.class));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        //System.out.println(JSONObject.toJSONString(taxInvoice));
        return taxInvoice;
    }
}
