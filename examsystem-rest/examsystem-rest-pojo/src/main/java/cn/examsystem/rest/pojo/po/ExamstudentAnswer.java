package cn.examsystem.rest.pojo.po;

import java.math.BigDecimal;
import java.util.Date;

public class ExamstudentAnswer {
    private String id;

    private String examStudentId;

    private String testpaperQuestionId;

    private BigDecimal score;

    private Boolean isGraded;

    private Date createdTime;

    private Date updatedTime;

    private String studentAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExamStudentId() {
        return examStudentId;
    }

    public void setExamStudentId(String examStudentId) {
        this.examStudentId = examStudentId == null ? null : examStudentId.trim();
    }

    public String getTestpaperQuestionId() {
        return testpaperQuestionId;
    }

    public void setTestpaperQuestionId(String testpaperQuestionId) {
        this.testpaperQuestionId = testpaperQuestionId == null ? null : testpaperQuestionId.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Boolean getIsGraded() {
        return isGraded;
    }

    public void setIsGraded(Boolean isGraded) {
        this.isGraded = isGraded;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer == null ? null : studentAnswer.trim();
    }
}