package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;
import cn.examsystem.rest.service.FillInBlankQuestionService;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private FillInBlankQuestionService fillInBlankQuestionService;

    @GetMapping("/v1/fillInBlankQuestion/{id}")
    public ResultInfo getFillInBlankQuestion(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            FillInBlankQuestionDto fillInBlankQuestionDto = fillInBlankQuestionService.getFillInBlankQuestion(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,fillInBlankQuestionDto);
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

            //调用rest服务
            List<FillInBlankQuestionDto> fillInBlankQuestionDtoList = fillInBlankQuestionService.listFillInBlankQuestion(fillInBlankQuestionVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,fillInBlankQuestionDtoList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/fillInBlankQuestion")
    public ResultInfo btchDeleteFillInBlankQuestion(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
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
            resultInfo=fillInBlankQuestionService.saveFillInBlankQuestion(fillInBlankQuestionDto);
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
            resultInfo=fillInBlankQuestionService.updateFillInBlankQuestion(id,fillInBlankQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
