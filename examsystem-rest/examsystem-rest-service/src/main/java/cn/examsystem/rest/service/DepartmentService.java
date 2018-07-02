package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Department;
import cn.examsystem.rest.pojo.vo.DepartmentVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 系业务层接口
 */
public interface DepartmentService {

    //根据条件查询系
    public List<Department>listDepartment(DepartmentVo departmentVo) throws Exception;

    //添加系
    public ResultInfo saveDepartment(Department department) throws Exception;

    //更新系
    public ResultInfo updateDepartment(String id,Department department) throws Exception;
}
