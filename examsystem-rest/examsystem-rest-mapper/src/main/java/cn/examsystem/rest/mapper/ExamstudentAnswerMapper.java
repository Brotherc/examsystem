package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.ExamstudentAnswer;
import cn.examsystem.rest.pojo.po.ExamstudentAnswerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamstudentAnswerMapper {
    int countByExample(ExamstudentAnswerExample example);

    int deleteByExample(ExamstudentAnswerExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExamstudentAnswer record);

    int insertSelective(ExamstudentAnswer record);

    List<ExamstudentAnswer> selectByExampleWithBLOBs(ExamstudentAnswerExample example);

    List<ExamstudentAnswer> selectByExample(ExamstudentAnswerExample example);

    ExamstudentAnswer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ExamstudentAnswer record, @Param("example") ExamstudentAnswerExample example);

    int updateByExampleWithBLOBs(@Param("record") ExamstudentAnswer record, @Param("example") ExamstudentAnswerExample example);

    int updateByExample(@Param("record") ExamstudentAnswer record, @Param("example") ExamstudentAnswerExample example);

    int updateByPrimaryKeySelective(ExamstudentAnswer record);

    int updateByPrimaryKeyWithBLOBs(ExamstudentAnswer record);

    int updateByPrimaryKey(ExamstudentAnswer record);
}