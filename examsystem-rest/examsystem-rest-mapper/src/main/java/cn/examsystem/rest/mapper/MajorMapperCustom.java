package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.MajorDto;
import cn.examsystem.rest.pojo.vo.MajorVo;

import java.util.List;

public interface MajorMapperCustom {
    public List<MajorDto> listMajor(MajorVo majorVo) throws Exception;
}