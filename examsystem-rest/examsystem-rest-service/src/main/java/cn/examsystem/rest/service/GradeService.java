package cn.examsystem.rest.service;

import cn.examsystem.rest.pojo.po.Grade;
import cn.examsystem.rest.pojo.vo.GradeVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 年级业务层接口
 */
public interface GradeService {

    //查询年级
    public List<Grade>listGrade(GradeVo gradeVo) throws Exception;

}
