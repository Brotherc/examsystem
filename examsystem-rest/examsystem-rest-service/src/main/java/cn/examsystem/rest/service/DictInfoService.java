package cn.examsystem.rest.service;

import cn.examsystem.rest.pojo.po.DictInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 * 数据字典项业务层接口
 */
public interface DictInfoService {
    //根据数据字典类型Id查询对应数据字典项
    public List<DictInfo> getDictInfoByDictTypeId(String dictTypeId) throws Exception;
}
