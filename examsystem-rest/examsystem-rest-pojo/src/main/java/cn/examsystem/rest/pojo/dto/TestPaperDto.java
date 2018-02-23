package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.TestPaper;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/6.
 * 试卷（包括题目信息）
 */
public class TestPaperDto extends TestPaper{

    private String schoolYearName;
    private String courseName;

    private Integer singleChoiceQuestionNum;
    private Integer trueOrFalseQuestionNum;
    private Integer fillInBlankQuestionNum;

    private String singleChoiceQuestionIds;
    private BigDecimal singleChoiceQuestionScore;

    private String trueOrFalseQuestionIds;
    private BigDecimal trueOrFalseQuestionScore;

    private String fillInBlankQuestionIds;
    private BigDecimal fillInBlankQuestionScore;

    public String getSingleChoiceQuestionIds() {
        return singleChoiceQuestionIds;
    }

    public void setSingleChoiceQuestionIds(String singleChoiceQuestionIds) {
        this.singleChoiceQuestionIds = singleChoiceQuestionIds;
    }

    public BigDecimal getSingleChoiceQuestionScore() {
        return singleChoiceQuestionScore;
    }

    public void setSingleChoiceQuestionScore(BigDecimal singleChoiceQuestionScore) {
        this.singleChoiceQuestionScore = singleChoiceQuestionScore;
    }

    public String getTrueOrFalseQuestionIds() {
        return trueOrFalseQuestionIds;
    }

    public void setTrueOrFalseQuestionIds(String trueOrFalseQuestionIds) {
        this.trueOrFalseQuestionIds = trueOrFalseQuestionIds;
    }

    public BigDecimal getTrueOrFalseQuestionScore() {
        return trueOrFalseQuestionScore;
    }

    public void setTrueOrFalseQuestionScore(BigDecimal trueOrFalseQuestionScore) {
        this.trueOrFalseQuestionScore = trueOrFalseQuestionScore;
    }

    public String getFillInBlankQuestionIds() {
        return fillInBlankQuestionIds;
    }

    public void setFillInBlankQuestionIds(String fillInBlankQuestionIds) {
        this.fillInBlankQuestionIds = fillInBlankQuestionIds;
    }

    public BigDecimal getFillInBlankQuestionScore() {
        return fillInBlankQuestionScore;
    }

    public void setFillInBlankQuestionScore(BigDecimal fillInBlankQuestionScore) {
        this.fillInBlankQuestionScore = fillInBlankQuestionScore;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolYearName() {
        return schoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        this.schoolYearName = schoolYearName;
    }

    public Integer getSingleChoiceQuestionNum() {
        return singleChoiceQuestionNum;
    }

    public void setSingleChoiceQuestionNum(Integer singleChoiceQuestionNum) {
        this.singleChoiceQuestionNum = singleChoiceQuestionNum;
    }

    public Integer getTrueOrFalseQuestionNum() {
        return trueOrFalseQuestionNum;
    }

    public void setTrueOrFalseQuestionNum(Integer trueOrFalseQuestionNum) {
        this.trueOrFalseQuestionNum = trueOrFalseQuestionNum;
    }

    public Integer getFillInBlankQuestionNum() {
        return fillInBlankQuestionNum;
    }

    public void setFillInBlankQuestionNum(Integer fillInBlankQuestionNum) {
        this.fillInBlankQuestionNum = fillInBlankQuestionNum;
    }
}
