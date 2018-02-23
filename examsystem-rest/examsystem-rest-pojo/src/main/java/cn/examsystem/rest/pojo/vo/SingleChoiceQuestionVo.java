package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.SingleChoiceQuestion;

/**
 * Created by Administrator on 2018/2/11.
 */
public class SingleChoiceQuestionVo extends SingleChoiceQuestion{
    private String knowledgePointId;

    public String getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }
}
