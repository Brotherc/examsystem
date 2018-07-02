package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Grade;
import cn.examsystem.rest.pojo.vo.GradeVo;
import cn.examsystem.rest.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 年级服务控制类
 */
@RestController
public class GradeController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    @Autowired
    private GradeService gradeService;

    @GetMapping("/v1/grade")
    public ResultInfo listGrade(GradeVo gradeVo) throws Exception{
        //System.out.print(gradeVo.getLessName());

        List<Grade> gradeList = gradeService.listGrade(gradeVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,gradeList);
        return resultInfo;
    }
}
