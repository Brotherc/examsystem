package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.vo.CourseVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import static cn.examsystem.common.utils.UrlUtils.expandURL;

/**
 * Created by Administrator on 2018/1/28.
 * 课程控制器类
 */
@RestController
public class CourseController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${COURSE_URL}")
    private String COURSE_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @GetMapping("/v1/course")
    public ResultInfo listCourse(CourseVo courseVo) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(courseVo);
            String url = expandURL(REST_BASE_URL + COURSE_URL+"?", obj);

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

    @DeleteMapping("/v1/course")
    public ResultInfo btchDeleteCourse(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL,HttpMethod.DELETE,ids,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/course")
    public ResultInfo saveCourse(CourseDto courseDto) throws Exception{

        ResultInfo resultInfo;
        System.out.println(courseDto.getMajorsId());
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL,HttpMethod.POST,courseDto,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/course/{id}")
    public ResultInfo updateCourse(@PathVariable String id, CourseDto courseDto) throws Exception{

        ResultInfo resultInfo;
        System.out.println(courseDto.getMajorsId());
        System.out.println(courseDto.getTeachersId());
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/{id}",HttpMethod.PUT,courseDto,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
