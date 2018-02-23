package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 填空题业务层接口
 */
public interface FillInBlankQuestionService {

    //根据id查询填空题
    public FillInBlankQuestionDto getFillInBlankQuestion(String id) throws Exception;

    //根据条件查询填空题
    public List<FillInBlankQuestionDto>listFillInBlankQuestion(FillInBlankQuestionVo fillInBlankQuestionVo) throws Exception;

    //添加填空题
    public ResultInfo saveFillInBlankQuestion(FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception;

    //更新填空题
    public ResultInfo updateFillInBlankQuestion(String id, FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception;
}
