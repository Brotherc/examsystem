package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.po.TestPaper;
import cn.examsystem.rest.pojo.vo.TestPaperVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 试卷业务层接口
 */
public interface TestPaperService {

    //根据id查询试卷
    public TestPaperDto getTestPaper(String id) throws Exception;

    //根据条件查询试卷
    public List<TestPaper> listTestPaper(TestPaperVo testPaperVo) throws Exception;

    //添加试卷
    public ResultInfo saveTestPaper(TestPaperDto testPaperDto) throws Exception;

    //更新试卷
    public ResultInfo updateTestPaper(String id, TestPaperDto testPaperDto) throws Exception;

    //查询试卷题目信息
    public TestPaperDto listTestPaperQuestionByTestPaperId(String testPaperId) throws Exception;

    //移除试卷中的题目
    public ResultInfo removeQuestionFromTestPaper(String testPaperId,String[] ids) throws Exception;

    //修改试卷中题目顺序
    public ResultInfo updateTestPaperQuestionOrder(String testPaperId,String testPaperQuestionId,Integer order) throws Exception;


    //根据试卷id查询试卷信息及该试卷题目信息（正在考试学生）
    public TestPaperDto getTestPaperAndQuestionsByIdForLoginStudent(String id,String examStudentId) throws Exception;
}
