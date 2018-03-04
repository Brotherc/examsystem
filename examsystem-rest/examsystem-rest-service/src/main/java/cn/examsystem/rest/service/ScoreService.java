package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 成绩业务层接口
 */
public interface ScoreService {
    //查询学生成绩
    public List<ExamStudentRelationDto> listStudentScore(String examId,ExamStudentRelationVo examStudentRelationVo) throws Exception;

    //为某一门考试自动评分
    public ResultInfo autoGradeForExam(String examId) throws Exception;

    //查询考试学生的试卷答题信息
    public TestPaperDto getTestPaperByExamStudent(String examStudentId) throws Exception;
}
