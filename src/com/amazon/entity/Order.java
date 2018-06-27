package com.amazon.entity;

import java.util.List;

public class Order {
    private Integer oId;

    private String bussinessId;  //单号

    private Float oCount;  //订单价格

    private Integer uId;

    private Integer aId;

    private String oDate;  //订单日期

    private String oStatus; //订单状态

    private String oDeliver;

    private Float oDeliverFee;  //运费

    private String uPay;  //支付方式

    private String uInvoiceType;  //发票类型

    private String uInvoiceTitle;  //发票抬头

    private List<Book> books;

    public Integer getoId() {
        return oId;
    }

    public void setoId(Integer oId) {
        this.oId = oId;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Float getoCount() {
        return oCount;
    }

    public void setoCount(Float oCount) {
        this.oCount = oCount;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getoDate() {
        return oDate;
    }

    public void setoDate(String oDate) {
        this.oDate = oDate;
    }

    public String getoStatus() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = oStatus;
    }

    public String getoDeliver() {
        return oDeliver;
    }

    public void setoDeliver(String oDeliver) {
        this.oDeliver = oDeliver;
    }

    public Float getoDeliverFee() {
        return oDeliverFee;
    }

    public void setoDeliverFee(Float oDeliverFee) {
        this.oDeliverFee = oDeliverFee;
    }

    public String getuPay() {
        return uPay;
    }

    public void setuPay(String uPay) {
        this.uPay = uPay;
    }

    public String getuInvoiceType() {
        return uInvoiceType;
    }

    public void setuInvoiceType(String uInvoiceType) {
        this.uInvoiceType = uInvoiceType;
    }

    public String getuInvoiceTitle() {
        return uInvoiceTitle;
    }

    public void setuInvoiceTitle(String uInvoiceTitle) {
        this.uInvoiceTitle = uInvoiceTitle;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
