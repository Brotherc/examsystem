package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static cn.examsystem.common.utils.UrlUtils.expandURL;

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

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @GetMapping("/v1/exam")
    public ResultInfo listExam(Exam exam) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(exam);
            String url = expandURL(REST_BASE_URL + EXAM_URL+"?", obj);

            System.out.print(url);

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/exam")
    public ResultInfo btchDeleteExam(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL,HttpMethod.DELETE,ids,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/exam")
    public ResultInfo saveExam(Exam exam, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        exam.setCreatedTeacherId(sysuserDto.getId());
        System.out.println(exam.getStartTime());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL,HttpMethod.POST,exam,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/{id}")
    public ResultInfo updateExam(@PathVariable String id, Exam exam) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}",HttpMethod.PUT,exam,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
