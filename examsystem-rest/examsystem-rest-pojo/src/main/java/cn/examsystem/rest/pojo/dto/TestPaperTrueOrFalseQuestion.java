package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.TestpaperQuestionRelation;

public class TestPaperTrueOrFalseQuestion extends TestpaperQuestionRelation{
    private String questionContent;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
