package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.ProgramQuestion;
import cn.examsystem.rest.pojo.po.ProgramQuestionExample;
import cn.examsystem.rest.pojo.po.ProgramQuestionWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProgramQuestionMapper {
    int countByExample(ProgramQuestionExample example);

    int deleteByExample(ProgramQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProgramQuestionWithBLOBs record);

    int insertSelective(ProgramQuestionWithBLOBs record);

    List<ProgramQuestionWithBLOBs> selectByExampleWithBLOBs(ProgramQuestionExample example);

    List<ProgramQuestion> selectByExample(ProgramQuestionExample example);

    ProgramQuestionWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProgramQuestionWithBLOBs record, @Param("example") ProgramQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") ProgramQuestionWithBLOBs record, @Param("example") ProgramQuestionExample example);

    int updateByExample(@Param("record") ProgramQuestion record, @Param("example") ProgramQuestionExample example);

    int updateByPrimaryKeySelective(ProgramQuestionWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProgramQuestionWithBLOBs record);

    int updateByPrimaryKey(ProgramQuestion record);
}