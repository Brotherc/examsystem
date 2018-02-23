package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.QuestionKnowledgePointRelationDto;

/**
 * Created by Administrator on 2018/1/30.
 * 题目业务层接口
 */
public interface QuestionService {

    //将知识点录入到题目中
    public ResultInfo addKnowledgePointToQuestion(String id,String knowledgePointId,QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception;

    //将知识点从题目中移除
    public ResultInfo removeKnowledgePointForQuestion(String id,String knowledgePointId,QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception;
}
