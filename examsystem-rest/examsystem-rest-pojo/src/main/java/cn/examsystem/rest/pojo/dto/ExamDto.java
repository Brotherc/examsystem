package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Exam;

/**
 * Created by Administrator on 2018/2/6.
 * 班级
 */
public class ExamDto extends Exam{
    private String testPaperName;
    private String courseName;
    private String schoolYearName;
    private String statusName;
    private String createdTeacherName;

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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreatedTeacherName() {
        return createdTeacherName;
    }

    public void setCreatedTeacherName(String createdTeacherName) {
        this.createdTeacherName = createdTeacherName;
    }

    public String getTestPaperName() {
        return testPaperName;
    }

    public void setTestPaperName(String testPaperName) {
        this.testPaperName = testPaperName;
    }
}
