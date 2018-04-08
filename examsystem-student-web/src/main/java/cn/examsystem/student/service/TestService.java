package cn.examsystem.student.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TestPaperDto;

/**
 * Created by Administrator on 2018/1/30.
 * 考试业务层接口
 */
public interface TestService {
    //保存学生试卷单选题答题信息至缓存中
    public ResultInfo saveSingleChoiceQuestionAnswer(String examStudentId,TestPaperDto testPaperDto ) throws Exception;

    //保存学生试卷判断题答题信息至缓存中
    public ResultInfo saveTrueOrFalseQuestionAnswer(String examStudentId, TestPaperDto testPaperDto ) throws Exception;

    //保存学生试卷填空题答题信息至缓存中
    public ResultInfo saveFillInBlankQuestionAnswer(String examStudentId, TestPaperDto testPaperDto ) throws Exception;

    //保存学生试卷程序题答题信息至缓存中
    public ResultInfo saveProgramQuestionAnswer(String examStudentId, TestPaperDto testPaperDto ) throws Exception;

}
