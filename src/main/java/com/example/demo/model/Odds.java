package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

/*
 一条赔率数据，对于日期，赔率，选手A，由于需要排序 不同时间节点需要排，最后需要放到set集合
 */
public class Odds implements  Serializable {
    private Long oddsID;
    //注意此处时间是不确定的且动态变化的
    private Date oddsTime;
    private GamblCompany gamblCompany;
    private String peilvA;
    private String peilvB;
    //采用何种方式 bet，比如1.8:1.8 hais -100,+100
    private String betTypeEnum;
//    //赛事-某一场
//    private Long finghtingID;
//
//    //可能的比赛结果
//    private FightingResultEnum maybeResultList;
//    //实际赛果-可以不存
//    private FightingResultEnum realResult;

    public Long getOddsID() {
        return oddsID;
    }

    public void setOddsID(Long oddsID) {
        this.oddsID = oddsID;
    }

    public Date getOddsTime() {
        return oddsTime;
    }

    public void setOddsTime(Date oddsTime) {
        this.oddsTime = oddsTime;
    }

    public String getPeilvA() {
        return peilvA;
    }

    public void setPeilvA(String peilvA) {
        this.peilvA = peilvA;
    }

    public String getPeilvB() {
        return peilvB;
    }

    public void setPeilvB(String peilvB) {
        this.peilvB = peilvB;
    }

    public String getBetTypeEnum() {
        return betTypeEnum;
    }

    public void setBetTypeEnum(String betTypeEnum) {
        this.betTypeEnum = betTypeEnum;
    }

    public GamblCompany getGamblCompany() {
        return gamblCompany;
    }

    public void setGamblCompany(GamblCompany gamblCompany) {
        this.gamblCompany = gamblCompany;
    }
    //    public Long getFinghtingID() {
//        return finghtingID;
//    }
//
//    public void setFinghtingID(Long finghtingID) {
//        this.finghtingID = finghtingID;
//    }
//
//    public FightingResultEnum getMaybeResultList() {
//        return maybeResultList;
//    }
//
//    public void setMaybeResultList(FightingResultEnum maybeResultList) {
//        this.maybeResultList = maybeResultList;
//    }
//
//    public FightingResultEnum getRealResult() {
//        return realResult;
//    }
//
//    public void setRealResult(FightingResultEnum realResult) {
//        this.realResult = realResult;
//    }

//    @Override
//    public int compareTo(Odds o) {
//        int result = 0;
//        //按照抓取的顺序-降序  主 次 副赛
//        result = -this.oddsTime.compareTo(o.oddsTime);
//        return result;
//    }

    @Override
    public String toString() {
        return "Odds{" +
                "oddsID=" + oddsID +
                ", oddsTime=" + oddsTime +
                ", gamblCompany=" + gamblCompany +
                ", peilvA='" + peilvA + '\'' +
                ", peilvB='" + peilvB + '\'' +
                ", betTypeEnum='" + betTypeEnum + '\'' +
                '}';
    }
}
