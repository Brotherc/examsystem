package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 判断题业务层接口
 */
public interface TrueOrFalseQuestionService {

    //根据id查询判断题
    public TrueOrFalseQuestionDto getTrueOrFalseQuestion(String id) throws Exception;

    //根据条件查询判断题
    public List<TrueOrFalseQuestionDto>listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo) throws Exception;

    //添加判断题
    public ResultInfo saveTrueOrFalseQuestion(TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception;

    //更新判断题
    public ResultInfo updateTrueOrFalseQuestion(String id, TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception;
}
