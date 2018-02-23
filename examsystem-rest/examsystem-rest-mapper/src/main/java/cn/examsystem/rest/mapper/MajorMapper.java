package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.po.MajorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MajorMapper {
    int countByExample(MajorExample example);

    int deleteByExample(MajorExample example);

    int deleteByPrimaryKey(String id);

    int insert(Major record);

    int insertSelective(Major record);

    List<Major> selectByExample(MajorExample example);

    Major selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Major record, @Param("example") MajorExample example);

    int updateByExample(@Param("record") Major record, @Param("example") MajorExample example);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);
}