package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.vo.ClassVo;
import cn.examsystem.rest.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 班级服务控制类
 */
@RestController
public class ClassController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private ClassService classService;

    @GetMapping("/v1/class")
    public ResultInfo listClass(ClassVo classVo) throws Exception{
        List<ClassDto> classList = classService.listClass(classVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,classList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/class")
    public ResultInfo btchDeleteClass(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/class")
    public ResultInfo btchSaveClass(@RequestBody ClassDto classDto) throws Exception{
        //System.out.println(classDto.getNames());
        //System.out.println(classDto.getMajorId());
        //System.out.println(classDto.getGradeId());
        return classService.btchSaveClass(classDto);
    }
}
