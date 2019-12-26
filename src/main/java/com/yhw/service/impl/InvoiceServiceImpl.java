package com.yhw.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhw.common.baidu.iocr.InvoiceRecognize;
import com.yhw.common.baidu.util.Base64Util;
import com.yhw.domain.InvoiceData;
import com.yhw.domain.TaxInvoice;
import com.yhw.domain.TaxInvoiceExport;
import com.yhw.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author yang.hongwei
 * @date 2019/12/25
 * */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    List<TaxInvoice> taxInvoiceList = new ArrayList<>();

    @Override
    /**
     * 批量上传发票信息
     * @param invoiceFiles 发票压缩包
     *
     */
    public List<TaxInvoice> batchInvoiceUpload(InputStream invoiceFiles){
        List<TaxInvoice> list = new ArrayList<>();
        taxInvoiceList.clear(); //清空上一次识别数据 ,用于接收当前识别数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZipInputStream zin = null;
                BufferedInputStream bin = null;
                ByteArrayOutputStream bos = null;
                StringBuilder failData = new StringBuilder();
                try {
                    if (invoiceFiles != null && invoiceFiles.available() > 0){
                        zin = new ZipInputStream(invoiceFiles, Charset.forName("GBK"));
                        bin = new BufferedInputStream(zin);
                        ZipEntry entry;
                        while ((entry = zin.getNextEntry()) != null && !entry.isDirectory()){
                            String fileName = entry.getName();
                            byte[] fileData = null;
                            bos = new ByteArrayOutputStream(0xffff);
                            int b;
                            while ((b = bin.read()) != -1){
                                bos.write(b);
                            }
                            if (bos != null){
                                fileData = bos.toByteArray();
                            }
                            bos.close();
                            String imgStr = Base64Util.encode(fileData);
                            /*识别保存证照*/
                            if (imgStr != null && imgStr.length() > 0){
                                TaxInvoice<InvoiceData> taxInvoice = new TaxInvoice<>();
                                taxInvoice = InvoiceRecognize.getInvoiceResult(imgStr);
                                if (taxInvoice != null){
                                    taxInvoiceList.add(taxInvoice);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).run();
        list = taxInvoiceList;
        return list;
    }

    @Override
    /**
     * 识别信息转换为TaxInvoiceExport类信息
     * @param list 发票实体类list
     *
     */
    public List<TaxInvoiceExport> invoiceToExport(List<TaxInvoice> list){
        List<TaxInvoiceExport> taxInvoiceExportList = new ArrayList<>();
        for (TaxInvoice<InvoiceData> taxInvoice : list){
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
                    taxInvoiceExportList.add(i, taxInvoiceExport);
                }
            }
        }
        return taxInvoiceExportList;
    }
}
