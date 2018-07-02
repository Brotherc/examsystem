package cn.examsystem.manager.controller;

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
 * Created by Administrator on 2018/1/28.
 * 专业控制器类
 */
@RestController
public class MajorController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${MAJOR_URL}")
    private String MAJOR_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private MajorService majorService;

    @GetMapping("/v1/major")
    public ResultInfo listMajor(MajorVo majorVo) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            List<MajorDto> majorList = majorService.listMajor(majorVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,majorList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/major")
    public ResultInfo btchDeleteMajor(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/major")
    public ResultInfo saveMajor(Major major) throws Exception{

        ResultInfo resultInfo;
        System.out.println(major);
        try {
            //调用rest服务
            resultInfo=majorService.saveMajor(major);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/major/{id}")
    public ResultInfo updateMajor(@PathVariable String id, Major major) throws Exception{

        ResultInfo resultInfo;
        System.out.println(major.getName());
        try {
            //调用rest服务
            resultInfo=majorService.updateMajor(id,major);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
