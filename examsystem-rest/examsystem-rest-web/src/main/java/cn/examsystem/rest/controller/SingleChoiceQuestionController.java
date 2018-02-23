package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SingleChoiceQuestionDto;
import cn.examsystem.rest.pojo.vo.SingleChoiceQuestionVo;
import cn.examsystem.rest.service.SingleChoiceQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 单选题服务控制类
 */
@RestController
public class SingleChoiceQuestionController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private SingleChoiceQuestionService singleChoiceQuestionService;

    @GetMapping("/v1/singleChoiceQuestion/{id}")
    public ResultInfo getSingleChoiceQuestion(@PathVariable String id) throws Exception{
        SingleChoiceQuestionDto singleChoiceQuestionDto = singleChoiceQuestionService.getSingleChoiceQuestion(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,singleChoiceQuestionDto);
        return resultInfo;
    }

    @GetMapping("/v1/singleChoiceQuestion")
    public ResultInfo listSingleChoiceQuestion(SingleChoiceQuestionVo singleChoiceQuestionVo) throws Exception{
        List<SingleChoiceQuestionDto> singleChoiceQuestionList = singleChoiceQuestionService.listSingleChoiceQuestion(singleChoiceQuestionVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,singleChoiceQuestionList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/singleChoiceQuestion")
    public ResultInfo btchDeleteSingleChoiceQuestion(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/singleChoiceQuestion")
    public ResultInfo saveSingleChoiceQuestion(@RequestBody SingleChoiceQuestionDto singleChoiceQuestionDto) throws Exception{
        return singleChoiceQuestionService.saveSingleChoiceQuestion(singleChoiceQuestionDto);
    }

    @PutMapping("/v1/singleChoiceQuestion/{id}")
    public ResultInfo updateSingleChoiceQuestion(@PathVariable String id,@RequestBody SingleChoiceQuestionDto singleChoiceQuestionDto) throws Exception{
        return singleChoiceQuestionService.updateSingleChoiceQuestion(id,singleChoiceQuestionDto);
    }
}
