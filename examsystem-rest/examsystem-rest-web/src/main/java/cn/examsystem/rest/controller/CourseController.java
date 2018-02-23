package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.po.Course;
import cn.examsystem.rest.pojo.vo.CourseVo;
import cn.examsystem.rest.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 课程服务控制类
 */
@RestController
public class CourseController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private CourseService courseService;

    @GetMapping("/v1/course/{id}")
    public ResultInfo getCourse(@PathVariable String id) throws Exception{
        Course course = courseService.getCourse(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,course);
        return resultInfo;
    }

    @GetMapping("/v1/course")
    public ResultInfo listCourse(CourseVo courseVo) throws Exception{
        List<CourseDto> courseList = courseService.listCourse(courseVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,courseList);
        return resultInfo;
    }

    @GetMapping("/v1/course/teacher/{teacherId}")
    public ResultInfo getCourseByTeacherId(@PathVariable String teacherId) throws Exception{
        List<Course> courseList=courseService.getCourseByTeacherId(teacherId);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,courseList);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/course")
    public ResultInfo btchDeleteCourse(@RequestBody String[] ids) throws Exception{

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/course")
    public ResultInfo saveCourse(@RequestBody CourseDto courseDto) throws Exception{
        System.out.println(courseDto.getMajorsId());
        return courseService.saveCourse(courseDto);
    }

    @PutMapping("/v1/course/{id}")
    public ResultInfo updateCourse(@PathVariable String id,@RequestBody CourseDto courseDto) throws Exception{
        System.out.println(courseDto.getMajorsId());
        System.out.println(courseDto.getTeachersId());
        return courseService.updateCourse(id,courseDto);
    }
}
