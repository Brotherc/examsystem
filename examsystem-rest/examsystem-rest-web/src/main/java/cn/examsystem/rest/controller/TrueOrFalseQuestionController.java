package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;
import cn.examsystem.rest.service.TrueOrFalseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 判断题服务控制类
 */
@RestController
public class TrueOrFalseQuestionController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private TrueOrFalseQuestionService trueOrFalseQuestionService;

    @GetMapping("/v1/trueOrFalseQuestion/{id}")
    public ResultInfo getTrueOrFalseQuestion(@PathVariable String id) throws Exception{
        TrueOrFalseQuestionDto trueOrFalseQuestionDto = trueOrFalseQuestionService.getTrueOrFalseQuestion(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,trueOrFalseQuestionDto);
        return resultInfo;
    }

    @GetMapping("/v1/trueOrFalseQuestion")
    public ResultInfo listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo) throws Exception{
        List<TrueOrFalseQuestionDto> trueOrFalseQuestionDtoList = trueOrFalseQuestionService.listTrueOrFalseQuestion(trueOrFalseQuestionVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,trueOrFalseQuestionDtoList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/trueOrFalseQuestion")
    public ResultInfo btchDeleteTrueOrFalseQuestion(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/trueOrFalseQuestion")
    public ResultInfo saveTrueOrFalseQuestion(@RequestBody TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception{
        return trueOrFalseQuestionService.saveTrueOrFalseQuestion(trueOrFalseQuestionDto);
    }

    @PutMapping("/v1/trueOrFalseQuestion/{id}")
    public ResultInfo updateTrueOrFalseQuestion(@PathVariable String id,@RequestBody TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception{
        return trueOrFalseQuestionService.updateTrueOrFalseQuestion(id,trueOrFalseQuestionDto);
    }

    @PostMapping("/v1/trueOrFalseQuestion/file")
    public ResultInfo addTrueOrFalseQuestionByExcel(String createdTeacherId, @RequestBody byte[] uploadData,String fileName) throws Exception{
        return trueOrFalseQuestionService.addTrueOrFalseQuestionByExcel(createdTeacherId,fileName,uploadData);
    }
}
