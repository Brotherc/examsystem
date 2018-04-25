package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.mapper.SysuserMapper;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.po.Sysuser;
import cn.examsystem.rest.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/30.
 * 用户业务层实现
 */
@Service
public class UserImpl implements UserService {

    @Value("${MESSAGE_SYSUSER_ID_NOT_NULL}")
    private String MESSAGE_SYSUSER_ID_NOT_NULL;
    @Value("${MESSAGE_SYSUSER_NAME_NOT_NULL}")
    private String MESSAGE_SYSUSER_NAME_NOT_NULL;
    @Value("${MESSAGE_SYSUSER_NOT_EXIST}")
    private String MESSAGE_SYSUSER_NOT_EXIST;

    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Autowired
    private SysuserMapper sysuserMapper;

    @Override
    public ResultInfo updateUser(String id,SysuserDto sysuserDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_ID_NOT_NULL,null);

        //名字不能为空
        String sysuserDtoName = sysuserDto.getName();
        if(StringUtils.isBlank(sysuserDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_NAME_NOT_NULL,null);

        //名字预处理
        sysuserDtoName=sysuserDtoName.trim();


        //id对应用户必须存在
        Sysuser sysuserDb = sysuserMapper.selectByPrimaryKey(id);

        if(sysuserDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SYSUSER_NOT_EXIST,null);


        sysuserDb.setName(sysuserDtoName);
        //如果密码不为空，则修改密码
        String sysuserDtoPassword = sysuserDto.getPassword();
        if(!StringUtils.isBlank(sysuserDtoPassword)){
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            sysuserDtoPassword=encoder.encode(sysuserDtoPassword.trim());
            sysuserDb.setPassword(sysuserDtoPassword);
        }


        //修改更新时间
        sysuserDb.setUpdatedTime(new Date());

        //修改用户
        sysuserMapper.updateByPrimaryKey(sysuserDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

}
