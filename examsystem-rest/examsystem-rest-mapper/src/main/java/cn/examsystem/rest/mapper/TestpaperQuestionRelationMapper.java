package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.TestpaperQuestionRelation;
import cn.examsystem.rest.pojo.po.TestpaperQuestionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestpaperQuestionRelationMapper {
    int countByExample(TestpaperQuestionRelationExample example);

    int deleteByExample(TestpaperQuestionRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestpaperQuestionRelation record);

    int insertSelective(TestpaperQuestionRelation record);

    List<TestpaperQuestionRelation> selectByExample(TestpaperQuestionRelationExample example);

    TestpaperQuestionRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestpaperQuestionRelation record, @Param("example") TestpaperQuestionRelationExample example);

    int updateByExample(@Param("record") TestpaperQuestionRelation record, @Param("example") TestpaperQuestionRelationExample example);

    int updateByPrimaryKeySelective(TestpaperQuestionRelation record);

    int updateByPrimaryKey(TestpaperQuestionRelation record);
}