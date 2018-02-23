package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.vo.ClassVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 班级业务层接口
 */
public interface ClassService {

    //根据条件查询班级
    public List<ClassDto>listClass(ClassVo classVo) throws Exception;

    //批量添加班级
    public ResultInfo btchSaveClass(ClassDto classDto) throws Exception;

}
