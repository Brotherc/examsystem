package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ProgramQuestionDto;
import cn.examsystem.rest.pojo.po.ProgramQuestion;
import cn.examsystem.rest.pojo.po.ProgramQuestionWithBLOBs;
import cn.examsystem.rest.service.ProgramQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 * 程序题控制器类
 */
@RestController
public class ProgramQuestionController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${PROGRAMQUESTION_URL}")
    private String PROGRAMQUESTION_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private ProgramQuestionService programQuestionService;

    @GetMapping("/v1/programQuestion/{id}")
    public ResultInfo getProgramQuestion(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            ProgramQuestionDto programQuestion = programQuestionService.getProgramQuestion(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,programQuestion);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/programQuestion")
    public ResultInfo listProgramQuestion(ProgramQuestion programQuestion) throws Exception{

        ResultInfo resultInfo;
        try{


            //调用rest服务
            List<ProgramQuestionWithBLOBs> programQuestionList = programQuestionService.listProgramQuestion(programQuestion);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,programQuestionList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

}
