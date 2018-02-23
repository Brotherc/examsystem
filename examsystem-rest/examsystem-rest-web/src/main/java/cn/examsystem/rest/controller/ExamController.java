package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 考试服务控制类
 */
@RestController
public class ExamController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private ExamService examService;

    @GetMapping("/v1/exam")
    public ResultInfo listExam(Exam exam) throws Exception{
        List<ExamDto> examDtoList = examService.listExam(exam);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examDtoList);
        System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }

    @DeleteMapping(value = "/v1/exam")
    public ResultInfo btchDeleteExam(@RequestBody String[] ids) throws Exception{

        for(String s:ids){
            System.out.println(s);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/exam")
    public ResultInfo saveExam(@RequestBody Exam exam) throws Exception{
        System.out.println(exam.getStartTime());
        return examService.saveExam(exam);
    }

    @PutMapping("/v1/exam/{id}")
    public ResultInfo updateExam(@PathVariable String id,@RequestBody Exam exam) throws Exception{
        return examService.updateExam(id,exam);
    }
}
