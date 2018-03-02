package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.TestpaperQuestionRelation;

public class TestPaperFillInBlankQuestion extends TestpaperQuestionRelation{
    private String questionContent;
    private Integer blankNum;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getBlankNum() {
        return blankNum;
    }

    public void setBlankNum(Integer blankNum) {
        this.blankNum = blankNum;
    }
}
