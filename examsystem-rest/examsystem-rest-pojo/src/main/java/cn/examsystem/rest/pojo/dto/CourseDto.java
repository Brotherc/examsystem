package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Course;
import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.po.Sysuser;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 * 课程类（包括专业信息,教师信息）
 */
public class CourseDto extends Course{
    private List<Major> majors;
    private List<Sysuser> teachers;
    private String majorsId;
    private String teachersId;

    public List<Major> getMajors() {
        return majors;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }

    public String getMajorsId() {
        return majorsId;
    }

    public void setMajorsId(String majorsId) {
        this.majorsId = majorsId;
    }

    public List<Sysuser> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Sysuser> teachers) {
        this.teachers = teachers;
    }

    public String getTeachersId() {
        return teachersId;
    }

    public void setTeachersId(String teachersId) {
        this.teachersId = teachersId;
    }
}
