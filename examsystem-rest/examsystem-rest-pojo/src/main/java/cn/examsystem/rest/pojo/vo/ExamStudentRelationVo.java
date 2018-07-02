package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;

/**
 * Created by Administrator on 2018/2/6.
 */
public class ExamStudentRelationVo extends ExamStudentRelationDto{

    @Override
    public void setStudentName(String studentName) {
        try {
            //System.out.println("初始------"+studentName);
            super.setStudentName(new String(studentName.getBytes("iso8859-1"), "utf-8"));
        }catch (Exception e){
        }
    }
}
