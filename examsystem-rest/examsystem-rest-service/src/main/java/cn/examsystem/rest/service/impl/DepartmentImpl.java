package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.DepartmentMapper;
import cn.examsystem.rest.pojo.po.Department;
import cn.examsystem.rest.pojo.po.DepartmentExample;
import cn.examsystem.rest.pojo.vo.DepartmentVo;
import cn.examsystem.rest.service.DepartmentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 系业务层实现
 */
@Service
public class DepartmentImpl implements DepartmentService{

    @Value("${MESSAGE_DEPARTMENT_NAME_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_NAME_NOT_NULL;

    @Value("${MESSAGE_DEPARTMENT_NAME_NOT_REPEAT}")
    private String MESSAGE_DEPARTMENT_NAME_NOT_REPEAT;

    @Value("${MESSAGE_DEPARTMENT_ID_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_ID_NOT_NULL;

    @Value("${MESSAGE_DEPARTMENT_NOT_EXIST}")
    private String MESSAGE_DEPARTMENT_NOT_EXIST;


    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;

    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> listDepartment(DepartmentVo departmentVo) throws Exception {
        DepartmentExample departmentExample=new DepartmentExample();
        DepartmentExample.Criteria departmentCriteria = departmentExample.createCriteria();

        //查询条件-名字-不为空
        if(departmentVo!=null){
            String name=departmentVo.getName();
            if(!StringUtils.isBlank(name)){
                departmentCriteria.andNameEqualTo(name);
            }
        }
        return departmentMapper.selectByExample(departmentExample);
    }

    @Override
    public ResultInfo saveDepartment(Department department) throws Exception {

        //名字不能为空
        String departmentName = department.getName();
        if(StringUtils.isBlank(departmentName)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_NULL,null);
        }
        //名字预处理
        departmentName=departmentName.trim();

        //名字不能重复
        DepartmentExample departmentExample=new DepartmentExample();
        DepartmentExample.Criteria departmentCriteria = departmentExample.createCriteria();
        departmentCriteria.andNameEqualTo(departmentName);
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
        if(!CollectionUtils.isEmpty(departmentList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_REPEAT,null);
        }

        //补全id
        String departmentId = UUIDBuild.getUUID();
        department.setId(departmentId);
        department.setName(departmentName);

        //补全创建时间，更新时间
        department.setCreatedTime(new Date());
        department.setUpdatedTime(new Date());

        //添加系
        departmentMapper.insert(department);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateDepartment(String id, Department department) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);

        //名字不允许为空
        String departmentName=department.getName();
        if(StringUtils.isBlank(departmentName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_NULL,null);

        //id对应系必须存在
        Department departmentDb = departmentMapper.selectByPrimaryKey(id);

        if(departmentDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);

        departmentName=departmentName.trim();
        //若对系信息进行了修改
        if(!StringUtils.equals(departmentName,departmentDb.getName())){
            //则不允许与已存在的系信息重复
            DepartmentExample departmentExample=new DepartmentExample();
            DepartmentExample.Criteria departmentCriteria = departmentExample.createCriteria();
            departmentCriteria.andNameEqualTo(departmentName);
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if(!CollectionUtils.isEmpty(departmentList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_REPEAT,null);
            }

            //更新系
            departmentDb.setName(departmentName);
            departmentDb.setUpdatedTime(new Date());
            departmentMapper.updateByPrimaryKey(departmentDb);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
