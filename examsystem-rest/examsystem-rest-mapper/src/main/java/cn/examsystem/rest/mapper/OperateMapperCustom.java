package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.OperateDto;

import java.util.List;

public interface OperateMapperCustom {
    //查询所有操作（包括该操作所需要的权限（角色））
    public List<OperateDto> listOperateIncludeRole();
}