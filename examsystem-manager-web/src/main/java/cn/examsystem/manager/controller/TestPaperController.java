package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.po.TestPaper;
import cn.examsystem.rest.pojo.vo.TestPaperVo;
import cn.examsystem.rest.service.TestPaperService;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    @Value("${TESTPAPER_QUESTION_URL}")
    private String TESTPAPER_QUESTION_URL;


    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private TestPaperService testPaperService;


    @GetMapping("/v1/testPaper/{id}")
    public ResultInfo getTestPaper(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            TestPaperDto testPaperDto = testPaperService.getTestPaper(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
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

            //调用rest服务
            List<TestPaper> testPaperList = testPaperService.listTestPaper(testPaperVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperList);
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
            resultInfo= testPaperService.saveTestPaper(testPaperDto);
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
            resultInfo=testPaperService.updateTestPaper(id,testPaperDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/testPaper/{id}/question")
    public ResultInfo listTestPaperQuestionByTestPaperId(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            TestPaperDto testPaperDto = testPaperService.listTestPaperQuestionByTestPaperId(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/testPaper/{testPaperId}/question")
    public ResultInfo removeQuestionFromTestPaper(@PathVariable String testPaperId,@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=testPaperService.removeQuestionFromTestPaper(testPaperId,ids);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/testPaper/{id}/question/{questionId}")
    public ResultInfo updateTestPaperQuestionOrder(@PathVariable String id,@PathVariable String questionId,Integer order,HttpSession session) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=testPaperService.updateTestPaperQuestionOrder(id,questionId,order);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

}
