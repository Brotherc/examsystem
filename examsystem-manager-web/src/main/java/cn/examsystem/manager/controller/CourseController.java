package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.vo.CourseVo;
import cn.examsystem.rest.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private CourseService courseService;

    @GetMapping("/v1/course")
    public ResultInfo listCourse(CourseVo courseVo) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务

            List<CourseDto> courseList = courseService.listCourse(courseVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,courseList);
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
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
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
            resultInfo=courseService.saveCourse(courseDto);
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
            resultInfo=courseService.updateCourse(id,courseDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
