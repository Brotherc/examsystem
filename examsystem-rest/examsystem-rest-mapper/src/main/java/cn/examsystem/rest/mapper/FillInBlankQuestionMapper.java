package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.FillInBlankQuestion;
import cn.examsystem.rest.pojo.po.FillInBlankQuestionExample;
import cn.examsystem.rest.pojo.po.FillInBlankQuestionWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FillInBlankQuestionMapper {
    int countByExample(FillInBlankQuestionExample example);

    int deleteByExample(FillInBlankQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(FillInBlankQuestionWithBLOBs record);

    int insertSelective(FillInBlankQuestionWithBLOBs record);

    List<FillInBlankQuestionWithBLOBs> selectByExampleWithBLOBs(FillInBlankQuestionExample example);

    List<FillInBlankQuestion> selectByExample(FillInBlankQuestionExample example);

    FillInBlankQuestionWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FillInBlankQuestionWithBLOBs record, @Param("example") FillInBlankQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") FillInBlankQuestionWithBLOBs record, @Param("example") FillInBlankQuestionExample example);

    int updateByExample(@Param("record") FillInBlankQuestion record, @Param("example") FillInBlankQuestionExample example);

    int updateByPrimaryKeySelective(FillInBlankQuestionWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FillInBlankQuestionWithBLOBs record);

    int updateByPrimaryKey(FillInBlankQuestion record);
}