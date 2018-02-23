package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.SchoolYear;
import cn.examsystem.rest.pojo.vo.SchoolYearVo;
import cn.examsystem.rest.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 学年服务控制类
 */
@RestController
public class SchoolYearController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Autowired
    private SchoolYearService schoolYearService;

    @GetMapping("/v1/schoolYear")
    public ResultInfo listSchoolYear(SchoolYearVo schoolYearVo) throws Exception{
        List<SchoolYear> schoolYearList = schoolYearService.listSchoolYear(schoolYearVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,schoolYearList);
        return resultInfo;
    }

}
