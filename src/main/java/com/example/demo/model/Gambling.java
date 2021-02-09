package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
/*
这个class存放 对于某一场比赛，某一个结果的竞猜数据 竞猜数据存放在两个set中，因为会是线性的，然后根据时间排序，重写compareble接口
 */
public class Gambling implements Serializable {
    //bet唯一标识
    private Long gambingID;
    //公司对象
    //实际比赛结果
    private String resultEnum;
    //预期比赛结果对A
    private String maybeResultTypeToA;
    //预期比赛结果对B
    private String maybeResultTypeToB;
    //选手PL
    private List<Odds> oddsList;

    public Long getGambingID() {
        return gambingID;
    }

    public void setGambingID(Long gambingID) {
        this.gambingID = gambingID;
    }




    public String getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(String resultEnum) {
        this.resultEnum = resultEnum;
    }

    public String getMaybeResultTypeToA() {
        return maybeResultTypeToA;
    }

    public void setMaybeResultTypeToA(String maybeResultTypeToA) {
        this.maybeResultTypeToA = maybeResultTypeToA;
    }

    public String getMaybeResultTypeToB() {
        return maybeResultTypeToB;
    }

    public void setMaybeResultTypeToB(String maybeResultTypeToB) {
        this.maybeResultTypeToB = maybeResultTypeToB;
    }

    public List<Odds> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<Odds> oddsList) {
        this.oddsList = oddsList;
    }

    @Override
    public String toString() {
        return "Gambling{" +
                "gambingID=" + gambingID +
                ", resultEnum='" + resultEnum + '\'' +
                ", maybeResultTypeToA='" + maybeResultTypeToA + '\'' +
                ", maybeResultTypeToB='" + maybeResultTypeToB + '\'' +
                ", oddsList=" + oddsList +
                '}';
    }
}
