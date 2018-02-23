package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Department;
import cn.examsystem.rest.pojo.vo.DepartmentVo;
import cn.examsystem.rest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 系服务控制类
 */
@RestController
public class DepartmentController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/v1/department")
    public ResultInfo listDepartment(DepartmentVo departmentVo) throws Exception{
        System.out.println(departmentVo.getName());
        List<Department> departmentList = departmentService.listDepartment(departmentVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,departmentList);
        System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }

    @DeleteMapping(value = "/v1/department")
    public ResultInfo btchDeleteDepartment(@RequestBody String[] ids) throws Exception{

        for(String s:ids){
            System.out.println(s);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/department")
    public ResultInfo saveDepatment(@RequestBody Department department) throws Exception{
        return departmentService.saveDepartment(department);
    }

    @PutMapping("/v1/department/{id}")
    public ResultInfo updateDepatment(@PathVariable String id,@RequestBody Department department) throws Exception{
        System.out.println(department.getName());
        return departmentService.updateDapartment(id,department);
    }
}
