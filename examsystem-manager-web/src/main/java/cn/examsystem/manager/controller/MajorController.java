package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.po.Major;
import cn.examsystem.rest.pojo.vo.MajorVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import static cn.examsystem.common.utils.UrlUtils.expandURL;

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

    @GetMapping("/v1/major")
    public ResultInfo listMajor(MajorVo majorVo) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(majorVo);
            String url = expandURL(REST_BASE_URL + MAJOR_URL+"?", obj);

            System.out.print(url);

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            System.out.println("---------"+resultInfo);
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
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+MAJOR_URL,HttpMethod.DELETE,ids,ResultInfo.class,new Object[]{});
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
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+MAJOR_URL,HttpMethod.POST,major,ResultInfo.class,new Object[]{});
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
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+MAJOR_URL+"/{id}",HttpMethod.PUT,major,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
