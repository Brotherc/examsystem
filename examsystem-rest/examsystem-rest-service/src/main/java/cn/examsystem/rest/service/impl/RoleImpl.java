package cn.examsystem.rest.service.impl;

import cn.examsystem.rest.mapper.RoleMapper;
import cn.examsystem.rest.pojo.po.Role;
import cn.examsystem.rest.pojo.po.RoleExample;
import cn.examsystem.rest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 * 角色业务层实现
 */
@Service
public class RoleImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> listRole() throws Exception {
        return roleMapper.selectByExample(new RoleExample());
    }
}
