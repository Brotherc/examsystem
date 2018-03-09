package cn.examsystem.student.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.student.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/1/25.
 */
@Controller
public class PageController {

    @Value("${SESSION_KEY_EXAM_STUDENT}")
    private String SESSION_KEY_EXAM_STUDENT;


    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${TEST_URL}")
    private String TEST_URL;
    @Value("${TEST_TESTPAPER_URL}")
    private String TEST_TESTPAPER_URL;

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
    @Value("${MODEL_KEY_EXAM_STUDENT_REMAIN_TIME}")
    private String MODEL_KEY_EXAM_STUDENT_REMAIN_TIME;
    @Value("${MODEL_KEY_EXAM_STUDENT_START_TIME}")
    private String MODEL_KEY_EXAM_STUDENT_START_TIME;

    @Value("${MODEL_KEY_EXAM_STUDENT}")
    private String MODEL_KEY_EXAM_STUDENT;
    @Value("${MODEL_KEY_TESTPAPER}")
    private String MODEL_KEY_TESTPAPER;

    @GetMapping("/v1/test/testPaper/{testPaperId}")
    public String toTestPaperPage(@PathVariable String testPaperId, HttpSession session, Model model) throws Exception{

        ExamStudentRelationDto examStudentRelationDto = (ExamStudentRelationDto)session.getAttribute(SESSION_KEY_EXAM_STUDENT);

        System.out.println(examStudentRelationDto.getCourseName());
        System.out.println(examStudentRelationDto.getSchoolYearName());
        System.out.println(examStudentRelationDto.getTerm());
        System.out.println(examStudentRelationDto.getPartOrderStartTime());
        System.out.println(examStudentRelationDto.getTime());

        //加载试卷及题目信息
        TestPaperDto testPaperDto=null;
        LinkedHashMap testPaperMap=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+TEST_URL+TEST_TESTPAPER_URL+"/{testPaperId}"+"?examStudentId="+examStudentRelationDto.getId(), HttpMethod.GET, ResultInfo.class,new Object[]{testPaperId});
            testPaperMap=(LinkedHashMap)resultInfo.getData();
            String testPaperJson = JsonUtils.objectToJson(testPaperMap);
            testPaperDto=JsonUtils.jsonToPojo(testPaperJson,TestPaperDto.class);

            System.out.println(testPaperDto.getSingleChoiceQuestions().get(0).getQuestionOrder());
            System.out.println(testPaperDto.getTrueOrFalseQuestions().size());
            System.out.println(testPaperDto.getFillInBlankQuestions().size());

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(examStudentRelationDto.getPartOrderStartTime());
        //计算剩余考试时间
        Long endTime=examStudentRelationDto.getPartOrderStartTime().getTime()+examStudentRelationDto.getTime()*1000;
        Long currentTime=System.currentTimeMillis();
        model.addAttribute(MODEL_KEY_EXAM_STUDENT_REMAIN_TIME, endTime-currentTime);
        model.addAttribute(MODEL_KEY_EXAM_STUDENT_START_TIME, examStudentRelationDto.getPartOrderStartTime().getTime());

        //保存到前台
        model.addAttribute(MODEL_KEY_SINGLECHOICEQUESTIONS,testPaperMap.get("singleChoiceQuestions"));
        model.addAttribute(MODEL_KEY_TRUEORFALSEQUESTIONS,testPaperMap.get("trueOrFalseQuestions"));
        model.addAttribute(MODEL_KEY_FILLINBLANKQUESTIONS,testPaperMap.get("fillInBlankQuestions"));

        model.addAttribute(MODEL_KEY_SINGLECHOICEQUESTION_ANSWER,testPaperMap.get("singleChoiceQuestionAnswer"));
        model.addAttribute(MODEL_KEY_TRUEORFALSEQUESTION_ANSWER,testPaperMap.get("trueOrFalseQuestionAnswer"));
        model.addAttribute(MODEL_KEY_FILLINBLANKQUESTION_ANSWER,testPaperMap.get("fillInBlankQuestionAnswer"));

        model.addAttribute(MODEL_KEY_EXAM_STUDENT,examStudentRelationDto);
        model.addAttribute(MODEL_KEY_TESTPAPER,testPaperMap);


        return "testPaper/list";
    }
}
