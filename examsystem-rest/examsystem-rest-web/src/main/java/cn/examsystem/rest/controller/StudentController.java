package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.vo.StudentVo;
import cn.examsystem.rest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 学生服务控制类
 */
@RestController
public class StudentController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private StudentService studentService;

    @GetMapping("/v1/student")
    public ResultInfo listStudent(StudentVo studentVo) throws Exception{
        List<Student> studentList = studentService.listStudent(studentVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,studentList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/student")
    public ResultInfo btchDeleteStudent(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/student")
    public ResultInfo saveStudent(@RequestBody Student student) throws Exception{
        return studentService.saveStudent(student);
    }

    @PutMapping("/v1/student/{id}")
    public ResultInfo updateStudent(@PathVariable String id,@RequestBody Student student) throws Exception{
        return studentService.updateStudent(id,student);
    }
}
