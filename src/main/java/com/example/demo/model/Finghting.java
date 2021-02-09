package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

/*
一场比赛包括选手A 选手B 可能的比赛结果 真实的比赛结果--可能还需要其他，这几个最重要
 */
public class Finghting implements Serializable,Comparable<Finghting> {
    //场次比赛唯一标识
    private Long finghtingID;
    //赛事活动
    private Long contestID;
    //赛事活动名称
    private String contestName;
    //选手A
    private Fighter peopleA;
    //选手B
    private Fighter peopleB;
//    可能的比赛结果集合
//    private List<FightingResultEnum> maybeResultList;
    //实际赛果
    private FightingResultEnum realResult;
    //排序-第几场
    private String order;
    //pl set集合____
    private List<Gambling> gamblingList;

    public Long getFinghtingID() {
        return finghtingID;
    }

    public void setFinghtingID(Long finghtingID) {
        this.finghtingID = finghtingID;
    }

    public Long getContestID() {
        return contestID;
    }

    public void setContestID(Long contestID) {
        this.contestID = contestID;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public Fighter getPeopleA() {
        return peopleA;
    }

    public void setPeopleA(Fighter peopleA) {
        this.peopleA = peopleA;
    }

    public Fighter getPeopleB() {
        return peopleB;
    }

    public void setPeopleB(Fighter peopleB) {
        this.peopleB = peopleB;
    }

    public FightingResultEnum getRealResult() {
        return realResult;
    }

    public void setRealResult(FightingResultEnum realResult) {
        this.realResult = realResult;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Gambling> getGamblingList() {
        return gamblingList;
    }

    public void setGamblingList(List<Gambling> gamblingList) {
        this.gamblingList = gamblingList;
    }

    @Override
    public int compareTo(Finghting o) {
        int result = 0;
        //按照生产时间降序
        result = -this.order.compareTo(o.order);
        return result;
    }

    @Override
    public String toString() {
        return "Finghting{" +
                "finghtingID=" + finghtingID +
                ", contestID=" + contestID +
                ", contestName='" + contestName + '\'' +
                ", peopleA=" + peopleA +
                ", peopleB=" + peopleB +
                ", realResult=" + realResult +
                ", order='" + order + '\'' +
                ", gamblingList=" + gamblingList +
                '}';
    }
}
