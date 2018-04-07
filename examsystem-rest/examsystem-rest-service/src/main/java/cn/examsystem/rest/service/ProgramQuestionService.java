package cn.examsystem.rest.service;

import cn.examsystem.rest.pojo.dto.ProgramQuestionDto;
import cn.examsystem.rest.pojo.po.ProgramQuestion;
import cn.examsystem.rest.pojo.po.ProgramQuestionWithBLOBs;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 单选题业务层接口
 */
public interface ProgramQuestionService {

    //根据id查询程序题
    public ProgramQuestionDto getProgramQuestion(String id) throws Exception;

    //根据条件查询判断题
    public List<ProgramQuestionWithBLOBs>listProgramQuestion(ProgramQuestion programQuestion) throws Exception;

}
