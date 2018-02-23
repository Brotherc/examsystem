package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.Grade;
import cn.examsystem.rest.pojo.po.GradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GradeMapper {
    int countByExample(GradeExample example);

    int deleteByExample(GradeExample example);

    int deleteByPrimaryKey(String id);

    int insert(Grade record);

    int insertSelective(Grade record);

    List<Grade> selectByExample(GradeExample example);

    Grade selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Grade record, @Param("example") GradeExample example);

    int updateByExample(@Param("record") Grade record, @Param("example") GradeExample example);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
}