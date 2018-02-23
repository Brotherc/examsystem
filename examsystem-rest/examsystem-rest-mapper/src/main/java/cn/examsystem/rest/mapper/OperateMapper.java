package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.Operate;
import cn.examsystem.rest.pojo.po.OperateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateMapper {
    int countByExample(OperateExample example);

    int deleteByExample(OperateExample example);

    int deleteByPrimaryKey(String id);

    int insert(Operate record);

    int insertSelective(Operate record);

    List<Operate> selectByExample(OperateExample example);

    Operate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Operate record, @Param("example") OperateExample example);

    int updateByExample(@Param("record") Operate record, @Param("example") OperateExample example);

    int updateByPrimaryKeySelective(Operate record);

    int updateByPrimaryKey(Operate record);
}