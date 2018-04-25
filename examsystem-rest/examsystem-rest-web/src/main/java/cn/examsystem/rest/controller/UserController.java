package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/30.
 * 用户服务控制类
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/v1/user/details/{id}")
    public ResultInfo updateSysuser(@PathVariable String id,@RequestBody SysuserDto sysuserDto) throws Exception{
        System.out.println("修改user");
        return userService.updateUser(id,sysuserDto);
    }
}
