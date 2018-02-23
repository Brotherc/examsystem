package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.vo.SysuserVo;
import cn.examsystem.rest.service.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 用户服务控制类
 */
@RestController
public class SysuserController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private SysuserService sysuserService;

    @GetMapping("/v1/sysuser")
    public ResultInfo listSysuser(SysuserVo sysuserVo) throws Exception{
        List<SysuserDto> sysuserList = sysuserService.listSysuser(sysuserVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,sysuserList);
        System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }

    @DeleteMapping(value = "/v1/sysuser")
    public ResultInfo btchDeleteDepartment(@RequestBody String[] ids) throws Exception{
/*
        for(String s:ids){
            System.out.println(s);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);*/
return null;
    }

    @PostMapping("/v1/sysuser")
    public ResultInfo saveSysuser(@RequestBody SysuserDto sysuserDto) throws Exception{
        return sysuserService.saveSysuser(sysuserDto);
    }

    @PutMapping("/v1/sysuser/{id}")
    public ResultInfo updateSysuser(@PathVariable String id,@RequestBody SysuserDto sysuserDto) throws Exception{
        return sysuserService.updateSysuser(id,sysuserDto);
    }
}
