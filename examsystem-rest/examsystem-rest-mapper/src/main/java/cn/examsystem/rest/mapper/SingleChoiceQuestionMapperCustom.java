package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.SingleChoiceQuestionDto;
import cn.examsystem.rest.pojo.vo.SingleChoiceQuestionVo;

import java.util.List;

public interface SingleChoiceQuestionMapperCustom {
    public List<SingleChoiceQuestionDto> listSingleChoiceQuestion(SingleChoiceQuestionVo singleChoiceQuestionVo);
}