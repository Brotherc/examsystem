package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Student;

/**
 * Created by Administrator on 2018/2/6.
 * 班级（包括班级信息，考试场次信息）
 */
public class StudentDto extends Student {
    private String gradeName;
    private String majorName;
    private Integer className;
    private Integer partOrder;

    public Integer getPartOrder() {
        return partOrder;
    }

    public void setPartOrder(Integer partOrder) {
        this.partOrder = partOrder;
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
}
