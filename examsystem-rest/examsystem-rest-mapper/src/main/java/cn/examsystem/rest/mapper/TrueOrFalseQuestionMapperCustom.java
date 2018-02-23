package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;

import java.util.List;

public interface TrueOrFalseQuestionMapperCustom {
    public List<TrueOrFalseQuestionDto> listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo);
}