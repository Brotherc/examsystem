package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.po.TestPaper;
import cn.examsystem.rest.pojo.po.TestPaperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestPaperMapper {
    int countByExample(TestPaperExample example);

    int deleteByExample(TestPaperExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestPaper record);

    int insertSelective(TestPaper record);

    List<TestPaper> selectByExample(TestPaperExample example);

    TestPaper selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestPaper record, @Param("example") TestPaperExample example);

    int updateByExample(@Param("record") TestPaper record, @Param("example") TestPaperExample example);

    int updateByPrimaryKeySelective(TestPaper record);

    int updateByPrimaryKey(TestPaper record);
}