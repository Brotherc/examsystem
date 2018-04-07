package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.TestpaperQuestionRelation;

public class TestPaperProgramQuestion extends TestpaperQuestionRelation{
    private String questionContent;
    private String questionInputDescription;
    private String questionOutputDescription;
    private Integer questionTimeLimit;
    private Integer questionMemoryLimit;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionInputDescription() {
        return questionInputDescription;
    }

    public void setQuestionInputDescription(String questionInputDescription) {
        this.questionInputDescription = questionInputDescription;
    }

    public String getQuestionOutputDescription() {
        return questionOutputDescription;
    }

    public void setQuestionOutputDescription(String questionOutputDescription) {
        this.questionOutputDescription = questionOutputDescription;
    }

    public Integer getQuestionTimeLimit() {
        return questionTimeLimit;
    }

    public void setQuestionTimeLimit(Integer questionTimeLimit) {
        this.questionTimeLimit = questionTimeLimit;
    }

    public Integer getQuestionMemoryLimit() {
        return questionMemoryLimit;
    }

    public void setQuestionMemoryLimit(Integer questionMemoryLimit) {
        this.questionMemoryLimit = questionMemoryLimit;
    }
}
