package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.vo.SysuserVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 用户业务层接口
 */
public interface SysuserService {

    //根据条件查询用户
    public List<SysuserDto>listSysuser(SysuserVo sysuserVo) throws Exception;

    //添加用户
    public ResultInfo saveSysuser(SysuserDto sysuserDto) throws Exception;

    //更新用户
    public ResultInfo updateSysuser(String id,SysuserDto sysuserDto) throws Exception;

}
