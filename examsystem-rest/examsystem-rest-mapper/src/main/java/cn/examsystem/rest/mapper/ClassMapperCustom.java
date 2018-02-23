package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.vo.ClassVo;

import java.util.List;

public interface ClassMapperCustom {
    public List<ClassDto> listClass(ClassVo classVo);
}