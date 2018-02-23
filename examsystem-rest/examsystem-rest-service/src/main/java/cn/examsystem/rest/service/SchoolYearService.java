package cn.examsystem.rest.service;

import cn.examsystem.rest.pojo.po.SchoolYear;
import cn.examsystem.rest.pojo.vo.SchoolYearVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 学年业务层接口
 */
public interface SchoolYearService {

    //查询学年
    public List<SchoolYear>listSchoolYear(SchoolYearVo schoolYearVo) throws Exception;

}
