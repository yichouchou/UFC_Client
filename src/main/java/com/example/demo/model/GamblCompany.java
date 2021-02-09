package com.example.demo.model;

import java.io.Serializable;

/*
Bocai公司实体类  公司名称 图片
 */
public class GamblCompany implements Serializable {
    //公司唯一标识
    private Long conpanyID;
    //公司名称
    private String companyName;
    //公司图片
    private String companyPic;

    public Long getConpanyID() {
        return conpanyID;
    }

    public void setConpanyID(Long conpanyID) {
        this.conpanyID = conpanyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPic() {
        return companyPic;
    }

    public void setCompanyPic(String companyPic) {
        this.companyPic = companyPic;
    }

    @Override
    public String toString() {
        return "GamblCompany{" +
                "conpanyID=" + conpanyID +
                ", companyName='" + companyName + '\'' +
                ", companyPic='" + companyPic + '\'' +
                '}';
    }
}
