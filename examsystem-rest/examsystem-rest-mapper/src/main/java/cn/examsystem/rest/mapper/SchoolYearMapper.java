package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.SchoolYear;
import cn.examsystem.rest.pojo.po.SchoolYearExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchoolYearMapper {
    int countByExample(SchoolYearExample example);

    int deleteByExample(SchoolYearExample example);

    int deleteByPrimaryKey(String id);

    int insert(SchoolYear record);

    int insertSelective(SchoolYear record);

    List<SchoolYear> selectByExample(SchoolYearExample example);

    SchoolYear selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SchoolYear record, @Param("example") SchoolYearExample example);

    int updateByExample(@Param("record") SchoolYear record, @Param("example") SchoolYearExample example);

    int updateByPrimaryKeySelective(SchoolYear record);

    int updateByPrimaryKey(SchoolYear record);
}