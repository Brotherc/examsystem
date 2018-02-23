package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.QuestionMatcherRelation;
import cn.examsystem.rest.pojo.po.QuestionMatcherRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionMatcherRelationMapper {
    int countByExample(QuestionMatcherRelationExample example);

    int deleteByExample(QuestionMatcherRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(QuestionMatcherRelation record);

    int insertSelective(QuestionMatcherRelation record);

    List<QuestionMatcherRelation> selectByExample(QuestionMatcherRelationExample example);

    QuestionMatcherRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") QuestionMatcherRelation record, @Param("example") QuestionMatcherRelationExample example);

    int updateByExample(@Param("record") QuestionMatcherRelation record, @Param("example") QuestionMatcherRelationExample example);

    int updateByPrimaryKeySelective(QuestionMatcherRelation record);

    int updateByPrimaryKey(QuestionMatcherRelation record);
}