package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

/*
比赛选手实体类，包括选手名称，别称，图片
 */
public class Fighter implements Serializable {
    //选手唯一标识
    private Long fighterID;
    //选手名称
    private String fighterName;
    //选手中文名
    private String fighterChineseName;
    //选手图片
    private String fighterPic;
    //选手昵称
    private List<String> fighterSecNameList;

    public Long getFighterID() {
        return fighterID;
    }

    public void setFighterID(Long fighterID) {
        this.fighterID = fighterID;
    }

    public String getFighterName() {
        return fighterName;
    }

    public void setFighterName(String fighterName) {
        this.fighterName = fighterName;
    }

    public String getFighterChineseName() {
        return fighterChineseName;
    }

    public void setFighterChineseName(String fighterChineseName) {
        this.fighterChineseName = fighterChineseName;
    }

    public String getFighterPic() {
        return fighterPic;
    }

    public void setFighterPic(String fighterPic) {
        this.fighterPic = fighterPic;
    }

    public List<String> getFighterSecNameList() {
        return fighterSecNameList;
    }

    public void setFighterSecNameList(List<String> fighterSecNameList) {
        this.fighterSecNameList = fighterSecNameList;
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "fighterID=" + fighterID +
                ", fighterName='" + fighterName + '\'' +
                ", fighterChineseName='" + fighterChineseName + '\'' +
                ", fighterPic='" + fighterPic + '\'' +
                ", fighterSecNameList=" + fighterSecNameList +
                '}';
    }
}
