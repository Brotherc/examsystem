package cn.examsystem.rest.controller;

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
 * Created by Administrator on 2018/1/30.
 * 程序题服务控制类
 */
@RestController
public class ProgramQuestionController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private ProgramQuestionService programQuestionService;

    @GetMapping("/v1/programQuestion/{id}")
    public ResultInfo getProgramQuestion(@PathVariable String id) throws Exception{
        ProgramQuestionDto programQuestion = programQuestionService.getProgramQuestion(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,programQuestion);
        return resultInfo;
    }

    @GetMapping("/v1/programQuestion")
    public ResultInfo listProgramQuestion(ProgramQuestion programQuestion) throws Exception{
        List<ProgramQuestionWithBLOBs> programQuestionList = programQuestionService.listProgramQuestion(programQuestion);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,programQuestionList);
        return resultInfo;
    }
}
