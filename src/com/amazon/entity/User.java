package com.amazon.entity;

public class User {

    private Integer uId;

    /**
     * 用户昵称
     */
    private String uRegister;

    /**
     * 用户姓名
     */
    private String uName;

    /**
     * 用户性别
     */
    private String uSex;

    /**
     * 用户密码
     */
    private String uPassword;

    /**
     * 用户手机号
     */
    private String uPhone;

    /**
     * 用户QQ
     * @return
     */
    private String uQQ;

    private String uPayOne;

    private Integer carts;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuRegister() {
        return uRegister;
    }

    public void setuRegister(String uRegister) {
        this.uRegister = uRegister;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuQQ() {
        return uQQ;
    }

    public void setuQQ(String uQQ) {
        this.uQQ = uQQ;
    }

    public String getuPayOne() {
        return uPayOne;
    }

    public void setuPayOne(String uPayOne) {
        this.uPayOne = uPayOne;
    }

    public Integer getCarts() {
        return carts;
    }

    public void setCarts(Integer carts) {
        this.carts = carts;
    }
}
