package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.MajorDto;
import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.vo.MajorVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 专业业务层接口
 */
public interface MajorService {

    //根据条件查询专业
    public List<MajorDto>listMajor(MajorVo majorVo) throws Exception;

    //添加专业
    public ResultInfo saveMajor(Major major) throws Exception;

    //更新专业
    public ResultInfo updateMajor(String id, Major major) throws Exception;
}
