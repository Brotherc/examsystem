package cn.examsystem.student.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.security.pojo.dto.StudentDto;
import cn.examsystem.student.service.TestService;
import cn.examsystem.student.utils.CookieUtils;
import cn.examsystem.student.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/1/28.
 * 考试控制器类
 */
@RestController
public class ExamController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${EXAM_URL}")
    private String EXAM_URL;
    @Value("${EXAM_LOGIN_STUDENT_URL}")
    private String EXAM_LOGIN_STUDENT_URL;
    @Value("${TEST_URL}")
    private String TEST_URL;
    @Value("${TEST_TESTPAPER_URL}")
    private String TEST_TESTPAPER_URL;

    @Value("${SESSION_KEY_EXAM_STUDENT}")
    private String SESSION_KEY_EXAM_STUDENT;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @Autowired
    private TestService testService;

    @GetMapping("/v1/test/loginStudent")
    public ResultInfo getExam(HttpSession session, HttpServletRequest request) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        StudentDto studentDto= (StudentDto) securityContext.getAuthentication().getPrincipal();

        ResultInfo resultInfo;
        try{

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(REST_BASE_URL + EXAM_URL+EXAM_LOGIN_STUDENT_URL+"/{studentId}", HttpMethod.GET, ResultInfo.class,new Object[]{studentDto.getId()});

            if(resultInfo.getStatus()==ResultInfo.STATUS_RESULT_OK){
                LinkedHashMap examStudentMap=(LinkedHashMap)resultInfo.getData();
                String examStudentJson = JsonUtils.objectToJson(examStudentMap);
                ExamStudentRelationDto examStudentRelationDto = JsonUtils.jsonToPojo(examStudentJson, ExamStudentRelationDto.class);

                System.out.println(examStudentRelationDto.getIsProceeded());
                if(examStudentRelationDto.getIsProceeded()){
                    System.out.println("考过试");
                    if(CookieUtils.getCookValue(request,examStudentRelationDto.getId())!=null){
                        examStudentRelationDto.setIsLocal(true);
System.out.println("本地");
                    }
                    else{
                        System.out.println("不是本地");
                        examStudentRelationDto.setIsLocal(false);
                    }
                }
                else{
                    System.out.println("没考过试");
                    examStudentRelationDto.setIsLocal(false);
                }
System.out.println(examStudentRelationDto.getIsLocal());
                session.setAttribute(SESSION_KEY_EXAM_STUDENT,examStudentRelationDto);

                resultInfo.setData(examStudentRelationDto);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_NOT_FOUND,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/test")
    public ResultInfo test(ExamStudentRelationDto studentRelationDto, HttpServletRequest request, HttpServletResponse response) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+TEST_URL,HttpMethod.POST,studentRelationDto,ResultInfo.class,new Object[]{});

            if(resultInfo.getStatus()==ResultInfo.STATUS_RESULT_CREATED){
                //添加学生考试信息进入考试cookie，并保存本地
                String examStudentCookie = CookieUtils.getCookValue(request, studentRelationDto.getId());
                if(examStudentCookie!=null){
                    int num=Integer.valueOf(examStudentCookie)+1;
                    Cookie cookie=new Cookie(studentRelationDto.getId(), num+"");
                    cookie.setMaxAge(60*60*3);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }else{
                    Cookie cookie=new Cookie(studentRelationDto.getId(), "1");
                    cookie.setMaxAge(60*60*3);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/test/singleChoiceQuestion/answer")
    public ResultInfo saveSingleChoiceQuestionAnswer(HttpSession session, TestPaperDto testPaperDto) throws Exception{

        System.out.println(testPaperDto.getSingleChoiceQuestionAnswer());
        ExamStudentRelationDto examStudentRelationDto = (ExamStudentRelationDto)session.getAttribute(SESSION_KEY_EXAM_STUDENT);
        System.out.println(examStudentRelationDto.getId());

        return testService.saveSingleChoiceQuestionAnswer(examStudentRelationDto.getId(),testPaperDto);
    }

    @PostMapping("/v1/test/trueOrFalseQuestion/answer")
    public ResultInfo saveTrueOrFalseQuestionAnswer(HttpSession session, TestPaperDto testPaperDto) throws Exception{

        System.out.println(testPaperDto.getTrueOrFalseQuestionAnswer());

        ExamStudentRelationDto examStudentRelationDto = (ExamStudentRelationDto)session.getAttribute(SESSION_KEY_EXAM_STUDENT);
        return testService.saveTrueOrFalseQuestionAnswer(examStudentRelationDto.getId(),testPaperDto);
    }

    @PostMapping("/v1/test/fillInBlankQuestion/answer")
    public ResultInfo saveFillInBlankQuestionAnswer(HttpSession session, TestPaperDto testPaperDto) throws Exception{

        System.out.println(testPaperDto.getFillInBlankQuestionAnswer());

        ExamStudentRelationDto examStudentRelationDto = (ExamStudentRelationDto)session.getAttribute(SESSION_KEY_EXAM_STUDENT);

        System.out.println(examStudentRelationDto.getId());
        return testService.saveFillInBlankQuestionAnswer(examStudentRelationDto.getId(),testPaperDto);

    }

    @PostMapping("/v1/test/testPaper/{testPaperId}")
    public ResultInfo submitTestPape(HttpSession session, @PathVariable String testPaperId,HttpServletRequest request,HttpServletResponse response) throws Exception{

        ExamStudentRelationDto examStudentRelationDto = (ExamStudentRelationDto)session.getAttribute(SESSION_KEY_EXAM_STUDENT);

        System.out.println(examStudentRelationDto.getId());
        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+TEST_URL+TEST_TESTPAPER_URL+"/{testPaperId}",HttpMethod.POST,examStudentRelationDto,ResultInfo.class,new Object[]{testPaperId});

            if(resultInfo.getStatus()==ResultInfo.STATUS_RESULT_CREATED){
                //将学生考试cookie删除
                Cookie cookie = CookieUtils.getCook(request, examStudentRelationDto.getId());
                if(cookie!=null){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;

    }
}
