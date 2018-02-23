package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.DepartmentMapper;
import cn.examsystem.rest.mapper.MajorMapper;
import cn.examsystem.rest.mapper.MajorMapperCustom;
import cn.examsystem.rest.pojo.dto.MajorDto;
import cn.examsystem.rest.pojo.po.Department;
import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.po.MajorExample;
import cn.examsystem.rest.pojo.vo.MajorVo;
import cn.examsystem.rest.service.MajorService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 专业业务层实现
 */
@Service
public class MajorImpl implements MajorService {

    @Value("${MESSAGE_MAJOR_NAME_NOT_NULL}")
    private String MESSAGE_MAJOR_NAME_NOT_NULL;
    @Value("${MESSAGE_DEPARTMENT_ID_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_ID_NOT_NULL;
    @Value("${MESSAGE_DEPARTMENT_NOT_EXIST}")
    private String MESSAGE_DEPARTMENT_NOT_EXIST;
    @Value("${MESSAGE_MAJOR_NAME_NOT_REPEAT}")
    private String MESSAGE_MAJOR_NAME_NOT_REPEAT;
    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_MAJOR_ID_NOT_NULL}")
    private String MESSAGE_MAJOR_ID_NOT_NULL;
    @Value("${MESSAGE_MAJOR_NOT_EXIST}")
    private String MESSAGE_MAJOR_NOT_EXIST;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Autowired
    private MajorMapperCustom majorMapperCustom;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<MajorDto> listMajor(MajorVo majorVo) throws Exception {
        return majorMapperCustom.listMajor(majorVo);
    }

    @Override
    public ResultInfo saveMajor(Major major) throws Exception {

        //名字不能为空
        String majorName = major.getName();
        if(StringUtils.isBlank(majorName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NAME_NOT_NULL,null);

        //名字预处理
        majorName=majorName.trim();

        //系id不能为空
        String departmentId=major.getDepartmentId();
        if(StringUtils.isBlank(departmentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);

        //添加的专业所属的系必须存在
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        if(department==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);

        //名字不能重复
        MajorExample majorExample=new MajorExample();
        MajorExample.Criteria majorCriteria = majorExample.createCriteria();
        majorCriteria.andNameEqualTo(majorName);
        List<Major> majorList = majorMapper.selectByExample(majorExample);
        if(!CollectionUtils.isEmpty(majorList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NAME_NOT_REPEAT,null);
        }

        //补全id
        String majorId = UUIDBuild.getUUID();
        major.setId(majorId);
        major.setName(majorName);

        //补全创建时间，更新时间
        major.setCreatedTime(new Date());
        major.setUpdatedTime(new Date());

        //添加专业
        majorMapper.insert(major);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateMajor(String id, Major major) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_ID_NOT_NULL,null);

        //名字不允许为空
        String majorName=major.getName();
        if(StringUtils.isBlank(majorName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NAME_NOT_NULL,null);

        //系id不能为空
        String departmentId=major.getDepartmentId();
        if(StringUtils.isBlank(departmentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);

        //id对应专业必须存在
        Major majorDb = majorMapper.selectByPrimaryKey(id);

        if(majorDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NOT_EXIST,null);

        //修改的专业所属的系必须存在
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        if(department==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);

        majorName=majorName.trim();
        //若对专业名字进行了修改
        if(!StringUtils.equals(majorName,majorDb.getName())){
            //则不允许与已存在的专业信息重复
            MajorExample majorExample=new MajorExample();
            MajorExample.Criteria majorCriteria = majorExample.createCriteria();
            majorCriteria.andNameEqualTo(majorName);
            List<Major> majorList = majorMapper.selectByExample(majorExample);
            if(!CollectionUtils.isEmpty(majorList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NAME_NOT_REPEAT,null);
            }
            //设置名字
            majorDb.setName(majorName);
        }

        //更新专业
        majorDb.setDepartmentId(departmentId);
        majorDb.setUpdatedTime(new Date());
        majorMapper.updateByPrimaryKey(majorDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
