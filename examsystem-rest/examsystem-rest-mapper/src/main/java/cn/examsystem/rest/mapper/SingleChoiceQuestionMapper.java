package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.SingleChoiceQuestion;
import cn.examsystem.rest.pojo.po.SingleChoiceQuestionExample;
import cn.examsystem.rest.pojo.po.SingleChoiceQuestionWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SingleChoiceQuestionMapper {
    int countByExample(SingleChoiceQuestionExample example);

    int deleteByExample(SingleChoiceQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SingleChoiceQuestionWithBLOBs record);

    int insertSelective(SingleChoiceQuestionWithBLOBs record);

    List<SingleChoiceQuestionWithBLOBs> selectByExampleWithBLOBs(SingleChoiceQuestionExample example);

    List<SingleChoiceQuestion> selectByExample(SingleChoiceQuestionExample example);

    SingleChoiceQuestionWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SingleChoiceQuestionWithBLOBs record, @Param("example") SingleChoiceQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") SingleChoiceQuestionWithBLOBs record, @Param("example") SingleChoiceQuestionExample example);

    int updateByExample(@Param("record") SingleChoiceQuestion record, @Param("example") SingleChoiceQuestionExample example);

    int updateByPrimaryKeySelective(SingleChoiceQuestionWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SingleChoiceQuestionWithBLOBs record);

    int updateByPrimaryKey(SingleChoiceQuestion record);
}