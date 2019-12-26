package com.yhw.controller;

import com.alibaba.fastjson.JSON;
import com.yhw.common.baidu.iocr.InvoiceRecognize;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.common.baidu.util.FileUtil;
import com.yhw.common.constant.GlobalConstants;
import com.yhw.common.core.controller.BaseController;
import com.yhw.common.core.domain.AjaxResult;
import com.yhw.common.core.page.TableDataInfo;
import com.yhw.common.utils.exportUtils;
import com.yhw.domain.InvoiceData;
import com.yhw.domain.TaxInvoice;
import com.yhw.domain.TaxInvoiceExport;
import com.yhw.service.InvoiceService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 增值税发票controller
 * @author yang.hongwei
 * @date 2019/12/19
 * */
@Controller
//@RequestMapping("/invoice")
public class InvoiceController extends BaseController {
    List<TaxInvoiceExport> exportList = new ArrayList<>();
    public String prefix = "invoice";

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping("/invoice")
    public String invoice(ModelMap modelMap){
        //exportList = null;
        modelMap.addAttribute("hello","hello");
        return prefix + "/invoice";
    }

    @RequestMapping("/invoiceList")
    public String invoiceList(){
        //exportList = null;
        return prefix + "/invoiceList";
    }

    /*识别并导出发票*/
    @PostMapping("/recognize")
    @ResponseBody
    public AjaxResult exportInvoice(MultipartFile picture, HttpServletResponse response){
        String result = null; // 返回结果
        String imgStr = null; //图片Base64编码
        List<TaxInvoiceExport> list = new ArrayList<>();
        if (picture == null || picture.getSize() < 1){
            result = GlobalConstants.WEB_PARAM_INVALID;
        }else {
            try {
                byte[] data = FileUtil.inputStream2byte(picture.getInputStream());
                imgStr = Base64Util.encode(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        TaxInvoice<InvoiceData> taxInvoice = new TaxInvoice<>();
        taxInvoice = InvoiceRecognize.getInvoiceResult(imgStr);

        if (!(taxInvoice == null)){
            for (int i = 0; i < taxInvoice.getCommodityName().size(); i++){
                TaxInvoiceExport taxInvoiceExport = new TaxInvoiceExport();
                taxInvoiceExport.setInvoiceNum(taxInvoice.getInvoiceNum());
                taxInvoiceExport.setProductName(taxInvoice.getCommodityName().get(i).getWord());
                if (taxInvoice.getCommodityType().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductType(taxInvoice.getCommodityType().get(i).getWord());
                }
                if (taxInvoice.getCommoditNum().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductNum(taxInvoice.getCommoditNum().get(i).getWord());
                }
                if (taxInvoice.getCommodityPrice().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductPrice(taxInvoice.getCommodityPrice().get(i).getWord());
                }
                if (taxInvoice.getCommodityAmount().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductAmount(taxInvoice.getCommodityAmount().get(i).getWord());
                }
                list.add(i,taxInvoiceExport);
            }
            LinkedHashMap<String, String> invoiceList = new LinkedHashMap<>();
            invoiceList.put("invoiceNum", "发票号码");
            invoiceList.put("productName", "商品名称");
            invoiceList.put("productType", "规格型号");
            invoiceList.put("productNum", "商品数量");
            invoiceList.put("productPrice", "商品单价");
            invoiceList.put("productAmount", "总金额");
            InputStream is = null;
            try {
                is = exportUtils.customExport(list,invoiceList,"发票");
                String fileName = new String(("发票.xlsx").getBytes(),"ISO8859-1");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Length", String.valueOf(is.available()));
                ServletOutputStream outputStream = response.getOutputStream();
                IOUtils.copyLarge(is, outputStream);
                result = GlobalConstants.WEB_OPE_SUCCESS;
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result.equalsIgnoreCase(GlobalConstants.WEB_OPE_SUCCESS)){
            return AjaxResult.success(result);
        }else return AjaxResult.error(result);
    }

    /*单张识别发票并保存数据*/
    @PostMapping("/recognize/list")
    @ResponseBody
    public AjaxResult list(MultipartFile picture){
        StringBuilder result = new StringBuilder();
        result.append("识别结果如下：<br/>");
        String imgStr = null; //图片Base64编码
        List<TaxInvoiceExport> list = new ArrayList<>();
        if (picture == null || picture.getSize() < 1){
            return AjaxResult.error("解析错误");
        }else {
            try {
                byte[] data = FileUtil.inputStream2byte(picture.getInputStream());
                imgStr = Base64Util.encode(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        TaxInvoice<InvoiceData> taxInvoice = new TaxInvoice<>();
        taxInvoice = InvoiceRecognize.getInvoiceResult(imgStr);

        if (!(taxInvoice == null)) {
            for (int i = 0; i < taxInvoice.getCommodityName().size(); i++) {
                TaxInvoiceExport taxInvoiceExport = new TaxInvoiceExport();
                taxInvoiceExport.setInvoiceNum(taxInvoice.getInvoiceNum());
                taxInvoiceExport.setProductName(taxInvoice.getCommodityName().get(i).getWord());
                if (taxInvoice.getCommodityType().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductType(taxInvoice.getCommodityType().get(i).getWord());
                }
                if (taxInvoice.getCommoditNum().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductNum(taxInvoice.getCommoditNum().get(i).getWord());
                }
                if (taxInvoice.getCommodityPrice().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductPrice(taxInvoice.getCommodityPrice().get(i).getWord());
                }
                if (taxInvoice.getCommodityAmount().size() == taxInvoice.getCommodityName().size()){
                    taxInvoiceExport.setProductAmount(taxInvoice.getCommodityAmount().get(i).getWord());
                }
                result.append("发票代码：" + taxInvoiceExport.getInvoiceNum() + "    &nbsp&nbsp&nbsp&nbsp商品名称：" + taxInvoiceExport.getProductName() +  "     &nbsp&nbsp&nbsp&nbsp规格型号：" + taxInvoiceExport.getProductType() + "      &nbsp&nbsp&nbsp&nbsp商品数量：" +
                        taxInvoiceExport.getProductNum() + "    &nbsp&nbsp&nbsp&nbsp商品价格：" + taxInvoiceExport.getProductPrice() + "     &nbsp&nbsp&nbsp&nbsp商品金额：" + taxInvoiceExport.getProductAmount() + "<br/>");
                list.add(i, taxInvoiceExport);
            }
        }
        result.append("<br/>点击导出即可");
        if (exportList == null || exportList.isEmpty()){
            exportList = list;
        }else {
            exportList.addAll(list);
        }
        return AjaxResult.success(result.toString());
    }

    /*上传图片压缩包解析并保存*/
    @PostMapping("/recognize/batchUpload")
    @ResponseBody
    public AjaxResult batchUpload(MultipartFile pictures){
        String result = null;
        if (pictures == null || pictures.getSize() < 1){
            return AjaxResult.warn("上传文件为空，请检查！");
        }else {
            try {
                List<TaxInvoice> taxInvoiceList = invoiceService.batchInvoiceUpload(pictures.getInputStream());
                if (taxInvoiceList != null && taxInvoiceList.size() > 0){
                    List<TaxInvoiceExport> taxInvoiceExportList = invoiceService.invoiceToExport(taxInvoiceList);
                    if (exportList == null){
                        exportList = taxInvoiceExportList;
                    }else {
                        exportList.addAll(taxInvoiceExportList);
                    }
                    result = GlobalConstants.WEB_OPE_SUCCESS;
                }else {
                    result = "识别异常";
                }
            }catch (Exception e){
                result = GlobalConstants.WEB_OPE_EXCEPTION;
            }
        }
        if (result.equalsIgnoreCase(GlobalConstants.WEB_OPE_SUCCESS)){
            return AjaxResult.success(result);
        }else {
            return AjaxResult.error(result);
        }
    }

    /*导出识别数据*/
    @GetMapping("/recognize/export")
    @ResponseBody
    public String exportInvoiceList(HttpServletResponse response){
        String result = null;
        if (exportList == null || exportList.isEmpty()){
            result = GlobalConstants.WEB_PARAM_INVALID;
            return result;
        }
        LinkedHashMap<String, String> invoiceList = new LinkedHashMap<>();
        invoiceList.put("invoiceNum", "发票号码");
        invoiceList.put("productName", "商品名称");
        invoiceList.put("productType", "规格型号");
        invoiceList.put("productNum", "商品数量");
        invoiceList.put("productPrice", "商品单价");
        invoiceList.put("productAmount", "总金额");
        InputStream is = null;
        try {
            is = exportUtils.customExport(exportList,invoiceList,"发票");
            String fileName = new String(("发票.xlsx").getBytes(),"ISO8859-1");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Length", String.valueOf(is.available()));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copyLarge(is, outputStream);
            result = GlobalConstants.WEB_OPE_SUCCESS;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*显示识别列表*/
    @PostMapping("/recognize/invoiceList")
    @ResponseBody
    public TableDataInfo list(){
        if (exportList != null && exportList.size() > 0){
            return getDataTable(exportList);
        }else {
            return getDataTable(new ArrayList<>());
        }
    }

    /*清空识别数据*/
    @PostMapping("/deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(){
        exportList.clear();
        return AjaxResult.success("清空成功!");
    }
}
