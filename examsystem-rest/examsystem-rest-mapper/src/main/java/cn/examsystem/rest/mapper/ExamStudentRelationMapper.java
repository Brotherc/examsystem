package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.ExamStudentRelation;
import cn.examsystem.rest.pojo.po.ExamStudentRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamStudentRelationMapper {
    int countByExample(ExamStudentRelationExample example);

    int deleteByExample(ExamStudentRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExamStudentRelation record);

    int insertSelective(ExamStudentRelation record);

    List<ExamStudentRelation> selectByExample(ExamStudentRelationExample example);

    ExamStudentRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ExamStudentRelation record, @Param("example") ExamStudentRelationExample example);

    int updateByExample(@Param("record") ExamStudentRelation record, @Param("example") ExamStudentRelationExample example);

    int updateByPrimaryKeySelective(ExamStudentRelation record);

    int updateByPrimaryKey(ExamStudentRelation record);
}