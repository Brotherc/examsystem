package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;
import cn.examsystem.rest.service.TrueOrFalseQuestionService;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 * 判断题控制器类
 */
@RestController
public class TrueOrFalseQuestionController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${TRUEORFALSEQUESTION_URL}")
    private String TRUEORFALSEQUESTION_URL;
    @Value("${QUESTION_FILE_URL}")
    private String QUESTION_FILE_URL;


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
    private TrueOrFalseQuestionService trueOrFalseQuestionService;

    @GetMapping("/v1/trueOrFalseQuestion/{id}")
    public ResultInfo getTrueOrFalseQuestion(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            TrueOrFalseQuestionDto trueOrFalseQuestionDto = trueOrFalseQuestionService.getTrueOrFalseQuestion(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,trueOrFalseQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/trueOrFalseQuestion")
    public ResultInfo listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            List<TrueOrFalseQuestionDto> trueOrFalseQuestionDtoList = trueOrFalseQuestionService.listTrueOrFalseQuestion(trueOrFalseQuestionVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,trueOrFalseQuestionDtoList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/trueOrFalseQuestion")
    public ResultInfo btchDeleteTrueOrFalseQuestion(@RequestParam(value = "ids[]") String[] ids) throws Exception{

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

    @PostMapping("/v1/trueOrFalseQuestion")
    public ResultInfo saveTrueOrFalseQuestion(TrueOrFalseQuestionDto trueOrFalseQuestionDto, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        trueOrFalseQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=trueOrFalseQuestionService.saveTrueOrFalseQuestion(trueOrFalseQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/trueOrFalseQuestion/{id}")
    public ResultInfo updateTrueOrFalseQuestion(@PathVariable String id, TrueOrFalseQuestionDto trueOrFalseQuestionDto,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        trueOrFalseQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo= trueOrFalseQuestionService.updateTrueOrFalseQuestion(id,trueOrFalseQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/trueOrFalseQuestion/file")
    public ResultInfo addTrueOrFalseQuestionByExcel(MultipartFile upload, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        System.out.println(upload.getOriginalFilename());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=trueOrFalseQuestionService.addTrueOrFalseQuestionByExcel(sysuserDto.getId(),upload.getOriginalFilename(),upload.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }
}
