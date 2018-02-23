package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Role;
import cn.examsystem.rest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 用户服务控制类
 */
@RestController
public class RoleController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Autowired
    private RoleService roleService;

    @GetMapping("/v1/role")
    public ResultInfo listRole() throws Exception{

        List<Role> roleList = roleService.listRole();
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,roleList);
        return resultInfo;
    }
}
