package com.yhw.domain;

import java.util.List;

/**
 * 增值税发票实体类
 * author yang.hongwei
 * date 2019/12/19
 * */
public class TaxInvoice<T> {
    //发票种类
    private String invoiceType = "";

    //发票号码
    private String invoiceNum = "";

    //发票代码
    private String invoiceCode = "";

    //开票日期
    private String invoiceDate = "";

    //规格型号
    private List<T> commodityType;

    //商品名称
    private List<T> commodityName;

    //单位
    private List<T> commodityUnit;

    //数量
    private List<T> commoditNum;

    //单价
    private List<T> commodityPrice;

    //金额
    private List<T> commodityAmount;

    //税率
    private List<T> commodityTaxRate;

    //税额
    private List<T> commodityTax;

    //合计金额
    private String totalAmount = "";

    //合计税额
    private String totalTax = "";

    //收款人
    private String payee = "";

    //开票人
    private String noteDrawer = "";

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<T> getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(List<T> commodityName) {
        this.commodityName = commodityName;
    }

    public List<T> getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(List<T> commodityUnit) {
        this.commodityUnit = commodityUnit;
    }

    public List<T> getCommoditNum() {
        return commoditNum;
    }

    public void setCommoditNum(List<T> commoditNum) {
        this.commoditNum = commoditNum;
    }

    public List<T> getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(List<T> commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public List<T> getCommodityAmount() {
        return commodityAmount;
    }

    public void setCommodityAmount(List<T> commodityAmount) {
        this.commodityAmount = commodityAmount;
    }

    public List<T> getCommodityTaxRate() {
        return commodityTaxRate;
    }

    public void setCommodityTaxRate(List<T> commodityTaxRate) {
        this.commodityTaxRate = commodityTaxRate;
    }

    public List<T> getCommodityTax() {
        return commodityTax;
    }

    public void setCommodityTax(List<T> commodityTax) {
        this.commodityTax = commodityTax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNoteDrawer() {
        return noteDrawer;
    }

    public void setNoteDrawer(String noteDrawer) {
        this.noteDrawer = noteDrawer;
    }

    public List<T> getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(List<T> commodityType) {
        this.commodityType = commodityType;
    }
}
