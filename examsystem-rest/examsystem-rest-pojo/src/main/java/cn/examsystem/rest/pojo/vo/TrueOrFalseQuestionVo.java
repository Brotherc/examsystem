package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.TrueOrFalseQuestion;

/**
 * Created by Administrator on 2018/2/11.
 */
public class TrueOrFalseQuestionVo extends TrueOrFalseQuestion {
    private String knowledgePointId;

    public String getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }
}
