package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.TrueOrFalseQuestion;
import cn.examsystem.rest.pojo.po.TrueOrFalseQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrueOrFalseQuestionMapper {
    int countByExample(TrueOrFalseQuestionExample example);

    int deleteByExample(TrueOrFalseQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TrueOrFalseQuestion record);

    int insertSelective(TrueOrFalseQuestion record);

    List<TrueOrFalseQuestion> selectByExample(TrueOrFalseQuestionExample example);

    TrueOrFalseQuestion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TrueOrFalseQuestion record, @Param("example") TrueOrFalseQuestionExample example);

    int updateByExample(@Param("record") TrueOrFalseQuestion record, @Param("example") TrueOrFalseQuestionExample example);

    int updateByPrimaryKeySelective(TrueOrFalseQuestion record);

    int updateByPrimaryKey(TrueOrFalseQuestion record);
}