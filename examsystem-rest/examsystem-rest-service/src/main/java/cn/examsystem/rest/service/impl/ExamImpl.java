package cn.examsystem.rest.service.impl;

import cn.examsystem.common.jedis.JedisClient;
import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.*;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.StudentDto;
import cn.examsystem.rest.pojo.po.Class;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.ClassVo;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.pojo.vo.StudentVo;
import cn.examsystem.rest.quartz.ExamQuartz;
import cn.examsystem.rest.service.ExamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static org.quartz.JobBuilder.newJob;

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
    @Value("${MESSAGE_EXAM_ID_NOT_NULL}")
    private String MESSAGE_EXAM_ID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_NOT_NULL}")
    private String MESSAGE_STUDENT_NOT_NULL;
    @Value("${MESSAGE_STUDENT_STUDENTID_NOT_NULL}")
    private String MESSAGE_STUDENT_STUDENTID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_NAME_NOT_NULL}")
    private String MESSAGE_STUDENT_NAME_NOT_NULL;
    @Value("${MESSAGE_CLASS_ID_NOT_NULL}")
    private String MESSAGE_CLASS_ID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_EXAM_PARTORDER_NOT_NULL}")
    private String MESSAGE_STUDENT_EXAM_PARTORDER_NOT_NULL;
    @Value("${MESSAGE_STUDENT_EXAM_IS_END}")
    private String MESSAGE_STUDENT_EXAM_IS_END;
    @Value("${MESSAGE_EXAM_NOT_EXIST}")
    private String MESSAGE_EXAM_NOT_EXIST;
    @Value("${MESSAGE_CLASS_NOT_EXIST}")
    private String MESSAGE_CLASS_NOT_EXIST;
    @Value("${MESSAGE_STUDENT_STATUS_IS_STOP}")
    private String MESSAGE_STUDENT_STATUS_IS_STOP;
    @Value("${MESSAGE_STUDENT_EXAM_EXIST}")
    private String MESSAGE_STUDENT_EXAM_EXIST;
    @Value("${MESSAGE_PARAM_NOT_MATCH}")
    private String MESSAGE_PARAM_NOT_MATCH;
    @Value("${MESSAGE_PART_ORDER_MORE}")
    private String MESSAGE_PART_ORDER_MORE;
    @Value("${MESSAGE_EXAM_STUDENT_ID_NOT_NULL}")
    private String MESSAGE_EXAM_STUDENT_ID_NOT_NULL;
    @Value("${MESSAGE_EXAM_STUDENT_NOT_EXIST}")
    private String MESSAGE_EXAM_STUDENT_NOT_EXIST;
    @Value("${MESSAGE_STUDENT_NOT_EXIST}")
    private String MESSAGE_STUDENT_NOT_EXIST;
    @Value("${MESSAGE_EXAM_NOT_STARTTIME}")
    private String MESSAGE_EXAM_NOT_STARTTIME;
    @Value("${MESSAGE_EXAM_IS_STARTED}")
    private String MESSAGE_EXAM_IS_STARTED;
    @Value("${MESSAGE_EXAM_IS_NOT_STARTED}")
    private String MESSAGE_EXAM_IS_NOT_STARTED;
    @Value("${MESSAGE_EXAM_PWD_NOT_NULL}")
    private String MESSAGE_EXAM_PWD_NOT_NULL;
    @Value("${MESSAGE_EXAM_PWD_NOT_SAME}")
    private String MESSAGE_EXAM_PWD_NOT_SAME;
    @Value("${MESSAGE_EXAM_STUDENT_PROCEED_AND_LOCAL_NOT_NULL}")
    private String MESSAGE_EXAM_STUDENT_PROCEED_AND_LOCAL_NOT_NULL;
    @Value("${MESSAGE_INVIGILATE_PWD_NOT_NULL}")
    private String MESSAGE_INVIGILATE_PWD_NOT_NULL;
    @Value("${MESSAGE_INVIGILATE_PWD_NOT_SAME}")
    private String MESSAGE_INVIGILATE_PWD_NOT_SAME;
    @Value("${MESSAGE_STUDENT_NO_EXIST_COURSE}")
    private String MESSAGE_STUDENT_NO_EXIST_COURSE;
    @Value("${MESSAGE_STUDENT_ID_NOT_NULL}")
    private String MESSAGE_STUDENT_ID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_STUDENTID_NOT_REPEAT}")
    private String MESSAGE_STUDENT_STUDENTID_NOT_REPEAT;

    @Value("${MESSAGE_STUDENT_EXAM_STATUS_NOT_NULL}")
    private String MESSAGE_STUDENT_EXAM_STATUS_NOT_NULL;
    @Value("${MESSAGE_STUDENT_EXAM_STATUS_ILLEGAL}")
    private String MESSAGE_STUDENT_EXAM_STATUS_ILLEGAL;
    @Value("${MESSAGE_STUDENT_EXAM_IS_MAKE_UP}")
    private String MESSAGE_STUDENT_EXAM_IS_MAKE_UP;
    @Value("${MESSAGE_STUDENT_EXAM_PARTORDER_IS_END}")
    private String MESSAGE_STUDENT_EXAM_PARTORDER_IS_END;



    @Value("${DICTINFO_EXAM_NOT_START_CODE}")
    private String DICTINFO_EXAM_NOT_START_CODE;
    @Value("${DICTINFO_EXAM_IS_PROCEED_CODE}")
    private String DICTINFO_EXAM_IS_PROCEED_CODE;
    @Value("${DICTINFO_EXAM_IS_END_CODE}")
    private String DICTINFO_EXAM_IS_END_CODE;

    @Value("${DICTINFO_STUDENT_EXAM_NOT_START_CODE}")
    private String DICTINFO_STUDENT_EXAM_NOT_START_CODE;
    @Value("${DICTINFO_STUDENT_STATUS_IS_STTOP_CODE}")
    private String DICTINFO_STUDENT_STATUS_IS_STTOP_CODE;
    @Value("${DICTINFO_STUDENT_STATUS_IS_USED_CODE}")
    private String DICTINFO_STUDENT_STATUS_IS_USED_CODE;
    @Value("${DICTINFO_STUDENT_EXAM_IS_PROCEED_CODE}")
    private String DICTINFO_STUDENT_EXAM_IS_PROCEED_CODE;
    @Value("${DICTINFO_STUDENT_EXAM_IS_END_CODE}")
    private String DICTINFO_STUDENT_EXAM_IS_END_CODE;
    @Value("${DICTINFO_STUDENT_EXAM_IS_MAKE_UP_CODE}")
    private String DICTINFO_STUDENT_EXAM_IS_MAKE_UP_CODE;

    @Value("${REDIS_KEY_EXAM_STUDENT_IS_PROCEEDED}")
    private String REDIS_KEY_EXAM_STUDENT_IS_PROCEEDED;


    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;


    @Value("${FILE_PATH_PRE_EXAM_STUDENT_EXCEL}")
    private String FILE_PATH_PRE_EXAM_STUDENT_EXCEL;

    @Value("${REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER}")
    private String REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER;
    @Value("${REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER}")
    private String REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER;
    @Value("${REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER}")
    private String REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER;
    @Value("${REDIS_KEY_PROGRAM_QUESTION_ORDER}")
    private String REDIS_KEY_PROGRAM_QUESTION_ORDER;
    @Value("${REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER}")
    private String REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER}")
    private String REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER}")
    private String REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER;
    @Value("${REDIS_KEY_PROGRAM_QUESTION_ANSWER}")
    private String REDIS_KEY_PROGRAM_QUESTION_ANSWER;

    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;
    @Value("${DICTINFO_PROGRAMQUESTION_TYPE_CODE}")
    private String DICTINFO_PROGRAMQUESTION_TYPE_CODE;

    @Autowired
    private JedisClient jedisClient;

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
    @Autowired
    private ExamStudentRelationMapper examStudentRelationMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private ClassMapperCustom classMapperCustom;
    @Autowired
    private TestpaperQuestionRelationMapper testpaperQuestionRelationMapper;
    @Autowired
    private ExamstudentAnswerMapper examstudentAnswerMapper;
    @Autowired
    private CourseMajorRelationMapper courseMajorRelationMapper;

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

        //System.out.println(startTime);
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

        //如果场次大于1，则间隔时间不能为空或0
        Integer intervalTime = exam.getIntervalTime();
        if(partNum>1&&(intervalTime==null||intervalTime==0))
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

        //如果场次为1，则场次间隔时间为0
        if(partNum==1)
            exam.setIntervalTime(0);


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


        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //id对应考试必须存在
        Exam examDb = examMapper.selectByPrimaryKey(id);
        if(examDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //只能修改状态为未启动的考试
        if(!examDb.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        Date startTime = exam.getStartTime();
        if(startTime==null){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_START_TIME_NOT_NULL,null);
        }

        //System.out.println(startTime);
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

        //如果场次大于1，则间隔时间不能为空或0
        Integer intervalTime = exam.getIntervalTime();
        if(partNum>1&&(intervalTime==null||intervalTime==0))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_INTERVAL_TIME_NOT_NULL,null);

        //修改的考试所属的试卷必须存在
        TestPaper testPaperDb = testPaperMapper.selectByPrimaryKey(testPaperId);
        if(testPaperDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NOT_EXIST,null);

        //修改的考试的学年必须存在
        SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(examSchoolYearId);
        if(schoolYear==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_NOT_EXIST,null);

        examDb.setTestPaperId(testPaperId);
        examDb.setSchoolYearId(examSchoolYearId);
        examDb.setTerm(term);
        examDb.setPartNum(partNum);

        //如果场次为1，则场次间隔时间为0
        if(partNum==1)
            intervalTime=0;

        examDb.setIntervalTime(intervalTime);

        long time = (endTime.getTime() - startTime.getTime()) / 1000;
        examDb.setTime((int)time);
        examDb.setStartTime(startTime);
        examDb.setEndTime(endTime);
        //补全创建时间，更新时间
        examDb.setUpdatedTime(new Date());

        //更新考试
        examMapper.updateByPrimaryKey(examDb);

        //如果场次不为1，重新为该门考试的学生分配场次
        if(partNum!=1){
            ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
            ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
            examStudentRelationCriteria.andExamIdEqualTo(id);
            List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);
            if(!CollectionUtils.isEmpty(examStudentRelationList)){//该门考试存在学生
                //该门考试学生总人数
                int studentNum=examStudentRelationList.size();
                //平均每场次学生人数
                int avgStudentNum=studentNum/partNum;

                int index=0;//初始化分配场次
                int remainStudent=studentNum%partNum;//无法整数分配剩余学生人数

                for(int i=0;i<examStudentRelationList.size()-remainStudent;i++){
                    if(i%avgStudentNum==0)
                        index++;
                    //更新考试学生场次信息
                    ExamStudentRelation examStudentRelation = examStudentRelationList.get(i);
                    examStudentRelation.setPartOrder(index);
                    examStudentRelation.setUpdatedTime(new Date());
                    examStudentRelationMapper.updateByPrimaryKey(examStudentRelation);
                }

                //重新分配剩余人数
                index=1;
                if(remainStudent!=0){
                    for(int i=examStudentRelationList.size()-remainStudent;i<examStudentRelationList.size();i++){
                        //更新考试学生场次信息
                        ExamStudentRelation examStudentRelation = examStudentRelationList.get(i);
                        examStudentRelation.setPartOrder(index);
                        examStudentRelation.setUpdatedTime(new Date());
                        examStudentRelationMapper.updateByPrimaryKey(examStudentRelation);
                        index++;
                    }
                }


            }
        }


        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public ResultInfo addStudentForExam(String id, StudentDto studentDto) throws Exception {
        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //为学生添加的考试必须存在
        Exam exam = examMapper.selectByPrimaryKey(id);
        if(exam==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //只允许为未启动的考试添加考试学生
        if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        //学生不允许为空
        if(studentDto==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NOT_NULL,null);

        //学生学号不能为空
        String studentStudentId=studentDto.getStudentId();
        if(StringUtils.isBlank(studentStudentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_NULL,null);

        //学生姓名不能为空
        String studentDtoName=studentDto.getName();
        if(StringUtils.isBlank(studentDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NAME_NOT_NULL,null);

        //名字预处理
        studentDtoName=studentDtoName.trim();

        //学生班级id不能为空
        String classId = studentDto.getClassId();
        if(StringUtils.isBlank(classId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_ID_NOT_NULL,null);

        //学生考试场次不能为空
        Integer partOrder=studentDto.getPartOrder();
        if(partOrder==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_PARTORDER_NOT_NULL,null);

        //学生班级必须存在
        Class aClass = classMapper.selectByPrimaryKey(classId);
        if(aClass==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_NOT_EXIST,null);

        //场次不能超出该门考试的最大场次
        if(partOrder>exam.getPartNum())
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PART_ORDER_MORE,null);

        //学生所属专业必须存在该门考试的考试科目
        CourseMajorRelationExample courseMajorRelationExample=new CourseMajorRelationExample();
        CourseMajorRelationExample.Criteria courseMajorRelationCriteria = courseMajorRelationExample.createCriteria();
        courseMajorRelationCriteria.andCourseIdEqualTo(exam.getCourseId());
        courseMajorRelationCriteria.andMajorIdEqualTo(aClass.getMajorId());
        List<CourseMajorRelation> courseMajorRelationList = courseMajorRelationMapper.selectByExample(courseMajorRelationExample);
        if(CollectionUtils.isEmpty(courseMajorRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NO_EXIST_COURSE,null);

        Student student=null;

        //判断系统中是否存在该学生
        StudentExample studentExample=new StudentExample();
        StudentExample.Criteria studentCriteria = studentExample.createCriteria();
        studentCriteria.andStudentIdEqualTo(studentStudentId);
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        if(!CollectionUtils.isEmpty(studentList)){//存在
            student=studentList.get(0);

            if(student.getStatus()==new Integer(DICTINFO_STUDENT_STATUS_IS_STTOP_CODE))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STATUS_IS_STOP,null);

            //修改学生信息
            student.setName(studentDtoName);
            student.setClassId(classId);
            student.setUpdatedTime(new Date());
            studentMapper.updateByPrimaryKey(student);

        }
        else {//不存在
            //将该学生保存到系统中
            String studentId = UUIDBuild.getUUID();
            studentDto.setId(studentId);
            studentDto.setName(studentDtoName);
            //设置学生学号后4位为密码
            String pwd=studentStudentId.substring(studentStudentId.length()-4);
            //加密
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            pwd=encoder.encode(pwd);
            studentDto.setPassword(pwd);
            studentDto.setStatus(new Integer(DICTINFO_STUDENT_STATUS_IS_USED_CODE));
            studentDto.setCreatedTime(new Date());
            studentDto.setUpdatedTime(new Date());

            studentMapper.insert(studentDto);

            student=studentDto;
        }


        //该门考试如果存在该学生，则不允许添加
        ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
        ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
        examStudentRelationCriteria.andExamIdEqualTo(id);
        examStudentRelationCriteria.andStudentIdEqualTo(student.getId());
        List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);
        if(!CollectionUtils.isEmpty(examStudentRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_EXIST,null);


        //将该学生添加到该门考试中
        ExamStudentRelation examStudentRelation=new ExamStudentRelation();
        String relationId = UUIDBuild.getUUID();
        examStudentRelation.setId(relationId);
        examStudentRelation.setExamId(id);
        examStudentRelation.setStudentId(student.getId());
        examStudentRelation.setStatus(new Integer(DICTINFO_STUDENT_EXAM_NOT_START_CODE));
        examStudentRelation.setScore(new BigDecimal(0));
        examStudentRelation.setIsGraded(false);
        examStudentRelation.setPartOrder(partOrder);
        examStudentRelation.setCreatedTime(new Date());
        examStudentRelation.setUpdatedTime(new Date());
        examStudentRelationMapper.insert(examStudentRelation);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);


    }

    @Override
    public ResultInfo addStudentsForExam(String id, String[] studentIds) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //id对应的考试存在
        Exam exam = examMapper.selectByPrimaryKey(id);
        if(exam==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //只允许为未启动的考试添加考试学生
        if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        //studentIds对应学生存在，并且状态正常
        for(String studentId:studentIds){
            Student student = studentMapper.selectByPrimaryKey(studentId);
            if(student==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NOT_EXIST,null);
            if(student.getStatus()==new Integer(DICTINFO_STUDENT_STATUS_IS_STTOP_CODE))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STATUS_IS_STOP,null);

            //学生所属专业必须存在该门考试的考试科目
            Class aClass = classMapper.selectByPrimaryKey(student.getClassId());
            if(aClass!=null){
                CourseMajorRelationExample courseMajorRelationExample=new CourseMajorRelationExample();
                CourseMajorRelationExample.Criteria courseMajorRelationCriteria = courseMajorRelationExample.createCriteria();
                courseMajorRelationCriteria.andCourseIdEqualTo(exam.getCourseId());
                courseMajorRelationCriteria.andMajorIdEqualTo(aClass.getMajorId());
                List<CourseMajorRelation> courseMajorRelationList = courseMajorRelationMapper.selectByExample(courseMajorRelationExample);
                if(CollectionUtils.isEmpty(courseMajorRelationList))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NO_EXIST_COURSE,null);
            }
        }


        Map<Integer,Integer> partOrders=new HashMap<>();
        //查询该考试的各场次人数
        for(int i=1;i<=exam.getPartNum();i++){
            ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
            ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
            examStudentRelationCriteria.andPartOrderEqualTo(i);
            examStudentRelationCriteria.andExamIdEqualTo(id);
            List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);
            partOrders.put(i,examStudentRelationList.size());
        }
        //System.out.println(partOrders);

        //如果该考试中不存在该学生，则将学生分配至考试中
        for(String studentId:studentIds){
            ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
            ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
            examStudentRelationCriteria.andExamIdEqualTo(id);
            examStudentRelationCriteria.andStudentIdEqualTo(studentId);
            List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);
            if(CollectionUtils.isEmpty(examStudentRelationList)){
                ExamStudentRelation examStudentRelation=new ExamStudentRelation();
                String relationId = UUIDBuild.getUUID();
                examStudentRelation.setId(relationId);
                examStudentRelation.setExamId(id);
                examStudentRelation.setStudentId(studentId);
                examStudentRelation.setStatus(new Integer(DICTINFO_STUDENT_EXAM_NOT_START_CODE));
                examStudentRelation.setScore(new BigDecimal(0));
                examStudentRelation.setIsGraded(false);

                //获得最小人数的场次
                Map.Entry<Integer, Integer> mapMinEntry = MathUtils.getMapMinEntry(partOrders);
                Integer key=mapMinEntry.getKey();
                //System.out.println(key);
                examStudentRelation.setPartOrder(key);
                partOrders.put(key,partOrders.get(key)+1);
                //System.out.println(partOrders);

                examStudentRelation.setCreatedTime(new Date());
                examStudentRelation.setUpdatedTime(new Date());

                examStudentRelationMapper.insert(examStudentRelation);
            }
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo addStudentForExamByExcel(String id, String fileName,byte[] uploadData) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //为学生添加的考试必须存在
        Exam exam = examMapper.selectByPrimaryKey(id);
        if(exam==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //只允许为未启动的考试添加考试学生
        if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        //将上传的文件写到磁盘

        //获得上传文件所在磁盘路径
        String absolutePath = FileUtil.byte2File(uploadData,FILE_PATH_PRE_EXAM_STUDENT_EXCEL,UUIDBuild.getUUID()+fileName.substring(fileName.lastIndexOf(".")));

        //读取文件
        Workbook book = POIUtils.getExcelWorkbook(absolutePath);
        Sheet sheet = POIUtils.getSheetByNum(book,1);

        List<StudentDto> studentListFromExcel=null;

        try {
            studentListFromExcel = getStudentListFromExcel(book, sheet);
        }catch (Exception e){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARAM_NOT_MATCH,null);
        }

        //处理成功的数量
        Integer countSuccess = 0;
        //处理失败的数量
        Integer countError = 0;
        //错误信息
        List<ResultInfo> errorDetails=new ArrayList<>();

        for(StudentDto studentDto:studentListFromExcel){
            String errorMessage="";
            //学生学号不能为空
            String studentStudentId=studentDto.getStudentId();
            if(StringUtils.isBlank(studentStudentId))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_NULL,null);


            //学生姓名不能为空
            String studentDtoName=studentDto.getName();
            if(StringUtils.isBlank(studentDtoName)){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=studentStudentId+":姓名不能为空";
                else
                    errorMessage+=",姓名不能为空";
            }else
                //名字预处理
                studentDtoName=studentDtoName.trim();

            //学生年级名字不能为空
            String studentDtoGradeName=studentDto.getGradeName();
            if(StringUtils.isBlank(studentDtoGradeName)){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=studentStudentId+":年级不能为空";
                else
                    errorMessage+=",年级不能为空";
            }else
                //名字预处理
                studentDtoGradeName=studentDtoGradeName.trim();

            //学生专业名字不能为空
            String studentDtoMajorName=studentDto.getMajorName();
            if(StringUtils.isBlank(studentDtoMajorName)){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=studentStudentId+":专业不能为空";
                else
                    errorMessage+=",专业不能为空";
            }else
                //名字预处理
                studentDtoMajorName=studentDtoMajorName.trim();

            //学生班级名字不能为空
            Integer studentDtoClassName=studentDto.getClassName();
            if(studentDtoClassName==null){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=studentStudentId+":班级不能为空";
                else
                    errorMessage+=",班级不能为空";
            }

            //学生考试场次不能为空
            Integer partOrder=studentDto.getPartOrder();
            if(partOrder==null){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=studentStudentId+":场次不能为空";
                else
                    errorMessage+=",场次不能为空";
            }else{
                //场次不能超出该门考试的最大场次
                if(partOrder>exam.getPartNum()){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":场次超出最大范围";
                    else
                        errorMessage+=",场次超出最大范围";
                }
            }

            //对应年级存在
            Grade grade=null;
            if(!StringUtils.isBlank(studentDtoGradeName)){
                GradeExample gradeExample=new GradeExample();
                GradeExample.Criteria gradeCriteria = gradeExample.createCriteria();
                gradeCriteria.andNameEqualTo(studentDtoGradeName);
                List<Grade> gradeList = gradeMapper.selectByExample(gradeExample);
                if(CollectionUtils.isEmpty(gradeList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":不存在该年级";
                    else
                        errorMessage+=",不存在该年级";
                }else
                    grade=gradeList.get(0);
            }


            //对应专业存在
            Major major=null;
            if(!StringUtils.isBlank(studentDtoMajorName)){
                MajorExample majorExample=new MajorExample();
                MajorExample.Criteria majorCriteria = majorExample.createCriteria();
                majorCriteria.andNameEqualTo(studentDtoMajorName);
                List<Major> majorList = majorMapper.selectByExample(majorExample);
                if(CollectionUtils.isEmpty(majorList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":不存在该专业";
                    else
                        errorMessage+=",不存在该专业";
                }else
                    major=majorList.get(0);
            }

            //对应班级信息存在
            Class aClass=null;
            if(major!=null&&grade!=null&&studentDtoClassName!=null){
                ClassExample classExampl=new ClassExample();
                ClassExample.Criteria classCriteria = classExampl.createCriteria();
                classCriteria.andNameEqualTo(studentDtoClassName);
                classCriteria.andGradeIdEqualTo(grade.getId());
                classCriteria.andMajorIdEqualTo(major.getId());
                List<Class> classList = classMapper.selectByExample(classExampl);
                if(CollectionUtils.isEmpty(classList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":不存在该班级";
                    else
                        errorMessage+=",不存在该班级";
                }else
                    aClass = classList.get(0);
            }

            //学生所属专业必须存在该门考试的考试科目
            if(major!=null){
                CourseMajorRelationExample courseMajorRelationExample=new CourseMajorRelationExample();
                CourseMajorRelationExample.Criteria courseMajorRelationCriteria = courseMajorRelationExample.createCriteria();
                courseMajorRelationCriteria.andCourseIdEqualTo(exam.getCourseId());
                courseMajorRelationCriteria.andMajorIdEqualTo(major.getId());
                List<CourseMajorRelation> courseMajorRelationList = courseMajorRelationMapper.selectByExample(courseMajorRelationExample);
                if(CollectionUtils.isEmpty(courseMajorRelationList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":学生并未学习该门课程，无法为其分配考试";
                    else
                        errorMessage+=",学生并未学习该门课程，无法为其分配考试";
                }
            }


            if(!StringUtils.isBlank(errorMessage)){
                countError++;
                ResultInfo resultInfo = new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY, errorMessage, null);
                errorDetails.add(resultInfo);
                continue;
            }

            //判断是否存在该学号的学生，
            StudentExample studentExample=new StudentExample();
            StudentExample.Criteria studentCriteria = studentExample.createCriteria();
            studentCriteria.andStudentIdEqualTo(studentStudentId);
            List<Student> studentList = studentMapper.selectByExample(studentExample);

            Student student=null;

            // 若存在，则修改学生信息
            if(!CollectionUtils.isEmpty(studentList)){
                Student studentDb = studentList.get(0);

                if(studentDb.getStatus()==new Integer(DICTINFO_STUDENT_STATUS_IS_STTOP_CODE)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=studentStudentId+":学号已被暂停";
                    else
                        errorMessage+=",学号已被暂停";
                }else{
                    studentDb.setName(studentDtoName);
                    studentDb.setClassId(aClass.getId());
                    studentDb.setUpdatedTime(new Date());
                    studentMapper.updateByPrimaryKey(studentDb);
                    student=studentDb;
                }

            }else{
                //若不存在，则保存该学生信息
                String studentId = UUIDBuild.getUUID();
                studentDto.setId(studentId);
                studentDto.setName(studentDtoName);
                //设置学生学号后4位为密码
                String pwd=studentStudentId.substring(studentStudentId.length()-4);
                //加密
                BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
                pwd=encoder.encode(pwd);
                studentDto.setPassword(pwd);
                studentDto.setStatus(new Integer(DICTINFO_STUDENT_STATUS_IS_USED_CODE));
                studentDto.setClassId(aClass.getId());
                studentDto.setCreatedTime(new Date());
                studentDto.setUpdatedTime(new Date());

                studentMapper.insert(studentDto);

                student=studentDto;
            }

            //该门考试如果存在该学生，则不允许添加
            ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
            ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
            examStudentRelationCriteria.andExamIdEqualTo(id);
            examStudentRelationCriteria.andStudentIdEqualTo(student.getId());
            List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);
            if(!CollectionUtils.isEmpty(examStudentRelationList)){
                countSuccess++;
                continue;
            }

            //将该学生添加到该门考试中
            ExamStudentRelation examStudentRelation=new ExamStudentRelation();
            String relationId = UUIDBuild.getUUID();
            examStudentRelation.setId(relationId);
            examStudentRelation.setExamId(id);
            examStudentRelation.setStudentId(student.getId());
            examStudentRelation.setStatus(new Integer(DICTINFO_STUDENT_EXAM_NOT_START_CODE));
            examStudentRelation.setScore(new BigDecimal(0));
            examStudentRelation.setIsGraded(false);
            examStudentRelation.setPartOrder(partOrder);
            examStudentRelation.setCreatedTime(new Date());
            examStudentRelation.setUpdatedTime(new Date());
            examStudentRelationMapper.insert(examStudentRelation);
            countSuccess++;

        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,countSuccess,errorDetails);
    }

    private List getStudentListFromExcel(Workbook book,Sheet sheet) throws Exception{
        //System.out.println("sheet名称是："+sheet.getSheetName());

        int lastRowNum = sheet.getLastRowNum();

        List<StudentDto> studentList=new ArrayList<>();

        for(int i=1;i<=lastRowNum;i++){
            Row row = sheet.getRow(i);
            if(row != null){

                StudentDto student=new StudentDto();

                Cell cell1 = row.getCell(0);
                String studentId=null;
                if(cell1 != null){
                    studentId=cell1.getStringCellValue();
                    student.setStudentId(studentId);
                }
                Cell cell2 = row.getCell(1);
                String name=null;
                if(cell2 != null){
                    name=cell2.getStringCellValue();
                    student.setName(name);
                }

                Cell cell3 = row.getCell(2);
                String gradeName=null;
                if(cell3 != null){
                    gradeName=String.valueOf((int)cell3.getNumericCellValue());
                    student.setGradeName(gradeName);
                }

                Cell cell4 = row.getCell(3);
                String majorName=null;
                if(cell4 != null){
                    majorName=cell4.getStringCellValue();
                    student.setMajorName(majorName);
                }

                Cell cell5 = row.getCell(4);
                Integer className=null;
                if(cell5 != null){
                    className=(int)cell5.getNumericCellValue();
                    student.setClassName(className);
                }

                Cell cell6 = row.getCell(5);
                Integer partOrder=null;
                if(cell6 != null){
                    partOrder=(int)cell6.getNumericCellValue();
                    student.setPartOrder(partOrder);
                }
                if(StringUtils.isBlank(studentId)&&StringUtils.isBlank(name)&&StringUtils.isBlank(gradeName)&&StringUtils.isBlank(majorName)&&className==null&&partOrder==null)
                    continue;
                studentList.add(student);
            }
        }
        return studentList;
    }

    @Override
    public List<ExamStudentRelationDto> listExamStudent(ExamStudentRelationVo examStudentRelationVo) throws Exception {
        //System.out.print("考试id"+examStudentRelationVo.getExamId());
        return examMapperCustom.listExamStudent(examStudentRelationVo);
    }

    @Override
    public ResultInfo removeStudentFromExam(String examStudentRelationId) throws Exception {

        ExamStudentRelation examStudentRelation = examStudentRelationMapper.selectByPrimaryKey(examStudentRelationId);

        //只允许移除未启动的考试学生
        if(examStudentRelation!=null){
            Exam exam = examMapper.selectByPrimaryKey(examStudentRelation.getExamId());
            if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);
        }
        examStudentRelationMapper.deleteByPrimaryKey(examStudentRelationId);
        return new ResultInfo(ResultInfo.STATUS_RESULT_NO_CONTENT,MESSAGE_DELETE_SUCCESS,null);
    }

    @Override
    public ResultInfo updateExamStudentPartOrder(String examStudentRelationId, Integer partOrdeer) throws Exception {

        //id对应的考试学生关系存在
        ExamStudentRelation examStudentRelation = examStudentRelationMapper.selectByPrimaryKey(examStudentRelationId);
        if(examStudentRelation==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //只允许更新未启动的考试学生的场次
        if(examStudentRelation!=null){
            Exam exam = examMapper.selectByPrimaryKey(examStudentRelation.getExamId());
            if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);
        }

        //id不允许为空
        if(StringUtils.isBlank(examStudentRelationId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_ID_NOT_NULL,null);

        //场次不能为空
        if(partOrdeer==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_PARTORDER_NOT_NULL,null);

        //场次不能超出范围
        Exam exam = examMapper.selectByPrimaryKey(examStudentRelation.getExamId());
        if(partOrdeer>exam.getPartNum())
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PART_ORDER_MORE,null);

        examStudentRelation.setPartOrder(partOrdeer);
        examStudentRelation.setUpdatedTime(new Date());
        examStudentRelationMapper.updateByPrimaryKey(examStudentRelation);
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public ResultInfo updateExamStudentStatus(String examStudentRelationId, Integer status) throws Exception {

        //id对应的考试学生关系存在
        ExamStudentRelation examStudentRelation = examStudentRelationMapper.selectByPrimaryKey(examStudentRelationId);
        if(examStudentRelation==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //id不允许为空
        if(StringUtils.isBlank(examStudentRelationId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_ID_NOT_NULL,null);

        //状态不能为空
        if(status==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_STATUS_NOT_NULL,null);

        //只允许更新正在考试的考试学生的状态
        if(examStudentRelation!=null){
            if(!examStudentRelation.getStatus().equals(new Integer(DICTINFO_STUDENT_EXAM_IS_PROCEED_CODE)))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_STATUS_ILLEGAL,null);
        }

        examStudentRelation.setStatus(status);
        examStudentRelation.setUpdatedTime(new Date());
        examStudentRelationMapper.updateByPrimaryKey(examStudentRelation);

        //清除缓存中有关该学生的考试答题信息
        jedisClient.del(examStudentRelationId);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public List<Student> listStudentNoExistExam(String examId, StudentVo studentVo) throws Exception {

        //查询出该门考试的所有学生id
        ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
        ExamStudentRelationExample.Criteria examStudentRelationCriteria = examStudentRelationExample.createCriteria();
        examStudentRelationCriteria.andExamIdEqualTo(examId);
        List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);

        //查询状态正常，id不在上述查询的ids中的学生
        StudentExample studentExample=new StudentExample();
        StudentExample.Criteria studentCriteria = studentExample.createCriteria();
        studentCriteria.andStatusEqualTo(new Integer(DICTINFO_STUDENT_STATUS_IS_USED_CODE));

        if(!CollectionUtils.isEmpty(examStudentRelationList)){
            List<String> studentIds=new ArrayList<>();

            for(ExamStudentRelation relation:examStudentRelationList){
                studentIds.add(relation.getStudentId());
            }
            studentCriteria.andIdNotIn(studentIds);
        }

        //构造查询条件
        if(studentVo!=null){
            String studentStudentId=studentVo.getStudentId();
            if(!StringUtils.isBlank(studentStudentId))
                studentCriteria.andStudentIdEqualTo(studentStudentId.trim());

            String name=studentVo.getName();
            if(!StringUtils.isBlank(name))
                studentCriteria.andNameEqualTo(name.trim());

            String classId=studentVo.getClassId();
            if(!StringUtils.isBlank(classId))
                studentCriteria.andClassIdEqualTo(classId);
        }

        return studentMapper.selectByExample(studentExample);
    }

    @Override
    public ResultInfo startExam(String id) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //id对应考试必须存在
        Exam examDb = examMapper.selectByPrimaryKey(id);

        if(examDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //如果考试的状态不是未开启，则不允许启动
        Integer status = examDb.getStatus();
        //System.out.println(status+"------------"+examDb.getStatus());
        if(!status.equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        //如果当前时间<考试启动时间，则不允许启动
        if(new Date().compareTo(examDb.getStartTime())==-1)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_STARTTIME,null);

        //修改考试状态
        examDb.setStatus(new Integer(DICTINFO_EXAM_IS_PROCEED_CODE));
        examDb.setUpdatedTime(new Date());
        examMapper.updateByPrimaryKey(examDb);

        //启动一个定时器，在最后一个场次结束时，修改考试状态
        //定时器启动时间：考试结束时间+（场次-1）*间隔时间
  /*      Timer timer=new Timer();
        final Exam examTimer=examDb;
        System.out.println(examDb.getTime()+(examDb.getPartNum()-1)*examDb.getIntervalTime());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //修改考试状态
                examTimer.setStatus(new Integer(DICTINFO_EXAM_IS_END_CODE));
                examTimer.setUpdatedTime(new Date());
                examMapper.updateByPrimaryKey(examTimer);

            }
        },(examDb.getTime()+(examDb.getPartNum()-1)*examDb.getIntervalTime())*1000);*/

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        String triggerName="trigger"+UUIDBuild.getUUID();
        String groupName="group"+UUIDBuild.getUUID();
        //定义一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, groupName) //定义name/group
                .startAt(DateUtil.getDateAfterSeconds(examDb.getEndTime(),Long.valueOf((examDb.getPartNum()-1)*examDb.getIntervalTime())))//设置定时器起始时间
                .withSchedule(SimpleScheduleBuilder.simpleSchedule() //使用SimpleTrigger
                )
                .build();

        JobDataMap map=new JobDataMap();
        map.put("exam",examDb);
        map.put("examMapper",examMapper);
        map.put("code",DICTINFO_EXAM_IS_END_CODE);

        //定义一个JobDetail
        JobDetail job = newJob(ExamQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity(triggerName, groupName) //定义name/group
                .setJobData(map)
                .build();

        scheduler.scheduleJob(job, trigger);

        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);

    }


    @Override
    public ResultInfo getProceedExamByLoginStudentId(String studentId) throws Exception {
        //查询该学生的所有考试
        ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
        ExamStudentRelationExample.Criteria relationCriteria = examStudentRelationExample.createCriteria();
        relationCriteria.andStudentIdEqualTo(studentId);
        List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);

        Exam exam=null;
        ExamStudentRelationDto examStudentRelation=null;

        //查询该学生所有考试中正在进行的考试
        for(ExamStudentRelation relation:examStudentRelationList){
            Exam examDb = examMapper.selectByPrimaryKey(relation.getExamId());
            //System.out.println(examDb.getStatus());
            if(examDb.getStatus().equals(new Integer(DICTINFO_EXAM_IS_PROCEED_CODE))){
                exam=examDb;
                examStudentRelation=new ExamStudentRelationDto();
                BeanUtils.copyProperties(relation,examStudentRelation);
                break;
            }
        }

        //如果没有该学生任何正在进行的考试信息，说明考试未启动
        if(exam==null||examStudentRelation==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_NOT_FOUND,MESSAGE_EXAM_IS_NOT_STARTED,null);

        //当前时间>学生考试场次结束时间，说明学生该场次考试已结束

        Date endTime = DateUtil.getDateAfterSeconds(exam.getEndTime(), (long) (examStudentRelation.getPartOrder() - 1) * exam.getIntervalTime());
        //System.out.println("学生考试结束时间"+endTime);
        if(new Date().compareTo(endTime)==1)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_EXAM_PARTORDER_IS_END,null);

        //如果存在正在进行的考试，但学生已考完
        if(examStudentRelation!=null&&examStudentRelation.getStatus().equals(new Integer(DICTINFO_STUDENT_EXAM_IS_END_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_NOT_FOUND,MESSAGE_STUDENT_EXAM_IS_END,null);

        //如果存在正在进行的考试，但学生被安排至补考
        if(examStudentRelation!=null&&examStudentRelation.getStatus().equals(new Integer(DICTINFO_STUDENT_EXAM_IS_MAKE_UP_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_NOT_FOUND,MESSAGE_STUDENT_EXAM_IS_MAKE_UP,null);

        Student student=studentMapper.selectByPrimaryKey(studentId);

        if(examStudentRelation!=null&&exam!=null){
            //设置该学生的考试信息（学号）
            examStudentRelation.setStudentStudentId(student.getStudentId());
            //设置该学生的考试信息（姓名）
            examStudentRelation.setStudentName(student.getName());

            //查询班级信息
            ClassVo classVo=new ClassVo();
            classVo.setId(student.getClassId());
            List<ClassDto> classDtoList = classMapperCustom.listClass(classVo);

            if(!CollectionUtils.isEmpty(classDtoList)){
                ClassDto classDto = classDtoList.get(0);
                //设置该学生的考试信息（系）
                examStudentRelation.setDepartmentName(classDto.getDepartmentName());
                //设置该学生的考试信息（年级）
                examStudentRelation.setGradeName(classDto.getGradeName());
                //设置该学生的考试信息（专业）
                examStudentRelation.setMajorName(classDto.getMajorName());
                //设置该学生的考试信息（班级）
                examStudentRelation.setClassName(classDto.getName());
            }

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(exam.getCourseId());
            if(course!=null)//设置该学生的考试信息（考试科目）
                examStudentRelation.setCourseName(course.getName());

            //查询学年信息
            SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(exam.getSchoolYearId());
            if(schoolYear!=null)//设置该学生的考试信息（学年）
                examStudentRelation.setSchoolYearName(schoolYear.getName());

            //设置该学生的考试信息（学期）
            examStudentRelation.setTerm(exam.getTerm());
            //设置该学生的考试信息（考试时长）
            examStudentRelation.setTime(exam.getTime());

            //设置该学生的考试信息（试卷id）
            examStudentRelation.setTestPaperId(exam.getTestPaperId());

            //设置该学生的考试信息（该场次开考时间）
            //开考时间：该考试的开考时间+（场次-1）*场次时间间隔
            examStudentRelation.setPartOrderStartTime(DateUtil.getDateAfterSeconds(exam.getStartTime(),(long)(examStudentRelation.getPartOrder()-1)*exam.getIntervalTime()));

            //查询该学生是否进行过考试
            Boolean exists = jedisClient.exists(examStudentRelation.getId());
            examStudentRelation.setIsProceeded(exists);
            //System.out.println(examStudentRelation.getIsProceeded());
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelation);
    }

    @Override
    public ResultInfo test(ExamStudentRelationDto examStudentRelationDto) throws Exception {

        if(examStudentRelationDto==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //id不允许为空
        String examStudentRelationId = examStudentRelationDto.getId();
        if(StringUtils.isBlank(examStudentRelationId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_ID_NOT_NULL,null);

        //考试id不允许为空
        String examId = examStudentRelationDto.getExamId();
        if(StringUtils.isBlank(examId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //学生考试
        Boolean proceeded = examStudentRelationDto.getIsProceeded();
        Boolean local = examStudentRelationDto.getIsLocal();

        //System.out.println(local);
        if(proceeded==null&&local==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_PROCEED_AND_LOCAL_NOT_NULL,null);

        String examPwd = examStudentRelationDto.getExamPwd();
        String invigilatePwd = examStudentRelationDto.getInvigilatePwd();

        if(!proceeded){//没进行过考试
            //考试密码不能为空
            if(StringUtils.isBlank(examPwd))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_PWD_NOT_NULL,null);
            //考试密码预处理
            examPwd=examPwd.trim();
        }else{
            if(local){
                //考试密码不能为空
                if(StringUtils.isBlank(examPwd))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_PWD_NOT_NULL,null);
                //考试密码预处理
                examPwd=examPwd.trim();
            }else{
                //监考密码不能为空
                if(StringUtils.isBlank(invigilatePwd))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_INVIGILATE_PWD_NOT_NULL,null);
                //监考密码预处理
                invigilatePwd=invigilatePwd.trim();
            }
        }

        //查询考试信息
        Exam exam = examMapper.selectByPrimaryKey(examId);
        if(exam==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //查询学生考试信息
        ExamStudentRelation examStudentRelation = examStudentRelationMapper.selectByPrimaryKey(examStudentRelationId);
        if(examStudentRelation==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //当前时间<学生考试场次开始时间，则不允许开始考试

        Date startTime = DateUtil.getDateAfterSeconds(exam.getStartTime(), (long) (examStudentRelation.getPartOrder() - 1) * exam.getIntervalTime());
        //System.out.println("学生考试开始时间"+startTime);
        if(new Date().compareTo(startTime)==-1)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_STARTTIME,null);

        //密码校验
        if(!proceeded){//没进行过考试
            //验证学生输入考试密码是否正确
            if(!StringUtils.equals(exam.getExamPwd(),examPwd))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_PWD_NOT_SAME,null);
        }else{
            if(local){
                //验证学生输入考试密码是否正确
                if(!StringUtils.equals(exam.getExamPwd(),examPwd))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_PWD_NOT_SAME,null);
            }else{
                //验证学生输入监考密码是否正确
                if(!StringUtils.equals(exam.getInvigilatePwd(),invigilatePwd))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_INVIGILATE_PWD_NOT_SAME,null);
            }
        }

        //修改学生考试的状态为进行中
        examStudentRelation.setStatus(new Integer(DICTINFO_STUDENT_EXAM_IS_PROCEED_CODE));
        examStudentRelation.setUpdatedTime(new Date());
        examStudentRelationMapper.updateByPrimaryKey(examStudentRelation);

        String json = jedisClient.hget(examStudentRelationId, REDIS_KEY_EXAM_STUDENT_IS_PROCEEDED);
        if(StringUtils.isBlank(json))
            jedisClient.hset(examStudentRelationId,REDIS_KEY_EXAM_STUDENT_IS_PROCEEDED,"1");

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo submitTestPaper(ExamStudentRelationDto examStudentRelationDto, String testPaperId) throws Exception {

        if(examStudentRelationDto==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //试卷id不能为空
        if(StringUtils.isBlank(testPaperId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_ID_NOT_NULL,null);

        //考试学生关系id不允许为空
        String examStudentRelationId = examStudentRelationDto.getId();
        if(StringUtils.isBlank(examStudentRelationId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_ID_NOT_NULL,null);

        //考试学生关系id对应的考试学生必须存在
        ExamStudentRelation examStudentRelationDb = examStudentRelationMapper.selectByPrimaryKey(examStudentRelationId);
        if(examStudentRelationDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_STUDENT_NOT_EXIST,null);

        //修改考试学生状态为已考
        examStudentRelationDb.setStatus(new Integer(DICTINFO_STUDENT_EXAM_IS_END_CODE));
        examStudentRelationDb.setUpdatedTime(new Date());
        examStudentRelationMapper.updateByPrimaryKey(examStudentRelationDb);



        //提交试卷
/*        String key=studentUuid+"_"+ksgluuid;
        String studentSjUuid = jedisClient.hget(key, STUDENT_SJ);*/
        String singleChoiceQuestionOrderJson = jedisClient.hget(examStudentRelationId, REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER);
        List<Integer> singleChoiceQuestionOrderList=null;
        Map<Integer, String> singleChoiceQuestionAnswer=null;
        if(!StringUtils.isBlank(singleChoiceQuestionOrderJson)){
            singleChoiceQuestionOrderList = JsonUtils.jsonToList(singleChoiceQuestionOrderJson, Integer.class);
            singleChoiceQuestionAnswer = JsonUtils.jsonToMap(jedisClient.hget(examStudentRelationId, REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER), Integer.class, String.class);
        }

        String trueOrFalseQuestionOrderJson = jedisClient.hget(examStudentRelationId, REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER);
        List<Integer> trueOrFalseQuestionOrderList=null;
        Map<Integer, String> trueOrFalseQuestionAnswer=null;
        if(!StringUtils.isBlank(trueOrFalseQuestionOrderJson)){
            trueOrFalseQuestionOrderList = JsonUtils.jsonToList(trueOrFalseQuestionOrderJson, Integer.class);
            trueOrFalseQuestionAnswer = JsonUtils.jsonToMap(jedisClient.hget(examStudentRelationId, REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER), Integer.class, String.class);
        }

        String fillInBlankQuestionOrderJson = jedisClient.hget(examStudentRelationId, REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER);
        List<Integer> fillInBlankQuestionOrderList=null;
        Map<Integer, List> fillInBlankQuestionAnswer=null;
        if(!StringUtils.isBlank(fillInBlankQuestionOrderJson)){
            fillInBlankQuestionOrderList = JsonUtils.jsonToList(fillInBlankQuestionOrderJson, Integer.class);
            fillInBlankQuestionAnswer = JsonUtils.jsonToMap(jedisClient.hget(examStudentRelationId, REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER), Integer.class, List.class);
        }

        String programQuestionOrderJson = jedisClient.hget(examStudentRelationId, REDIS_KEY_PROGRAM_QUESTION_ORDER);
        List<Integer> programQuestionOrderList=null;
        Map<Integer, String> programQuestionAnswer=null;
        if(!StringUtils.isBlank(programQuestionOrderJson)){
            programQuestionOrderList = JsonUtils.jsonToList(programQuestionOrderJson, Integer.class);
            programQuestionAnswer = JsonUtils.jsonToMap(jedisClient.hget(examStudentRelationId, REDIS_KEY_PROGRAM_QUESTION_ANSWER), Integer.class, String.class);
        }

        int index=1;

        if(!CollectionUtils.isEmpty(singleChoiceQuestionOrderList))
            for(Integer i:singleChoiceQuestionOrderList){
                //单选题提交
                ExamstudentAnswer examstudentAnswer=new ExamstudentAnswer();
                //设置id
                String studentAnswerId = UUIDBuild.getUUID();
                examstudentAnswer.setId(studentAnswerId);
                //设置考试学生id
                examstudentAnswer.setExamStudentId(examStudentRelationId);
                //设置答案
                if(singleChoiceQuestionAnswer==null||StringUtils.isBlank(singleChoiceQuestionAnswer.get(index)))
                    examstudentAnswer.setStudentAnswer("");
                else
                    examstudentAnswer.setStudentAnswer(singleChoiceQuestionAnswer.get(index));
                //设置分数初始为0分
                examstudentAnswer.setScore(new BigDecimal(0));
                //设置为未评分
                examstudentAnswer.setIsGraded(false);
                examstudentAnswer.setCreatedTime(new Date());
                examstudentAnswer.setUpdatedTime(new Date());


                //设置试卷题目id
                //查询该题目对应原先试卷的哪一道题目
                TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
                TestpaperQuestionRelationExample.Criteria questionCriteria = testpaperQuestionRelationExample.createCriteria();
                questionCriteria.andTestPaperIdEqualTo(testPaperId);
                questionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));
                questionCriteria.andQuestionOrderEqualTo(i);

                List<TestpaperQuestionRelation> testpaperQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);

                if(!CollectionUtils.isEmpty(testpaperQuestionRelationList)){
                    examstudentAnswer.setTestpaperQuestionId(testpaperQuestionRelationList.get(0).getId());
                }

                examstudentAnswerMapper.insert(examstudentAnswer);
                index++;
            }


        index=1;

        if(!CollectionUtils.isEmpty(trueOrFalseQuestionOrderList))
            for(Integer i:trueOrFalseQuestionOrderList){

                //判断题提交
                ExamstudentAnswer examstudentAnswer=new ExamstudentAnswer();
                //设置id
                String studentAnswerId = UUIDBuild.getUUID();
                examstudentAnswer.setId(studentAnswerId);
                //设置考试学生id
                examstudentAnswer.setExamStudentId(examStudentRelationId);
                //设置答案
                if(trueOrFalseQuestionAnswer==null||StringUtils.isBlank(trueOrFalseQuestionAnswer.get(index)))
                    examstudentAnswer.setStudentAnswer("");
                else
                    examstudentAnswer.setStudentAnswer(trueOrFalseQuestionAnswer.get(index));
                //设置分数初始为0分
                examstudentAnswer.setScore(new BigDecimal(0));
                //设置为未评分
                examstudentAnswer.setIsGraded(false);
                examstudentAnswer.setCreatedTime(new Date());
                examstudentAnswer.setUpdatedTime(new Date());


                //设置试卷题目id
                //查询该题目对应原先试卷的哪一道题目
                TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
                TestpaperQuestionRelationExample.Criteria questionCriteria = testpaperQuestionRelationExample.createCriteria();
                questionCriteria.andTestPaperIdEqualTo(testPaperId);
                questionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));
                questionCriteria.andQuestionOrderEqualTo(i);

                List<TestpaperQuestionRelation> testpaperQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);

                if(!CollectionUtils.isEmpty(testpaperQuestionRelationList)){
                    examstudentAnswer.setTestpaperQuestionId(testpaperQuestionRelationList.get(0).getId());
                }

                examstudentAnswerMapper.insert(examstudentAnswer);
                index++;
            }

        index=1;

        if(!CollectionUtils.isEmpty(fillInBlankQuestionOrderList))
            for(Integer i:fillInBlankQuestionOrderList){

                //填空题提交
                ExamstudentAnswer examstudentAnswer=new ExamstudentAnswer();
                //设置id
                String studentAnswerId = UUIDBuild.getUUID();
                examstudentAnswer.setId(studentAnswerId);
                //设置考试学生id
                examstudentAnswer.setExamStudentId(examStudentRelationId);
                //设置分数初始为0分
                examstudentAnswer.setScore(new BigDecimal(0));
                //设置为未评分
                examstudentAnswer.setIsGraded(false);
                examstudentAnswer.setCreatedTime(new Date());
                examstudentAnswer.setUpdatedTime(new Date());

                //设置试卷题目id
                //查询该题目对应原先试卷的哪一道题目
                TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
                TestpaperQuestionRelationExample.Criteria questionCriteria = testpaperQuestionRelationExample.createCriteria();
                questionCriteria.andTestPaperIdEqualTo(testPaperId);
                questionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
                questionCriteria.andQuestionOrderEqualTo(i);

                List<TestpaperQuestionRelation> testpaperQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);

                if(!CollectionUtils.isEmpty(testpaperQuestionRelationList)){
                    examstudentAnswer.setTestpaperQuestionId(testpaperQuestionRelationList.get(0).getId());
                }


                List<String> questionAnswerList = fillInBlankQuestionAnswer.get(index);
                for(int j=0;j<questionAnswerList.size();j++){
                    //去除所有空格
                    String s=questionAnswerList.get(j).replaceAll(" ", "");
                    questionAnswerList.set(j, s);
                    //System.out.println(s);
                }

                //设置答案
                String json = JsonUtils.objectToJson(questionAnswerList);
                examstudentAnswer.setStudentAnswer(json);

                //System.out.println(examstudentAnswer.getTestpaperQuestionId());

                examstudentAnswerMapper.insert(examstudentAnswer);
                index++;
            }

        index=1;

        if(!CollectionUtils.isEmpty(programQuestionOrderList))
            for(Integer i:programQuestionOrderList){

                //程序题提交
                ExamstudentAnswer examstudentAnswer=new ExamstudentAnswer();
                //设置id
                String studentAnswerId = UUIDBuild.getUUID();
                examstudentAnswer.setId(studentAnswerId);
                //设置考试学生id
                examstudentAnswer.setExamStudentId(examStudentRelationId);
                //设置答案
                //System.out.println("程序题答案"+programQuestionAnswer.get(index));
                if(programQuestionAnswer==null||StringUtils.isBlank(programQuestionAnswer.get(index)))
                    examstudentAnswer.setStudentAnswer("");
                else
                    examstudentAnswer.setStudentAnswer(programQuestionAnswer.get(index));
                //设置分数初始为0分
                examstudentAnswer.setScore(new BigDecimal(0));
                //设置为未评分
                examstudentAnswer.setIsGraded(false);
                examstudentAnswer.setCreatedTime(new Date());
                examstudentAnswer.setUpdatedTime(new Date());


                //设置试卷题目id
                //查询该题目对应原先试卷的哪一道题目
                TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
                TestpaperQuestionRelationExample.Criteria questionCriteria = testpaperQuestionRelationExample.createCriteria();
                questionCriteria.andTestPaperIdEqualTo(testPaperId);
                questionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_PROGRAMQUESTION_TYPE_CODE));
                questionCriteria.andQuestionOrderEqualTo(i);

                List<TestpaperQuestionRelation> testpaperQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);

                if(!CollectionUtils.isEmpty(testpaperQuestionRelationList)){
                    examstudentAnswer.setTestpaperQuestionId(testpaperQuestionRelationList.get(0).getId());
                }

                examstudentAnswerMapper.insert(examstudentAnswer);
                index++;
            }

        //将缓存中该学生考试相关信息删除
        jedisClient.del(examStudentRelationId);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);

    }

    @Override
    public ExamDto getExam(String id) throws Exception {
        Exam exam = examMapper.selectByPrimaryKey(id);
        ExamDto examDto=null;
        if(exam!=null) {
            examDto = new ExamDto();

            //构造fillInBlankQuestionDto
            BeanUtils.copyProperties(exam,examDto);

            //查询试卷信息
            TestPaper testPaper = testPaperMapper.selectByPrimaryKey(exam.getTestPaperId());

            examDto.setTestPaperName(testPaper.getName());
        }
        return examDto;
    }

    @Override
    public List<ExamStudentRelationDto> listInvigilationExamStudent(String examId,ExamStudentRelationVo examStudentRelationVo) throws Exception {

        Exam exam = examMapper.selectByPrimaryKey(examId);


        examStudentRelationVo=examStudentRelationVo==null?new ExamStudentRelationVo():examStudentRelationVo;

        examStudentRelationVo.setExamId(examId);
        List<ExamStudentRelationDto> examStudentRelationDtoList = examMapperCustom.listInvigilationExamStudent(examStudentRelationVo);

        for(ExamStudentRelationDto relationDto:examStudentRelationDtoList){
            //构造每个考试学生的考试剩余时间

            //开考时间：该考试的开考时间+（场次-1）*场次时间间隔
            relationDto.setPartOrderStartTime(DateUtil.getDateAfterSeconds(exam.getStartTime(),(long)(relationDto.getPartOrder()-1)*exam.getIntervalTime()));


            //计算剩余考试时间
            Long endTime=relationDto.getPartOrderStartTime().getTime()+exam.getTime()*1000;
            Long currentTime=System.currentTimeMillis();
            relationDto.setRemainTime(endTime-currentTime);
        }

        return examStudentRelationDtoList;
    }

    @Override
    public ResultInfo updateExamStudent(String examId, String studentId, Student student) throws Exception {

        //考试id不允许为空
        if(StringUtils.isBlank(examId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //修改的考试学生所在考试必须存在
        Exam exam = examMapper.selectByPrimaryKey(examId);
        if(exam==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //只允许修改未启动的考试对应的考试学生
        if(!exam.getStatus().equals(new Integer(DICTINFO_EXAM_NOT_START_CODE)))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_IS_STARTED,null);

        //id不允许为空
        if(StringUtils.isBlank(studentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_ID_NOT_NULL,null);

        //学号不允许为空
        String studentStudentId=student.getStudentId();
        if(StringUtils.isBlank(studentStudentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_NULL,null);

        //学号预处理
        studentStudentId=studentStudentId.trim();

        //名字不允许为空
        String studentName=student.getName();
        if(StringUtils.isBlank(studentName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NAME_NOT_NULL,null);

        //名字预处理
        studentName=studentName.trim();

        //学生班级id不能为空
        String classId = student.getClassId();
        if(StringUtils.isBlank(classId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_ID_NOT_NULL,null);

        //学生班级必须存在
        Class aClass = classMapper.selectByPrimaryKey(classId);
        if(aClass==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_NOT_EXIST,null);

        //id对应学生必须存在
        Student studentDb = studentMapper.selectByPrimaryKey(studentId);

        if(studentDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NOT_EXIST,null);

        //若对学号进行了修改
        if(!StringUtils.equals(studentStudentId,studentDb.getStudentId())){
            //学号不能重复
            StudentExample studentExample=new StudentExample();
            StudentExample.Criteria studentCriteria = studentExample.createCriteria();
            studentCriteria.andStudentIdEqualTo(studentStudentId);
            List<Student> studentList = studentMapper.selectByExample(studentExample);
            if(!CollectionUtils.isEmpty(studentList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_REPEAT,null);
            }
            //设置学号
            studentDb.setStudentId(studentStudentId);
        }

        //如果密码不为空，则修改密码
        String studentPassword = student.getPassword();
        if(!StringUtils.isBlank(studentPassword)){
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            studentPassword=encoder.encode(studentPassword.trim());
            studentDb.setPassword(studentPassword);
        }

        //如果状态不为空，则修改状态
        Integer status = student.getStatus();
        if(status!=null)
            studentDb.setStatus(status);

        //更新学生
        studentDb.setName(studentName);
        studentDb.setClassId(classId);
        studentDb.setUpdatedTime(new Date());
        studentMapper.updateByPrimaryKey(studentDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
