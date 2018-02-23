package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;
import cn.examsystem.rest.service.FillInBlankQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 判断题服务控制类
 */
@RestController
public class FillInBlankQuestionController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private FillInBlankQuestionService fillInBlankQuestionService;

    @GetMapping("/v1/fillInBlankQuestion/{id}")
    public ResultInfo getFillInBlankQuestion(@PathVariable String id) throws Exception{
        FillInBlankQuestionDto fillInBlankQuestionDto = fillInBlankQuestionService.getFillInBlankQuestion(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,fillInBlankQuestionDto);
        return resultInfo;
    }

    @GetMapping("/v1/fillInBlankQuestion")
    public ResultInfo listFillInBlankQuestion(FillInBlankQuestionVo fillInBlankQuestionVo) throws Exception{
        List<FillInBlankQuestionDto> fillInBlankQuestionDtoList = fillInBlankQuestionService.listFillInBlankQuestion(fillInBlankQuestionVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,fillInBlankQuestionDtoList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/fillInBlankQuestion")
    public ResultInfo btchDeleteFillInBlankQuestion(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/fillInBlankQuestion")
    public ResultInfo saveFillInBlankQuestion(@RequestBody FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception{
        return fillInBlankQuestionService.saveFillInBlankQuestion(fillInBlankQuestionDto);
    }

    @PutMapping("/v1/fillInBlankQuestion/{id}")
    public ResultInfo updateFillInBlankQuestion(@PathVariable String id,@RequestBody FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception{
        return fillInBlankQuestionService.updateFillInBlankQuestion(id,fillInBlankQuestionDto);
    }
}
