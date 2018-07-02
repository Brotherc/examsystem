package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.MajorDto;
import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.vo.MajorVo;
import cn.examsystem.rest.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 专业服务控制类
 */
@RestController
public class MajorController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private MajorService majorService;

    @GetMapping("/v1/major")
    public ResultInfo listMajor(MajorVo majorVo) throws Exception{
        List<MajorDto> majorList = majorService.listMajor(majorVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,majorList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/major")
    public ResultInfo btchDeleteMajor(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/major")
    public ResultInfo saveMajor(@RequestBody Major major) throws Exception{
        return majorService.saveMajor(major);
    }

    @PutMapping("/v1/major/{id}")
    public ResultInfo updateMajor(@PathVariable String id,@RequestBody Major major) throws Exception{
        //System.out.println(major.getName());
        return majorService.updateMajor(id,major);
    }
}
