package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.FillInBlankQuestion;
import cn.examsystem.rest.pojo.po.FillInBlankQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FillInBlankQuestionMapper {
    int countByExample(FillInBlankQuestionExample example);

    int deleteByExample(FillInBlankQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(FillInBlankQuestion record);

    int insertSelective(FillInBlankQuestion record);

    List<FillInBlankQuestion> selectByExampleWithBLOBs(FillInBlankQuestionExample example);

    List<FillInBlankQuestion> selectByExample(FillInBlankQuestionExample example);

    FillInBlankQuestion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FillInBlankQuestion record, @Param("example") FillInBlankQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") FillInBlankQuestion record, @Param("example") FillInBlankQuestionExample example);

    int updateByExample(@Param("record") FillInBlankQuestion record, @Param("example") FillInBlankQuestionExample example);

    int updateByPrimaryKeySelective(FillInBlankQuestion record);

    int updateByPrimaryKeyWithBLOBs(FillInBlankQuestion record);

    int updateByPrimaryKey(FillInBlankQuestion record);
}