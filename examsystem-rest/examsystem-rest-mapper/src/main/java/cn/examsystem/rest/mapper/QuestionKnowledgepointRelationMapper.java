package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.QuestionKnowledgepointRelation;
import cn.examsystem.rest.pojo.po.QuestionKnowledgepointRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionKnowledgepointRelationMapper {
    int countByExample(QuestionKnowledgepointRelationExample example);

    int deleteByExample(QuestionKnowledgepointRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(QuestionKnowledgepointRelation record);

    int insertSelective(QuestionKnowledgepointRelation record);

    List<QuestionKnowledgepointRelation> selectByExample(QuestionKnowledgepointRelationExample example);

    QuestionKnowledgepointRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") QuestionKnowledgepointRelation record, @Param("example") QuestionKnowledgepointRelationExample example);

    int updateByExample(@Param("record") QuestionKnowledgepointRelation record, @Param("example") QuestionKnowledgepointRelationExample example);

    int updateByPrimaryKeySelective(QuestionKnowledgepointRelation record);

    int updateByPrimaryKey(QuestionKnowledgepointRelation record);
}