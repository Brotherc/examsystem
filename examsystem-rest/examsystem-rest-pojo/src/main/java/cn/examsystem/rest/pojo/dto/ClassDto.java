package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Class;

/**
 * Created by Administrator on 2018/2/6.
 * 班级（包括系信息）
 */
public class ClassDto extends Class{
    private String majorName;
    private String gradeName;
    private String departmentId;
    private String departmentName;
    private String names;

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
