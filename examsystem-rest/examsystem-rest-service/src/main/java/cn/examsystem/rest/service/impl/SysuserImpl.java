package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.SysuserVo;
import cn.examsystem.rest.service.SysuserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 用户业务层实现
 */
@Service
public class SysuserImpl implements SysuserService {

    @Value("${ROLE_TEACHER_ID}")
    private String ROLE_TEACHER_ID;
    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;

    @Value("${MESSAGE_SYSUSER_ID_NOT_NULL}")
    private String MESSAGE_SYSUSER_ID_NOT_NULL;
    @Value("${MESSAGE_SYSUSER_NAME_NOT_NULL}")
    private String MESSAGE_SYSUSER_NAME_NOT_NULL;
    @Value("${MESSAGE_SYSUSER_PWD_NOT_NULL}")
    private String MESSAGE_SYSUSER_PWD_NOT_NULL;
    @Value("${MESSAGE_ROLE_ID_NOT_NULL}")
    private String MESSAGE_ROLE_ID_NOT_NULL;
    @Value("${MESSAGE_DEPARTMENT_ID_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_ID_NOT_NULL;
    @Value("${MESSAGE_ROLE_NOT_EXIST}")
    private String MESSAGE_ROLE_NOT_EXIST;
    @Value("${MESSAGE_DEPARTMENT_NOT_EXIST}")
    private String MESSAGE_DEPARTMENT_NOT_EXIST;
    @Value("${MESSAGE_SYSUSER_SYSUSER_ID_NOT_REPEAT}")
    private String MESSAGE_SYSUSER_SYSUSER_ID_NOT_REPEAT;
    @Value("${MESSAGE_SYSUSER_NOT_EXIST}")
    private String MESSAGE_SYSUSER_NOT_EXIST;
    @Value("${MESSAGE_SYSUSER_STATUS_NOT_NULL}")
    private String MESSAGE_SYSUSER_STATUS_NOT_NULL;


    @Autowired
    private SysuserMapperCustom sysuserMapperCustom;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public List<SysuserDto> listSysuser(SysuserVo sysuserVo) throws Exception {
        System.out.println(sysuserVo.getStatus());
        return sysuserMapperCustom.listSysuser(sysuserVo);
    }

    @Override
    public ResultInfo saveSysuser(SysuserDto sysuserDto) throws Exception {

        //账号不能为空
        String sysuserDtoId = sysuserDto.getSysuserId();
        if(StringUtils.isBlank(sysuserDtoId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_ID_NOT_NULL,null);

        //账号预处理
        sysuserDtoId=sysuserDtoId.trim();

        //名字不能为空
        String sysuserDtoName = sysuserDto.getName();
        if(StringUtils.isBlank(sysuserDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_NAME_NOT_NULL,null);

        //名字预处理
        sysuserDtoName=sysuserDtoName.trim();

        //密码不能为空
        String sysuserDtoPassword = sysuserDto.getPassword();
        if(StringUtils.isBlank(sysuserDtoPassword))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_PWD_NOT_NULL,null);

        //密码预处理
        sysuserDtoPassword=sysuserDtoPassword.trim();

        //角色id不能为空
        String sysuserDtoRolesId=sysuserDto.getRolesId();
        if(StringUtils.isBlank(sysuserDtoRolesId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_ROLE_ID_NOT_NULL,null);

        String[] rolesIdArr=null;

        //如果角色中存在教师，系id不能为空
        String sysuserDtoDepartmentId=sysuserDto.getDepartmentId();
        if(sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            if(StringUtils.isBlank(sysuserDtoDepartmentId))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);
        }

        //添加的用户所属的角色必须存在
        rolesIdArr=stirngIdsToArrIds(sysuserDtoRolesId);
        for(String roleId:rolesIdArr){
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if(role==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_ROLE_NOT_EXIST,null);
        }

        //如果添加的用户是教师，所属的系必须存在
        if(sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            Department department = departmentMapper.selectByPrimaryKey(sysuserDtoDepartmentId);
            if(department==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);
        }

        //账号不能重复
        SysuserExample sysuserExample=new SysuserExample();
        SysuserExample.Criteria sysuserCriteria = sysuserExample.createCriteria();
        sysuserCriteria.andSysuserIdEqualTo(sysuserDtoId);
        List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
        if(!CollectionUtils.isEmpty(sysuserList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_SYSUSER_ID_NOT_REPEAT,null);
        }

        //补全id
        String sysuserId = UUIDBuild.getUUID();
        sysuserDto.setId(sysuserId);
        sysuserDto.setSysuserId(sysuserDtoId);
        sysuserDto.setName(sysuserDtoName);
        //密码加密
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        sysuserDtoPassword=encoder.encode(sysuserDtoPassword);
        sysuserDto.setPassword(sysuserDtoPassword);
        //设置状态
        sysuserDto.setStatus(1);

        //如果添加的不是教师，则系为空
        if(!sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            sysuserDto.setDepartmentId(null);
        }

        //补全创建时间，更新时间
        sysuserDto.setCreatedTime(new Date());
        sysuserDto.setUpdatedTime(new Date());

        //添加用户
        sysuserMapper.insert(sysuserDto);

        //添加用户角色关系
        saveSysuserRoleRelation(sysuserId,rolesIdArr);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);

    }

    @Override
    public ResultInfo updateSysuser(String id,SysuserDto sysuserDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_ID_NOT_NULL,null);

        //账号不能为空
        String sysuserDtoId = sysuserDto.getSysuserId();
        if(StringUtils.isBlank(sysuserDtoId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_ID_NOT_NULL,null);

        //账号预处理
        sysuserDtoId=sysuserDtoId.trim();

        //名字不能为空
        String sysuserDtoName = sysuserDto.getName();
        if(StringUtils.isBlank(sysuserDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_NAME_NOT_NULL,null);

        //名字预处理
        sysuserDtoName=sysuserDtoName.trim();

        //名字不能为空
        Integer sysuserDtoStatus = sysuserDto.getStatus();
        if(sysuserDtoStatus==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_STATUS_NOT_NULL,null);


        //角色id不能为空
        String sysuserDtoRolesId=sysuserDto.getRolesId();
        if(StringUtils.isBlank(sysuserDtoRolesId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_ROLE_ID_NOT_NULL,null);

        String[] rolesIdArr=null;

        //如果角色中存在教师，系id不能为空
        String sysuserDtoDepartmentId=sysuserDto.getDepartmentId();
        if(sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            if(StringUtils.isBlank(sysuserDtoDepartmentId))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);
        }

        //id对应用户必须存在
        Sysuser sysuserDb = sysuserMapper.selectByPrimaryKey(id);

        if(sysuserDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_NOT_EXIST,null);

        //修改的用户所属的角色必须存在
        rolesIdArr=stirngIdsToArrIds(sysuserDtoRolesId);
        for(String roleId:rolesIdArr){
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if(role==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_ROLE_NOT_EXIST,null);
        }

        //如果修改的用户是教师，所属的系必须存在
        if(sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            Department department = departmentMapper.selectByPrimaryKey(sysuserDtoDepartmentId);
            if(department==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);
        }

        //如果修改了账号，账号不能重复
        String sysuserDbId = sysuserDb.getSysuserId();
        if(!StringUtils.equals(sysuserDbId,sysuserDtoId)){
            SysuserExample sysuserExample=new SysuserExample();
            SysuserExample.Criteria sysuserCriteria = sysuserExample.createCriteria();
            sysuserCriteria.andSysuserIdEqualTo(sysuserDtoId);
            List<Sysuser> sysuserList = sysuserMapper.selectByExample(sysuserExample);
            if(!CollectionUtils.isEmpty(sysuserList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_SYSUSER_ID_NOT_REPEAT,null);
            }
            sysuserDb.setSysuserId(sysuserDtoId);
        }

        sysuserDb.setName(sysuserDtoName);
        //如果密码不为空，则修改密码
        String sysuserDtoPassword = sysuserDto.getPassword();
        if(!StringUtils.isBlank(sysuserDtoPassword)){
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            sysuserDtoPassword=encoder.encode(sysuserDtoPassword.trim());
            sysuserDb.setPassword(sysuserDtoPassword);
        }

        //设置状态
        sysuserDb.setStatus(sysuserDtoStatus);

        //如果修改的是教师，则修改系
        if(sysuserDtoRolesId.contains(ROLE_TEACHER_ID)){
            sysuserDb.setDepartmentId(sysuserDtoDepartmentId);
        }
        else{
            sysuserDb.setDepartmentId(null);
        }

        //修改更新时间
        sysuserDb.setUpdatedTime(new Date());

        //修改用户
        sysuserMapper.updateByPrimaryKey(sysuserDb);


        //删除原来用户角色关系
        UserRoleRelationExample userRoleRelationExample=new UserRoleRelationExample();
        UserRoleRelationExample.Criteria userRoleRelationCriteria = userRoleRelationExample.createCriteria();
        userRoleRelationCriteria.andUserIdEqualTo(id);
        userRoleRelationMapper.deleteByExample(userRoleRelationExample);

        //添加用户角色关系
        saveSysuserRoleRelation(id,rolesIdArr);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    /**
     * 将id字符串转换成数组
     * @param ids 要转成数组的id字符串
     * @return
     */
    private String[] stirngIdsToArrIds(String ids){
        if(ids.contains(","))//超过1个id
            return ids.split(",");
        else
            return new String[]{ids};
    }

    /**
     * 添加用户角色关系
     * @param sysuserId 用户id
     * @param rolesIdArr 角色id数组
     */
    private void saveSysuserRoleRelation(String sysuserId,String[] rolesIdArr){
        for(String roleId:rolesIdArr){
            UserRoleRelation userRoleRelation=new UserRoleRelation();
            String userRoleRelationId = UUIDBuild.getUUID();
            userRoleRelation.setId(userRoleRelationId);
            userRoleRelation.setUserId(sysuserId);
            userRoleRelation.setRoleId(roleId);
            userRoleRelation.setCreatedTime(new Date());
            userRoleRelation.setUpdatedTime(new Date());

            //添加数据
            userRoleRelationMapper.insert(userRoleRelation);
        }
    }
}
