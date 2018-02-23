package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.vo.TestPaperVo;
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
 * 试卷控制器类
 */
@RestController
public class TestPaperController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${TESTPAPER_URL}")
    private String TESTPAPER_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;


    @GetMapping("/v1/testPaper/{id}")
    public ResultInfo getTestPaper(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+TESTPAPER_URL+"/{id}", HttpMethod.GET, ResultInfo.class,new Object[]{id});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/testPaper")
    public ResultInfo listTestPaper(TestPaperVo testPaperVo,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        testPaperVo.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(testPaperVo);
            String url = expandURL(REST_BASE_URL + TESTPAPER_URL+"?", obj);

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

    @PostMapping("/v1/testPaper")
    public ResultInfo saveTestPaper(TestPaperDto testPaperDto, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        testPaperDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        System.out.println(testPaperDto.getSingleChoiceQuestionIds());
        System.out.println(testPaperDto.getFillInBlankQuestionIds());
        System.out.println(testPaperDto.getSingleChoiceQuestionScore());
        System.out.println(testPaperDto.getFillInBlankQuestionScore());

        try {
            //调用rest服务
            resultInfo= RestTemplateUtils.exchange(REST_BASE_URL+TESTPAPER_URL, HttpMethod.POST,testPaperDto,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/testPaper/{id}")
    public ResultInfo updateTestPaper(@PathVariable String id, TestPaperDto testPaperDto,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        testPaperDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+TESTPAPER_URL+"/{id}",HttpMethod.PUT,testPaperDto,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

}
