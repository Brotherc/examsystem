package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.SchoolYear;

/**
 * Created by Administrator on 2018/2/6.
 */
public class SchoolYearVo extends SchoolYear{
    private String lessName;

    public String getLessName() {
        return lessName;
    }

    public void setLessName(String lessName) {
        this.lessName = lessName;
    }
}
