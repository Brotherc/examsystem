package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.vo.SysuserVo;

import java.util.List;

public interface SysuserMapperCustom {
    public List<SysuserDto> listSysuser(SysuserVo sysuserVo);
}