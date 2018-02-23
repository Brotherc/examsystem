package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.Menu;

import java.util.List;

public interface ModuleMapperCustom {
    //根据角色id查询对应所能操作的资源（菜单）
    public List<Menu> listMenuByRoleIds(List<String> roleIds);
}