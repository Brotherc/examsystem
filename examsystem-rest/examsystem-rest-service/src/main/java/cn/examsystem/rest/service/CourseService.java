package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.po.Course;
import cn.examsystem.rest.pojo.vo.CourseVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 课程业务层接口
 */
public interface CourseService {

    //根据id查询课程
    public Course getCourse(String id) throws Exception;

    //根据条件查询课程
    public List<CourseDto>listCourse(CourseVo courseVo) throws Exception;

    //根据教师id查询课程
    public List<Course>getCourseByTeacherId(String teacherId) throws Exception;

    //添加课程
    public ResultInfo saveCourse(CourseDto courseDto) throws Exception;

    //更新课程
    public ResultInfo updateCourse(String id, CourseDto courseDto) throws Exception;
}
