package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.po.Exam;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 考试业务层接口
 */
public interface ExamService {

    //根据条件查询考试
    public List<ExamDto>listExam(Exam exam) throws Exception;

    //添加考试
    public ResultInfo saveExam(Exam exam) throws Exception;

    //更新考试
    public ResultInfo updateExam(String id, Exam exam) throws Exception;
}
