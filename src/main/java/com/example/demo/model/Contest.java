package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
一场赛事活动 包括 活动名称 第二名称 开始时间 包含的比赛List
 */
public class Contest implements Serializable {
    //唯一标识
    private Long contestID;
    //简称、昵称
    private String contestName;
    //第二简称
    private String contestSecName;
    //赛事活动开始时间
    private String startTime;
    //比赛场次集合
    private List<Finghting> finghtingList;

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

    public String getContestSecName() {
        return contestSecName;
    }

    public void setContestSecName(String contestSecName) {
        this.contestSecName = contestSecName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Finghting> getFinghtingList() {
        return finghtingList;
    }

    public void setFinghtingList(List<Finghting> finghtingList) {
        this.finghtingList = finghtingList;
    }

    public int compareTo(Contest o) {
        int result = 0;
        //按照生产时间降序
        result = -this.startTime.compareTo(o.startTime);
        return result;
    }


    @Override
    public String toString() {
        return "Contest{" +
                "contestID=" + contestID +
                ", contestName='" + contestName + '\'' +
                ", contestSecName='" + contestSecName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finghtingList=" + finghtingList +
                '}';
    }
}
