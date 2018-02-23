package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.DictInfo;
import cn.examsystem.rest.service.DictInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 数据字典项服务控制类
 */
@RestController
public class DictInfoController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Autowired
    private DictInfoService dictInfoService;

    @GetMapping("/v1/dictInfo")
    public ResultInfo lgetDictInfoByDictType(DictInfo dictInfo) throws Exception{

        List<DictInfo> dictInfoList = dictInfoService.getDictInfoByDictTypeId(dictInfo.getDictTypeId());
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,dictInfoList);
        return resultInfo;
    }
}
