package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;
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
 * 填空题控制器类
 */
@RestController
public class FillInBlankQuestionController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${FILLINBLANKQUESTION_URL}")
    private String FILLINBLANKQUESTION_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @GetMapping("/v1/fillInBlankQuestion/{id}")
    public ResultInfo getFillInBlankQuestion(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+FILLINBLANKQUESTION_URL+"/{id}", HttpMethod.GET, ResultInfo.class,new Object[]{id});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/fillInBlankQuestion")
    public ResultInfo listFillInBlankQuestion(FillInBlankQuestionVo fillInBlankQuestionVo) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(fillInBlankQuestionVo);
            String url = expandURL(REST_BASE_URL + FILLINBLANKQUESTION_URL+"?", obj);

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

    @DeleteMapping("/v1/fillInBlankQuestion")
    public ResultInfo btchFillInBlankQuestion(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+FILLINBLANKQUESTION_URL,HttpMethod.DELETE,ids,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/fillInBlankQuestion")
    public ResultInfo saveFillInBlankQuestion(FillInBlankQuestionDto fillInBlankQuestionDto, HttpSession session) throws Exception{
        System.out.println(fillInBlankQuestionDto.getAnswerList());

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        fillInBlankQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+FILLINBLANKQUESTION_URL,HttpMethod.POST,fillInBlankQuestionDto,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/fillInBlankQuestion/{id}")
    public ResultInfo updateFillInBlankQuestion(@PathVariable String id, FillInBlankQuestionDto fillInBlankQuestionDto,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        fillInBlankQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+FILLINBLANKQUESTION_URL+"/{id}",HttpMethod.PUT,fillInBlankQuestionDto,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
