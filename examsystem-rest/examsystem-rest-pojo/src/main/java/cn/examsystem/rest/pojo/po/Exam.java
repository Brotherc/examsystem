package cn.examsystem.rest.pojo.po;

import java.util.Date;

public class Exam {
    private String id;

    private String courseId;

    private String testPaperId;

    private Integer type;

    private String schoolYearId;

    private Integer term;

    private Integer status;

    private String createdTeacherId;

    private Integer partNum;

    private Integer intervalTime;

    private Date startTime;

    private Date endTime;

    private Integer time;

    private String examPwd;

    private String invigilatePwd;

    private Date createdTime;

    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(String testPaperId) {
        this.testPaperId = testPaperId == null ? null : testPaperId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedTeacherId() {
        return createdTeacherId;
    }

    public void setCreatedTeacherId(String createdTeacherId) {
        this.createdTeacherId = createdTeacherId == null ? null : createdTeacherId.trim();
    }

    public Integer getPartNum() {
        return partNum;
    }

    public void setPartNum(Integer partNum) {
        this.partNum = partNum;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getExamPwd() {
        return examPwd;
    }

    public void setExamPwd(String examPwd) {
        this.examPwd = examPwd == null ? null : examPwd.trim();
    }

    public String getInvigilatePwd() {
        return invigilatePwd;
    }

    public void setInvigilatePwd(String invigilatePwd) {
        this.invigilatePwd = invigilatePwd == null ? null : invigilatePwd.trim();
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