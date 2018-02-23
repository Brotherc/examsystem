package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Major;

/**
 * Created by Administrator on 2018/2/3.
 * 专业（包括系名字）
 */
public class MajorDto extends Major{
    //系名字
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
