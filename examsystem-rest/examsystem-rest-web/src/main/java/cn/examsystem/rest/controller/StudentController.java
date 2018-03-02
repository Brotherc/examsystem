package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

/*    @GetMapping("/v1/student")
    public ResultInfo listStudent(Student student) throws Exception{
        List<MajorDto> majorList = majorService.listMajor(majorVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,majorList);
        return resultInfo;
    }*/

/*    @DeleteMapping(value = "/v1/major")
    public ResultInfo btchDeleteMajor(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }*/

/*    @PostMapping("/v1/major")
    public ResultInfo saveMajor(@RequestBody Major major) throws Exception{
        return majorService.saveMajor(major);
    }*/

    @PutMapping("/v1/student/{id}")
    public ResultInfo updateStudent(@PathVariable String id,@RequestBody Student student) throws Exception{
        return studentService.updateStudent(id,student);
    }
}
