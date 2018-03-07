package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.vo.StudentVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 学生业务层接口
 */
public interface StudentService {

    //更新学生
    public ResultInfo updateStudent(String id, Student student) throws Exception;

    //根据条件查询学生
    public List<Student> listStudent(StudentVo studentVo) throws Exception;

    //添加学生
    public ResultInfo saveStudent(Student student) throws Exception;

}
