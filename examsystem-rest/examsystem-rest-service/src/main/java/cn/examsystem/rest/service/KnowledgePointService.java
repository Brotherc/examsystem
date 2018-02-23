package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.KnowledgePoint;

/**
 * Created by Administrator on 2018/1/30.
 * 知识点业务层接口
 */
public interface KnowledgePointService {

    //根据父id查询知识点树
    public ResultInfo getKnowledgePoint(String parentId) throws Exception;

    //添加知识点
    public ResultInfo saveKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception;

    //更新知识点
    public ResultInfo updateKnowlesgePoint(String id,KnowledgePoint knowledgePoint) throws Exception;

    //删除知识点
    public ResultInfo deleteKnowledgePoint(String id,KnowledgePoint knowledgePoint) throws Exception;
}
