package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.TrueOrFalseQuestion;
import cn.examsystem.rest.pojo.po.TrueOrFalseQuestionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrueOrFalseQuestionMapper {
    int countByExample(TrueOrFalseQuestionExample example);

    int deleteByExample(TrueOrFalseQuestionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TrueOrFalseQuestion record);

    int insertSelective(TrueOrFalseQuestion record);

    List<TrueOrFalseQuestion> selectByExampleWithBLOBs(TrueOrFalseQuestionExample example);

    List<TrueOrFalseQuestion> selectByExample(TrueOrFalseQuestionExample example);

    TrueOrFalseQuestion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TrueOrFalseQuestion record, @Param("example") TrueOrFalseQuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") TrueOrFalseQuestion record, @Param("example") TrueOrFalseQuestionExample example);

    int updateByExample(@Param("record") TrueOrFalseQuestion record, @Param("example") TrueOrFalseQuestionExample example);

    int updateByPrimaryKeySelective(TrueOrFalseQuestion record);

    int updateByPrimaryKeyWithBLOBs(TrueOrFalseQuestion record);

    int updateByPrimaryKey(TrueOrFalseQuestion record);
}