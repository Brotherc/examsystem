package cn.examsystem.rest.service.impl;

import cn.examsystem.rest.mapper.SchoolYearMapper;
import cn.examsystem.rest.pojo.po.SchoolYear;
import cn.examsystem.rest.pojo.po.SchoolYearExample;
import cn.examsystem.rest.pojo.vo.SchoolYearVo;
import cn.examsystem.rest.service.SchoolYearService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 学年业务层实现
 */
@Service
public class SchoolYearImpl implements SchoolYearService {

    @Autowired
    private SchoolYearMapper schoolYearMapper;

    @Override
    public List<SchoolYear> listSchoolYear(SchoolYearVo schoolYearVo) throws Exception {

        SchoolYearExample schoolYearExample=new SchoolYearExample();
        SchoolYearExample.Criteria schoolYearCriteria = schoolYearExample.createCriteria();
        if(schoolYearVo!=null){
            String lessName=schoolYearVo.getLessName();
            if(!StringUtils.isBlank(lessName)){
                schoolYearCriteria.andNameLessThanOrEqualTo(lessName.trim());
            }
        }
        schoolYearExample.setOrderByClause("name");
        return schoolYearMapper.selectByExample(schoolYearExample);
    }
}
