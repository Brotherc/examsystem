package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.ExamStudentRelation;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/6.
 * 班级（包括学生信息）
 */
public class ExamStudentRelationDto extends ExamStudentRelation{
    private String studentStudentId;
    private String studentName;
    private String studentClassId;
    private String departmentName;
    private String gradeName;
    private String majorName;
    private Integer className;
    private Date lastLoginTime;
    private String lastLoginIp;

    private String courseName;
    private String schoolYearName;
    private Integer term;
    private Integer time;
    private String testPaperId;

    private Date partOrderStartTime;

    private String examPwd;
    private String invigilatePwd;

    private Boolean isProceeded;//学生是否进行过该门考试
    private Boolean isLocal;//学生是否在本地机器操作

    private Long remainTime;//考试学生该门考试剩余时间（单位：毫秒）


    public String getStudentStudentId() {
        return studentStudentId;
    }

    public void setStudentStudentId(String studentStudentId) {
        this.studentStudentId = studentStudentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClassId() {
        return studentClassId;
    }

    public void setStudentClassId(String studentClassId) {
        this.studentClassId = studentClassId;
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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Date getPartOrderStartTime() {
        return partOrderStartTime;
    }

    public void setPartOrderStartTime(Date partOrderStartTime) {
        this.partOrderStartTime = partOrderStartTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getClassName() {
        return className;
    }

    public void setClassName(Integer className) {
        this.className = className;
    }

    public String getExamPwd() {
        return examPwd;
    }

    public void setExamPwd(String examPwd) {
        this.examPwd = examPwd;
    }

    public String getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(String testPaperId) {
        this.testPaperId = testPaperId;
    }

    public Boolean getIsProceeded() {
        return isProceeded;
    }

    public void setIsProceeded(Boolean isProceeded) {
        this.isProceeded = isProceeded;
    }


    public String getInvigilatePwd() {
        return invigilatePwd;
    }

    public void setInvigilatePwd(String invigilatePwd) {
        this.invigilatePwd = invigilatePwd;
    }

    public Boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Long remainTime) {
        this.remainTime = remainTime;
    }
}
