package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;

/**
 * Created by Administrator on 2018/1/30.
 * 审核业务层接口
 */
public interface CheckerService {
    //审核题目
    public ResultInfo checkQuestion(String id,String questionType) throws Exception;
}
