package cn.examsystem.rest.service.impl;

import cn.examsystem.rest.mapper.DictInfoMapper;
import cn.examsystem.rest.pojo.po.DictInfo;
import cn.examsystem.rest.pojo.po.DictInfoExample;
import cn.examsystem.rest.service.DictInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 数据字典项业务层实现
 */
@Service
public class DictInfoImpl implements DictInfoService {

    @Autowired
    private DictInfoMapper dictInfoMapper;

    @Override
    public List<DictInfo> getDictInfoByDictTypeId(String dictTypeId) throws Exception {

        DictInfoExample dictInfoExample=new DictInfoExample();
        DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
        dictInfoCriteria.andDictTypeIdEqualTo(dictTypeId);
        return dictInfoMapper.selectByExample(dictInfoExample);
    }
}
