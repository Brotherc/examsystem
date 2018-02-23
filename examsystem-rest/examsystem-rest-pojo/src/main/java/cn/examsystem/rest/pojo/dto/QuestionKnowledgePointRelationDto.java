package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.QuestionKnowledgepointRelation;

/**
 * Created by Administrator on 2018/2/12.
 * 题目知识类（包括题目类型）
 */
public class QuestionKnowledgePointRelationDto extends QuestionKnowledgepointRelation{
    private Integer questionType;

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
