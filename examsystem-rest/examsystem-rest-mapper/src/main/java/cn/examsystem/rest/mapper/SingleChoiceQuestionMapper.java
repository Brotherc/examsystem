package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.SingleChoiceQuestion;
import cn.examsystem.rest.pojo.po.SingleChoiceQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SingleChoiceQuestionMapper {
    int countByExample(SingleChoiceQuestionExample example);

    int deleteByExample(SingleChoiceQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SingleChoiceQuestion record);

    int insertSelective(SingleChoiceQuestion record);

    List<SingleChoiceQuestion> selectByExample(SingleChoiceQuestionExample example);

    SingleChoiceQuestion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SingleChoiceQuestion record, @Param("example") SingleChoiceQuestionExample example);

    int updateByExample(@Param("record") SingleChoiceQuestion record, @Param("example") SingleChoiceQuestionExample example);

    int updateByPrimaryKeySelective(SingleChoiceQuestion record);

    int updateByPrimaryKey(SingleChoiceQuestion record);
}