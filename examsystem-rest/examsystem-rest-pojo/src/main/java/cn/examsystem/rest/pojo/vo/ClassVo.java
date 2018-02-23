package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.Class;

/**
 * Created by Administrator on 2018/2/6.
 */
public class ClassVo extends Class{
    private String departmentId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

}
