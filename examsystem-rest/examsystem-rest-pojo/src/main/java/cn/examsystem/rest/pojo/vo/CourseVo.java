package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.Course;

/**
 * Created by Administrator on 2018/2/4.
 * 课程视图类
 */
public class CourseVo extends Course{
    private String majorId;
    //教师用户id
    private String sysuserId;

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getSysuserId() {
        return sysuserId;
    }

    public void setSysuserId(String sysuserId) {
        this.sysuserId = sysuserId;
    }

    @Override
    public void setName(String name) {
        try {
            System.out.println("初始------"+name);
            super.setName(new String(name.getBytes("iso8859-1"), "utf-8"));
        }catch (Exception e){
        }
    }
}
