package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SysuserDto;

/**
 * Created by Administrator on 2018/1/30.
 * 用户业务层接口
 */
public interface UserService {

    //更新用户
    public ResultInfo updateUser(String id, SysuserDto sysuserDto) throws Exception;

}
