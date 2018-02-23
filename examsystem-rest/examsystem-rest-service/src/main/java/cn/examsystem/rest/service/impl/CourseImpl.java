package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.CourseDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.CourseVo;
import cn.examsystem.rest.service.CourseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 课程业务层实现
 */
@Service
public class CourseImpl implements CourseService {

    @Value("${MESSAGE_MAJOR_NAME_NOT_NULL}")
    private String MESSAGE_MAJOR_NAME_NOT_NULL;
    @Value("${MESSAGE_MAJOR_NAME_NOT_REPEAT}")
    private String MESSAGE_MAJOR_NAME_NOT_REPEAT;
    @Value("${MESSAGE_MAJOR_ID_NOT_NULL}")
    private String MESSAGE_MAJOR_ID_NOT_NULL;
    @Value("${MESSAGE_MAJOR_NOT_EXIST}")
    private String MESSAGE_MAJOR_NOT_EXIST;

    @Value("${MESSAGE_COURSE_NAME_NOT_NULL}")
    private String MESSAGE_COURSE_NAME_NOT_NULL;
    @Value("${MESSAGE_COURSE_NAME_NOT_REPEAT}")
    private String MESSAGE_COURSE_NAME_NOT_REPEAT;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;

    @Value("${MESSAGE_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_TEACHER_NOT_EXIST}")
    private String MESSAGE_TEACHER_NOT_EXIST;

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Autowired
    private CourseMapperCustom courseMapperCustom;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private CourseMajorRelationMapper courseMajorRelationMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private CourseTeacherRelationMapper courseTeacherRelationMapper;
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Override
    public Course getCourse(String id) throws Exception {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CourseDto> listCourse(CourseVo courseVo) throws Exception {
        return courseMapperCustom.listCourse(courseVo);
    }

    @Override
    public List<Course> getCourseByTeacherId(String teacherId) throws Exception {
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationCriteria.andTeacherIdEqualTo(teacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(!CollectionUtils.isEmpty(courseTeacherRelationList)){
            List<String> coursesId=new ArrayList<>();
            for(CourseTeacherRelation courseTeacherRelation:courseTeacherRelationList){
                coursesId.add(courseTeacherRelation.getCourseId());
            }
            CourseExample courseExample=new CourseExample();
            CourseExample.Criteria courseCriteria = courseExample.createCriteria();
            courseCriteria.andIdIn(coursesId);
            return courseMapper.selectByExample(courseExample);
        }
        return null;
    }

    @Override
    public ResultInfo saveCourse(CourseDto courseDto) throws Exception {

        //名字不能为空
        String courseDtoName = courseDto.getName();
        if(StringUtils.isBlank(courseDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NAME_NOT_NULL,null);

        //名字预处理
        courseDtoName=courseDtoName.trim();

        //专业id不能为空
        String majorsId=courseDto.getMajorsId();
        if(StringUtils.isBlank(majorsId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_ID_NOT_NULL,null);

        String[] majorsIdArr=null;

        //教师id不能为空
        String teachersId=courseDto.getTeachersId();
        if(StringUtils.isBlank(teachersId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_ID_NOT_NULL,null);

        String[] teachersIdArr=null;

        //添加的课程所属的专业必须存在
        majorsIdArr=stirngIdsToArrIds(majorsId);
        for(String majorId:majorsIdArr){
            Major major = majorMapper.selectByPrimaryKey(majorId);
            if(major==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NOT_EXIST,null);
        }

        //添加的课程所属的教师必须存在
        teachersIdArr=stirngIdsToArrIds(teachersId);
        for(String teacherId:teachersIdArr){
            Sysuser sysuser = sysuserMapper.selectByPrimaryKey(teacherId);
            if(sysuser==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_NOT_EXIST,null);
        }

        //名字不能重复
        CourseExample courseExample=new CourseExample();
        CourseExample.Criteria courseCriteria = courseExample.createCriteria();
        courseCriteria.andNameEqualTo(courseDtoName);
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        if(!CollectionUtils.isEmpty(courseList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NAME_NOT_REPEAT,null);
        }

        //补全id
        String courseId = UUIDBuild.getUUID();
        courseDto.setId(courseId);
        courseDto.setName(courseDtoName);

        //对应知识树父节点id
        String knowledgePointId=UUIDBuild.getUUID();
        courseDto.setKnowledgePointId(knowledgePointId);

        //补全创建时间，更新时间
        courseDto.setCreatedTime(new Date());
        courseDto.setUpdatedTime(new Date());

        //添加课程
        courseMapper.insert(courseDto);

        //添加课程专业关系
        saveCourseMajorRelation(courseId,majorsIdArr);

        //添加课程教师关系
        saveCourseTeacherRelation(courseId,teachersIdArr);

        //添加该课程的知识树父节点
        KnowledgePoint knowledgePoint=new KnowledgePoint();
        knowledgePoint.setId(knowledgePointId);
        knowledgePoint.setName(courseDtoName);
        knowledgePoint.setSortOrder(1);
        knowledgePoint.setParentId("0");
        knowledgePoint.setIsParent(false);
        knowledgePoint.setCreatedTime(new Date());
        knowledgePoint.setUpdatedTime(new Date());

        //添加知识点
        knowledgePointMapper.insert(knowledgePoint);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateCourse(String id, CourseDto courseDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //名字不能为空
        String courseDtoName = courseDto.getName();
        if(StringUtils.isBlank(courseDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NAME_NOT_NULL,null);

        //名字预处理
        courseDtoName=courseDtoName.trim();

        //专业id不能为空
        String majorsId=courseDto.getMajorsId();
        if(StringUtils.isBlank(majorsId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_ID_NOT_NULL,null);

        String[] majorsIdArr=null;

        //教师id不能为空
        String teachersId=courseDto.getTeachersId();
        if(StringUtils.isBlank(teachersId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_ID_NOT_NULL,null);

        String[] teachersIdArr=null;

        //id对应专业必须存在
        Course courseDb = courseMapper.selectByPrimaryKey(id);

        if(courseDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //修改的课程所属的专业必须存在
        majorsIdArr=stirngIdsToArrIds(majorsId);
        for(String majorId:majorsIdArr){
            Major major = majorMapper.selectByPrimaryKey(majorId);
            if(major==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NOT_EXIST,null);
        }

        //修改的课程所属的教师必须存在
        teachersIdArr=stirngIdsToArrIds(teachersId);
        for(String teacherId:teachersIdArr){
            Sysuser sysuser = sysuserMapper.selectByPrimaryKey(teacherId);
            if(sysuser==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_NOT_EXIST,null);
        }

        //若对课程名字进行了修改
        if(!StringUtils.equals(courseDtoName,courseDb.getName())){
            //则不允许与已存在的课程信息重复
            CourseExample courseExample=new CourseExample();
            CourseExample.Criteria courseCriteria = courseExample.createCriteria();
            courseCriteria.andNameEqualTo(courseDtoName);
            List<Course> courseList = courseMapper.selectByExample(courseExample);
            if(!CollectionUtils.isEmpty(courseList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NAME_NOT_REPEAT,null);
            }
            //设置名字
            courseDb.setName(courseDtoName);

            //修改对应知识树的父节点
            KnowledgePoint knowledgePoint=knowledgePointMapper.selectByPrimaryKey(courseDb.getKnowledgePointId());
            knowledgePoint.setName(courseDtoName);
            knowledgePoint.setUpdatedTime(new Date());
            //更新知识点
            knowledgePointMapper.updateByPrimaryKey(knowledgePoint);
        }

        //更新课程
        courseDb.setUpdatedTime(new Date());
        courseMapper.updateByPrimaryKey(courseDb);

        //删除之前的课程专业关系
        CourseMajorRelationExample courseMajorRelationExample=new CourseMajorRelationExample();
        CourseMajorRelationExample.Criteria courseMajorRelationCriteria = courseMajorRelationExample.createCriteria();
        courseMajorRelationCriteria.andCourseIdEqualTo(id);
        courseMajorRelationMapper.deleteByExample(courseMajorRelationExample);

        //添加新的课程专业关系
        saveCourseMajorRelation(id,majorsIdArr);

        //删除以前的课程教师关系
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherCriteria.andCourseIdEqualTo(id);
        courseTeacherRelationMapper.deleteByExample(courseTeacherRelationExample);

        //添加新的课程教师关系
        saveCourseTeacherRelation(id,teachersIdArr);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    /**
     * 添加课程专业关系
     * @param courseId 课程id
     * @param majorsIdArr  专业id数组
     */
    private void saveCourseMajorRelation(String courseId,String[] majorsIdArr){
        for(String majorId:majorsIdArr){
            CourseMajorRelation courseMajorRelation=new CourseMajorRelation();
            String courseMajorRelationId = UUIDBuild.getUUID();
            courseMajorRelation.setId(courseMajorRelationId);
            courseMajorRelation.setCourseId(courseId);
            courseMajorRelation.setMajorId(majorId);
            courseMajorRelation.setCreatedTime(new Date());
            courseMajorRelation.setUpdatedTime(new Date());

            //添加数据
            courseMajorRelationMapper.insert(courseMajorRelation);
        }
    }

    /**
     * 添加课程教师关系
     * @param courseId 课程id
     * @param teachersIdArr 教师id数组
     */
    private void saveCourseTeacherRelation(String courseId,String[] teachersIdArr){
        for(String teacherId:teachersIdArr){
            CourseTeacherRelation courseTeacherRelation=new CourseTeacherRelation();
            String courseTeacherRelationId = UUIDBuild.getUUID();
            courseTeacherRelation.setId(courseTeacherRelationId);
            courseTeacherRelation.setCourseId(courseId);
            courseTeacherRelation.setTeacherId(teacherId);
            courseTeacherRelation.setCreatedTime(new Date());
            courseTeacherRelation.setUpdatedTime(new Date());

            //添加数据
            courseTeacherRelationMapper.insert(courseTeacherRelation);
        }
    }

    /**
     * 将id字符串转换成数组
     * @param ids 要转成数组的id字符串
     * @return
     */
    private String[] stirngIdsToArrIds(String ids){
        if(ids.contains(","))//超过1个id
            return ids.split(",");
        else
            return new String[]{ids};
    }

}
