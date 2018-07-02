package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SingleChoiceQuestionDto;
import cn.examsystem.rest.pojo.vo.SingleChoiceQuestionVo;
import cn.examsystem.rest.service.SingleChoiceQuestionService;
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
 * 单选题控制器类
 */
@RestController
public class SingleChoiceQuestionController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${SINGLECHOICEQUESTION_URL}")
    private String SINGLECHOICEQUESTION_URL;
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
    private SingleChoiceQuestionService singleChoiceQuestionService;

    @GetMapping("/v1/singleChoiceQuestion/{id}")
    public ResultInfo getSingleChoiceQuestion(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            SingleChoiceQuestionDto singleChoiceQuestionDto = singleChoiceQuestionService.getSingleChoiceQuestion(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,singleChoiceQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/singleChoiceQuestion")
    public ResultInfo listSingleChoiceQuestion(SingleChoiceQuestionVo singleChoiceQuestionVo) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            List<SingleChoiceQuestionDto> singleChoiceQuestionList = singleChoiceQuestionService.listSingleChoiceQuestion(singleChoiceQuestionVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,singleChoiceQuestionList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/singleChoiceQuestion")
    public ResultInfo btchDeleteSingleChoiceQuestion(@RequestParam(value = "ids[]") String[] ids) throws Exception{

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

    @PostMapping("/v1/singleChoiceQuestion")
    public ResultInfo saveSingleChoiceQuestion(SingleChoiceQuestionDto singleChoiceQuestionDto, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        singleChoiceQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=singleChoiceQuestionService.saveSingleChoiceQuestion(singleChoiceQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/singleChoiceQuestion/{id}")
    public ResultInfo updateSingleChoiceQuestion(@PathVariable String id, SingleChoiceQuestionDto singleChoiceQuestionDto,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        singleChoiceQuestionDto.setCreatedTeacherId(sysuserDto.getId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=singleChoiceQuestionService.updateSingleChoiceQuestion(id,singleChoiceQuestionDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/singleChoiceQuestion/file")
    public ResultInfo addSingleChoiceQuestionByExcel( MultipartFile upload,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        System.out.println(upload.getOriginalFilename());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=singleChoiceQuestionService.addSingleChoiceQuestionByExcel(sysuserDto.getId(),upload.getOriginalFilename(),upload.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }
}
