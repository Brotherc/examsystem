package cn.examsystem.rest.pojo.po;

import java.math.BigDecimal;
import java.util.Date;

public class TestPaper {
    private String id;

    private String name;

    private BigDecimal score;

    private Integer type;

    private String courseId;

    private String createdTeacherId;

    private String schoolYearId;

    private Integer term;

    private Date createdTime;

    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getCreatedTeacherId() {
        return createdTeacherId;
    }

    public void setCreatedTeacherId(String createdTeacherId) {
        this.createdTeacherId = createdTeacherId == null ? null : createdTeacherId.trim();
    }

    public String getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(String schoolYearId) {
        this.schoolYearId = schoolYearId == null ? null : schoolYearId.trim();
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
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
}