package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Student;

/**
 * Created by Administrator on 2018/1/30.
 * 学生业务层接口
 */
public interface StudentService {

    //更新学生
    public ResultInfo updateStudent(String id, Student student) throws Exception;
}
