package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.CourseMajorRelation;
import cn.examsystem.rest.pojo.po.CourseMajorRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMajorRelationMapper {
    int countByExample(CourseMajorRelationExample example);

    int deleteByExample(CourseMajorRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseMajorRelation record);

    int insertSelective(CourseMajorRelation record);

    List<CourseMajorRelation> selectByExample(CourseMajorRelationExample example);

    CourseMajorRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseMajorRelation record, @Param("example") CourseMajorRelationExample example);

    int updateByExample(@Param("record") CourseMajorRelation record, @Param("example") CourseMajorRelationExample example);

    int updateByPrimaryKeySelective(CourseMajorRelation record);

    int updateByPrimaryKey(CourseMajorRelation record);
}