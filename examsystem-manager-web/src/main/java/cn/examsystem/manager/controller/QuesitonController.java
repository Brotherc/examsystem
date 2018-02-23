package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.QuestionKnowledgePointRelationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/28.
 * 题目控制器类
 */
@RestController
public class QuesitonController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${QUESTION_URL}")
    private String QUESTION_URL;
    @Value("${QUESTION_KNOWLEDGEPOINT_URL}")
    private String QUESTION_KNOWLEDGEPOINT_URL;

    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @DeleteMapping("/v1/question/{id}/knowledgePoint/{knowledgePointId}")
    public ResultInfo removeKnowledgePointForQuestion(@PathVariable String id,@PathVariable String knowledgePointId,QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+QUESTION_URL+"/{id}"+QUESTION_KNOWLEDGEPOINT_URL+"/{knowledgePointId}",HttpMethod.DELETE,questionKnowledgePointRelationDto,ResultInfo.class,new Object[]{id,knowledgePointId});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/question/{id}/knowledgePoint/{knowledgePointId}")
    public ResultInfo addKnowledgePointToQuestion(@PathVariable String id,@PathVariable String knowledgePointId, QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+QUESTION_URL+"/{id}"+QUESTION_KNOWLEDGEPOINT_URL+"/{knowledgePointId}",HttpMethod.POST,questionKnowledgePointRelationDto,ResultInfo.class,new Object[]{id,knowledgePointId});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

}
