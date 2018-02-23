package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.QuestionKnowledgePointRelationDto;
import cn.examsystem.rest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/30.
 * 题目服务控制类
 */
@RestController
public class QuestionController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private QuestionService questionService;


    @DeleteMapping(value = "/v1/question/{id}/knowledgePoint/{knowledgePointId}")
    public ResultInfo removeKnowledgePointForQuestion(@PathVariable String id, @PathVariable String knowledgePointId, @RequestBody QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception{
        return questionService.removeKnowledgePointForQuestion(id,knowledgePointId,questionKnowledgePointRelationDto);
    }

    @PostMapping("/v1/question/{id}/knowledgePoint/{knowledgePointId}")
    public ResultInfo addKnowledgePointToQuestion(@PathVariable String id, @PathVariable String knowledgePointId, @RequestBody QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception{
        return questionService.addKnowledgePointToQuestion(id,knowledgePointId,questionKnowledgePointRelationDto);
    }

}
