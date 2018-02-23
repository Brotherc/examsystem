package cn.examsystem.rest.service.impl;

import cn.examsystem.rest.mapper.GradeMapper;
import cn.examsystem.rest.pojo.po.Grade;
import cn.examsystem.rest.pojo.po.GradeExample;
import cn.examsystem.rest.pojo.vo.GradeVo;
import cn.examsystem.rest.service.GradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 年级业务层实现
 */
@Service
public class GradeImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public List<Grade> listGrade(GradeVo gradeVo) throws Exception {
        GradeExample gradeExample=new GradeExample();
        GradeExample.Criteria gradeCriteria = gradeExample.createCriteria();
        if(gradeVo!=null){
            String lessName=gradeVo.getLessName();
            if(!StringUtils.isBlank(lessName)){
                gradeCriteria.andNameLessThanOrEqualTo(lessName.trim());
            }
        }
        gradeExample.setOrderByClause("name");
        return gradeMapper.selectByExample(gradeExample);
    }

}
