package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.po.ExamstudentAnswer;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 * 成绩控制器类
 */
@RestController
public class ScoreController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${SCORE_URL}")
    private String SCORE_URL;
    @Value("${SCORE_EXAM_URL}")
    private String SCORE_EXAM_URL;
    @Value("${SCORE_STUDENT_URL}")
    private String SCORE_STUDENT_URL;
    @Value("${SCORE_TESTPAPER_URL}")
    private String SCORE_TESTPAPER_URL;
    @Value("${SCORE_TESTPAPERQUESTION_URL}")
    private String SCORE_TESTPAPERQUESTION_URL;


    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Value("${MODEL_KEY_SINGLECHOICEQUESTIONS}")
    private String MODEL_KEY_SINGLECHOICEQUESTIONS;
    @Value("${MODEL_KEY_TRUEORFALSEQUESTIONS}")
    private String MODEL_KEY_TRUEORFALSEQUESTIONS;
    @Value("${MODEL_KEY_FILLINBLANKQUESTIONS}")
    private String MODEL_KEY_FILLINBLANKQUESTIONS;
    @Value("${MODEL_KEY_SINGLECHOICEQUESTION_ANSWER}")
    private String MODEL_KEY_SINGLECHOICEQUESTION_ANSWER;
    @Value("${MODEL_KEY_TRUEORFALSEQUESTION_ANSWER}")
    private String MODEL_KEY_TRUEORFALSEQUESTION_ANSWER;
    @Value("${MODEL_KEY_FILLINBLANKQUESTION_ANSWER}")
    private String MODEL_KEY_FILLINBLANKQUESTION_ANSWER;
    @Value("${MODEL_KEY_TESTPAPER}")
    private String MODEL_KEY_TESTPAPER;

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/v1/score/exam/{examId}")
    public ResultInfo listStudentScore(@PathVariable String examId, ExamStudentRelationVo examStudentRelationVo) throws Exception{

        ResultInfo resultInfo;
        try{


            //调用rest服务
            List<ExamStudentRelationDto> examStudentRelationDtoList = scoreService.listStudentScore(examId,examStudentRelationVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/score/exam/{examId}")
    public ResultInfo autoGradeForExam(@PathVariable String examId) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=scoreService.autoGradeForExam(examId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
    @GetMapping("/v1/score/exam/student/{examStudentId}/testPaper")
    public ResultInfo getTestPaperByExamStudent(@PathVariable String examStudentId, Model model) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            TestPaperDto testPaperDto = scoreService.getTestPaperByExamStudent(examStudentId);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }

        return resultInfo;
    }

    @PutMapping("/v1/score/exam/student/{examStudentId}/testPaperQuestion/{testPaperQuestionId}")
    public ResultInfo updateTestPaperQuestionScore(@PathVariable String examStudentId, @PathVariable String testPaperQuestionId, ExamstudentAnswer examstudentAnswer) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo = scoreService.updateTestPaperQuestionScore(examStudentId,testPaperQuestionId,examstudentAnswer.getScore());
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
