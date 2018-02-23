package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;

import java.util.List;

public interface FillInBlankQuestionMapperCustom {
    public List<FillInBlankQuestionDto> listFillInBlankQuestion(FillInBlankQuestionVo fillInBlankQuestionVo);
}