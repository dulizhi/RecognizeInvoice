package com.yhw.domain;

/**
 * 增值税发票导出字段实体类
 * author yang.hongwei
 * date 2019/12/19
 * */
public class TaxInvoiceExport {
    //发票号码
    private String invoiceNum = "";

    //商品名称
    private String productName = "";

    //规格型号
    private String productType = "";

    //商品数量
    private String productNum = "";

    //商品单价
    private String productPrice = "";

    //商品金额
    private String productAmount = "";

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
