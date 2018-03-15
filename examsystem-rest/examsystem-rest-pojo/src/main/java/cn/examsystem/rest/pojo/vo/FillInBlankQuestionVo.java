package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.FillInBlankQuestionWithBLOBs;

/**
 * Created by Administrator on 2018/2/11.
 */
public class FillInBlankQuestionVo extends FillInBlankQuestionWithBLOBs {
    private String knowledgePointId;

    public String getKnowledgePointId() {
        return knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }
}
