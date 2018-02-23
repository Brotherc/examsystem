package cn.examsystem.rest.service;

import cn.examsystem.rest.pojo.po.Role;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 角色业务层接口
 */
public interface RoleService {

    //查询角色
    public List<Role>listRole() throws Exception;

}
