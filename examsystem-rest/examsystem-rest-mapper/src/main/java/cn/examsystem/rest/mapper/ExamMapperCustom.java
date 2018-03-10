package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;

import java.util.List;

public interface ExamMapperCustom {
    List<ExamDto> listExam(Exam exam);

    List<ExamStudentRelationDto> listExamStudent(ExamStudentRelationVo examStudentRelationVo);

    List<ExamStudentRelationDto> listInvigilationExamStudent(ExamStudentRelationVo examStudentRelationVo);
}