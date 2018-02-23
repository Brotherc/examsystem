package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.KnowledgePoint;
import cn.examsystem.rest.pojo.po.KnowledgePointExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KnowledgePointMapper {
    int countByExample(KnowledgePointExample example);

    int deleteByExample(KnowledgePointExample example);

    int deleteByPrimaryKey(String id);

    int insert(KnowledgePoint record);

    int insertSelective(KnowledgePoint record);

    List<KnowledgePoint> selectByExample(KnowledgePointExample example);

    KnowledgePoint selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") KnowledgePoint record, @Param("example") KnowledgePointExample example);

    int updateByExample(@Param("record") KnowledgePoint record, @Param("example") KnowledgePointExample example);

    int updateByPrimaryKeySelective(KnowledgePoint record);

    int updateByPrimaryKey(KnowledgePoint record);
}