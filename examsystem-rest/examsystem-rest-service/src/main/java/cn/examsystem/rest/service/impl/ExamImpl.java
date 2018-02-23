package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.RandomUtils;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.service.ExamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 考试业务层实现
 */
@Service
public class ExamImpl implements ExamService {

    @Value("${MESSAGE_DEPARTMENT_NAME_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_NAME_NOT_NULL;
    @Value("${MESSAGE_DEPARTMENT_NAME_NOT_REPEAT}")
    private String MESSAGE_DEPARTMENT_NAME_NOT_REPEAT;
    @Value("${MESSAGE_DEPARTMENT_ID_NOT_NULL}")
    private String MESSAGE_DEPARTMENT_ID_NOT_NULL;
    @Value("${MESSAGE_DEPARTMENT_NOT_EXIST}")
    private String MESSAGE_DEPARTMENT_NOT_EXIST;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_START_TIME_NOT_NULL}")
    private String MESSAGE_START_TIME_NOT_NULL;
    @Value("${MESSAGE_END_TIME_NOT_NULL}")
    private String MESSAGE_END_TIME_NOT_NULL;
    @Value("${MESSAGE_TESTPAPER_ID_NOT_NULL}")
    private String MESSAGE_TESTPAPER_ID_NOT_NULL;
    @Value("${MESSAGE_SCHOOLYEAR_ID_NOT_NULL}")
    private String MESSAGE_SCHOOLYEAR_ID_NOT_NULL;
    @Value("${MESSAGE_TERM_NOT_NULL}")
    private String MESSAGE_TERM_NOT_NULL;
    @Value("${MESSAGE_PART_NUM_NOT_NULL}")
    private String MESSAGE_PART_NUM_NOT_NULL;
    @Value("${MESSAGE_INTERVAL_TIME_NOT_NULL}")
    private String MESSAGE_INTERVAL_TIME_NOT_NULL;
    @Value("${MESSAGE_CREATED_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_CREATED_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;
    @Value("${MESSAGE_TESTPAPER_NOT_EXIST}")
    private String MESSAGE_TESTPAPER_NOT_EXIST;
    @Value("${MESSAGE_SCHOOLYEAR_NOT_EXIST}")
    private String MESSAGE_SCHOOLYEAR_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_NOT_EXIST}")
    private String MESSAGE_CREATED_TEACHER_NOT_EXIST;



    @Value("${DICTINFO_EXAM_NOT_START_CODE}")
    private String DICTINFO_EXAM_NOT_START_CODE;

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamMapperCustom examMapperCustom;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private SchoolYearMapper schoolYearMapper;
    @Autowired
    private SysuserMapper sysuserMapper;

    @Override
    public List<ExamDto> listExam(Exam exam) throws Exception {
        return examMapperCustom.listExam(exam);
    }

    @Override
    public ResultInfo saveExam(Exam exam) throws Exception {

        //课程id不能为空
        String examCourseId = exam.getCourseId();
        if(StringUtils.isBlank(examCourseId)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);
        }

        Date startTime = exam.getStartTime();
        if(startTime==null){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_START_TIME_NOT_NULL,null);
        }

        System.out.println(startTime);
        Date endTime = exam.getEndTime();
        if(endTime==null){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_END_TIME_NOT_NULL,null);
        }

        //试卷id不能为空
        String testPaperId = exam.getTestPaperId();
        if(StringUtils.isBlank(testPaperId)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_ID_NOT_NULL,null);
        }
        //学年id不能为空
        String examSchoolYearId=exam.getSchoolYearId();
        if(StringUtils.isBlank(examSchoolYearId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_ID_NOT_NULL,null);

        //学期不能为空
        Integer term = exam.getTerm();
        if(term==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TERM_NOT_NULL,null);

        //场次不能为空
        Integer partNum = exam.getPartNum();
        if(partNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PART_NUM_NOT_NULL,null);

        //如果场次大于1，则间隔时间不能为空
        Integer intervalTime = exam.getIntervalTime();
        if(partNum>1&&intervalTime==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_INTERVAL_TIME_NOT_NULL,null);

        //创建考试教师id不能为空
        String CreatedTeacherId = exam.getCreatedTeacherId();
        if(StringUtils.isBlank(CreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //添加的考试所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(examCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //添加的考试所属的试卷必须存在
        TestPaper testPaperDb = testPaperMapper.selectByPrimaryKey(testPaperId);
        if(testPaperDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NOT_EXIST,null);

        //添加的考试的学年必须存在
        SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(examSchoolYearId);
        if(schoolYear==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_NOT_EXIST,null);

        //添加的试卷的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(CreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //补全id
        String examId = UUIDBuild.getUUID();
        exam.setId(examId);
        exam.setStatus(new Integer(DICTINFO_EXAM_NOT_START_CODE));
        long time = (endTime.getTime() - startTime.getTime()) / 1000;
        exam.setTime((int)time);
        exam.setExamPwd(RandomUtils.FourPwd());
        exam.setInvigilatePwd(RandomUtils.FourPwd());
        //补全创建时间，更新时间
        exam.setCreatedTime(new Date());
        exam.setUpdatedTime(new Date());

        //添加考试
        examMapper.insert(exam);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateExam(String id, Exam exam) throws Exception {

/*        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_ID_NOT_NULL,null);

        //名字不允许为空
        String departmentName=department.getName();
        if(StringUtils.isBlank(departmentName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_NULL,null);

        //id对应系必须存在
        Department departmentDb = departmentMapper.selectByPrimaryKey(id);

        if(departmentDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NOT_EXIST,null);

        departmentName=departmentName.trim();
        //若对系信息进行了修改
        if(!StringUtils.equals(departmentName,departmentDb.getName())){
            //则不允许与已存在的系信息重复
            DepartmentExample departmentExample=new DepartmentExample();
            DepartmentExample.Criteria departmentCriteria = departmentExample.createCriteria();
            departmentCriteria.andNameEqualTo(departmentName);
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if(!CollectionUtils.isEmpty(departmentList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DEPARTMENT_NAME_NOT_REPEAT,null);
            }

            //更新系
            departmentDb.setName(departmentName);
            departmentDb.setUpdatedTime(new Date());
            departmentMapper.updateByPrimaryKey(departmentDb);
        }*/

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
