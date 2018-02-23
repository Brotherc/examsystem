package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.CourseTeacherRelation;
import cn.examsystem.rest.pojo.po.CourseTeacherRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseTeacherRelationMapper {
    int countByExample(CourseTeacherRelationExample example);

    int deleteByExample(CourseTeacherRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseTeacherRelation record);

    int insertSelective(CourseTeacherRelation record);

    List<CourseTeacherRelation> selectByExample(CourseTeacherRelationExample example);

    CourseTeacherRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseTeacherRelation record, @Param("example") CourseTeacherRelationExample example);

    int updateByExample(@Param("record") CourseTeacherRelation record, @Param("example") CourseTeacherRelationExample example);

    int updateByPrimaryKeySelective(CourseTeacherRelation record);

    int updateByPrimaryKey(CourseTeacherRelation record);
}