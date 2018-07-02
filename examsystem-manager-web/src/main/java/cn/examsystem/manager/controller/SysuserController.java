package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.SysuserDto;
import cn.examsystem.rest.pojo.vo.SysuserVo;
import cn.examsystem.rest.service.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 * 用户控制器类
 */
@RestController
public class SysuserController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${SYSUSER_URL}")
    private String SYSUSER_URL;

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
    private SysuserService sysuserService;

    @GetMapping("/v1/sysuser")
    public ResultInfo listSysuser(SysuserVo sysuserVo) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            List<SysuserDto> sysuserList = sysuserService.listSysuser(sysuserVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,sysuserList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/sysuser")
    public ResultInfo btchDeleteSysuser(@RequestParam(value = "ids[]") String[] ids) throws Exception{

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

    @PostMapping("/v1/sysuser")
    public ResultInfo saveSysuser(SysuserDto sysuserDto) throws Exception{

        ResultInfo resultInfo;
        System.out.println(sysuserDto);
        try {
            //调用rest服务
            resultInfo=sysuserService.saveSysuser(sysuserDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/sysuser/{id}")
    public ResultInfo updateSysuser(@PathVariable String id, SysuserDto sysuserDto) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=sysuserService.updateSysuser(id,sysuserDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
