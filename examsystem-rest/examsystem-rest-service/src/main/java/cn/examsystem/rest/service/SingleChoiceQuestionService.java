package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SingleChoiceQuestionDto;
import cn.examsystem.rest.pojo.vo.SingleChoiceQuestionVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 单选题业务层接口
 */
public interface SingleChoiceQuestionService {

    //根据id查询单选题
    public SingleChoiceQuestionDto getSingleChoiceQuestion(String id) throws Exception;

    //根据条件查询单选题
    public List<SingleChoiceQuestionDto>listSingleChoiceQuestion(SingleChoiceQuestionVo singleChoiceQuestionVo) throws Exception;

    //添加单选题
    public ResultInfo saveSingleChoiceQuestion(SingleChoiceQuestionDto SingleChoiceQuestionDto) throws Exception;

    //更新单选题
    public ResultInfo updateSingleChoiceQuestion(String id, SingleChoiceQuestionDto singleChoiceQuestionDto) throws Exception;
}
