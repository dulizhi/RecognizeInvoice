package com.yhw.service;

import com.yhw.domain.TaxInvoice;
import com.yhw.domain.TaxInvoiceExport;

import java.io.InputStream;
import java.util.List;

/**
 * @author yang.hongwei
 * @date 2019/12/25
 * */
public interface InvoiceService {
    /**
     * 批量上传发票信息
     * @param invoiceFiles 发票压缩包
     *
     */
    public List<TaxInvoice> batchInvoiceUpload(InputStream invoiceFiles);

    /**
     * 识别信息转换为TaxInvoiceExport类信息
     * @param list 发票实体类list
     *
     */
    public List<TaxInvoiceExport> invoiceToExport(List<TaxInvoice> list);
}
