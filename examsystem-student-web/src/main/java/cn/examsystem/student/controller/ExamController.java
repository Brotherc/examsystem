package cn.examsystem.student.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.security.pojo.dto.StudentDto;
import cn.examsystem.student.utils.CookieUtils;
import cn.examsystem.student.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/v1/exam/loginStudent")
    public ResultInfo getExam(HttpSession session, HttpServletRequest request) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        StudentDto studentDto= (StudentDto) securityContext.getAuthentication().getPrincipal();

        System.out.println(studentDto.getId());

        ResultInfo resultInfo;
        try{

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(REST_BASE_URL + EXAM_URL+EXAM_LOGIN_STUDENT_URL+"/{studentId}", HttpMethod.GET, ResultInfo.class,new Object[]{studentDto.getId()});
            System.out.println("---------"+resultInfo);

            LinkedHashMap examStudentMap=(LinkedHashMap)resultInfo.getData();
            String examStudentJson = JsonUtils.objectToJson(examStudentMap);
            ExamStudentRelationDto examStudentRelationDto = JsonUtils.jsonToPojo(examStudentJson, ExamStudentRelationDto.class);

            if(examStudentRelationDto.getProceeded()){
                if(CookieUtils.getCookValue(request,examStudentRelationDto.getId())!=null)
                    examStudentRelationDto.setLocal(true);
                else

                    examStudentRelationDto.setLocal(false);
            }

            session.setAttribute(SESSION_KEY_EXAM_STUDENT,examStudentRelationDto);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
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
}
