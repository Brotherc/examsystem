package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.DictInfo;
import cn.examsystem.rest.pojo.po.DictInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictInfoMapper {
    int countByExample(DictInfoExample example);

    int deleteByExample(DictInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(DictInfo record);

    int insertSelective(DictInfo record);

    List<DictInfo> selectByExample(DictInfoExample example);

    DictInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DictInfo record, @Param("example") DictInfoExample example);

    int updateByExample(@Param("record") DictInfo record, @Param("example") DictInfoExample example);

    int updateByPrimaryKeySelective(DictInfo record);

    int updateByPrimaryKey(DictInfo record);
}