package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.po.ExamstudentAnswer;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 成绩服务控制类
 */
@RestController
public class ScoreController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/v1/score/exam/{examId}")
    public ResultInfo listStudentScoreByExamId(@PathVariable String examId, ExamStudentRelationVo examStudentRelationVo) throws Exception{
        List<ExamStudentRelationDto> examStudentRelationDtoList = scoreService.listStudentScore(examId,examStudentRelationVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);
        return resultInfo;
    }

    @PutMapping("/v1/score/exam/{examId}")
    public ResultInfo autoGradeForExam(@PathVariable String examId) throws Exception{
        return scoreService.autoGradeForExam(examId);
    }

    @GetMapping("/v1/score/exam/student/{examStudentId}/testPaper")
    public ResultInfo getTestPaperByExamStudent(@PathVariable String examStudentId) throws Exception{
        TestPaperDto testPaperDto = scoreService.getTestPaperByExamStudent(examStudentId);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        return resultInfo;
    }

    @PutMapping("/v1/score/exam/student/{examStudentId}/testPaperQuestion/{testPaperQuestionId}")
    public ResultInfo updateTestPaperQuestionScore(@PathVariable String examStudentId, @PathVariable String testPaperQuestionId,@RequestBody ExamstudentAnswer examstudentAnswer) throws Exception{
        return scoreService.updateTestPaperQuestionScore(examStudentId,testPaperQuestionId,examstudentAnswer.getScore());
    }
}
