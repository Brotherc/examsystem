package cn.examsystem.rest.mapper;

import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.vo.CourseVo;

import java.util.List;

public interface CourseMapperCustom {
    public List<CourseDto> listCourse(CourseVo courseVo);
}