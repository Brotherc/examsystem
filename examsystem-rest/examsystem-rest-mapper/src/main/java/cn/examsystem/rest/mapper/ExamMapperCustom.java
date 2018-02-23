package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.po.Exam;

import java.util.List;

public interface ExamMapperCustom {
    List<ExamDto> listExam(Exam exam);
}