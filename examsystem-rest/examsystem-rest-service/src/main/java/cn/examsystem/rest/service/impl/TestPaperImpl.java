package cn.examsystem.rest.service.impl;

import cn.examsystem.common.jedis.JedisClient;
import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.common.utils.RandomUtils;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.dto.TestPaperFillInBlankQuestion;
import cn.examsystem.rest.pojo.dto.TestPaperSingleChoiceQuestion;
import cn.examsystem.rest.pojo.dto.TestPaperTrueOrFalseQuestion;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.TestPaperVo;
import cn.examsystem.rest.service.TestPaperService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 班级业务层实现
 */
@Service
public class TestPaperImpl implements TestPaperService {

    @Value("${MESSAGE_TESTPAPER_NAME_NOT_NULL}")
    private String MESSAGE_TESTPAPER_NAME_NOT_NULL;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_SCHOOLYEAR_ID_NOT_NULL}")
    private String MESSAGE_SCHOOLYEAR_ID_NOT_NULL;
    @Value("${MESSAGE_TERM_NOT_NULL}")
    private String MESSAGE_TERM_NOT_NULL;
    @Value("${MESSAGE_SCORE_NOT_NULL}")
    private String MESSAGE_SCORE_NOT_NULL;
    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;
    @Value("${MESSAGE_CREATED_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_CREATED_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;
    @Value("${MESSAGE_SCHOOLYEAR_NOT_EXIST}")
    private String MESSAGE_SCHOOLYEAR_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_NOT_EXIST}")
    private String MESSAGE_CREATED_TEACHER_NOT_EXIST;
    @Value("${MESSAGE_COURSE_NOT_SAME}")
    private String MESSAGE_COURSE_NOT_SAME;
    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;
    @Value("${MESSAGE_QUESTION_NOT_CHECKED}")
    private String MESSAGE_QUESTION_NOT_CHECKED;
    @Value("${MESSAGE_SCORE_NOT_SAME}")
    private String MESSAGE_SCORE_NOT_SAME;

    @Value("${MESSAGE_TESTPAPER_ID_NOT_NULL}")
    private String MESSAGE_TESTPAPER_ID_NOT_NULL;
    @Value("${MESSAGE_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_NUM_NOT_NULL}")
    private String MESSAGE_QUESTION_NUM_NOT_NULL;
    @Value("${MESSAGE_TESTPAPER_NOT_EXIST}")
    private String MESSAGE_TESTPAPER_NOT_EXIST;
    @Value("${MESSAGE_TESTPAPER_NOT_AUTHO}")
    private String MESSAGE_TESTPAPER_NOT_AUTHO;


    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;

    @Value("${REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER}")
    private String REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER;
    @Value("${REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER}")
    private String REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER;
    @Value("${REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER}")
    private String REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER;
    @Value("${REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER}")
    private String REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER}")
    private String REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER}")
    private String REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SchoolYearMapper schoolYearMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Autowired
    private FillInBlankQuestionMapper fillInBlankQuestionMapper;
    @Autowired
    private TestpaperQuestionRelationMapper testpaperQuestionRelationMapper;

    @Override
    public TestPaperDto getTestPaper(String id) throws Exception {
        TestPaper testPaper = testPaperMapper.selectByPrimaryKey(id);
        TestPaperDto testPaperDto=null;
        if(testPaper!=null){
            testPaperDto=new TestPaperDto();

            //构造testPaperDto
            BeanUtils.copyProperties(testPaper,testPaperDto);

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(testPaper.getCourseId());
            if(course!=null){
                testPaperDto.setCourseName(course.getName());
            }

            //查询学年信息
            SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(testPaper.getSchoolYearId());
            if(schoolYear!=null){
                testPaperDto.setSchoolYearName(schoolYear.getName());
            }

            //查询题目信息
            //单选题
            TestpaperQuestionRelationExample singleChoiceQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria singleChoiceQuestionRelationCriteria = singleChoiceQuestionRelationExample.createCriteria();
            singleChoiceQuestionRelationCriteria.andTestPaperIdEqualTo(id);
            singleChoiceQuestionRelationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));
            List<TestpaperQuestionRelation> singleChoiceQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(singleChoiceQuestionRelationExample);
            if(!CollectionUtils.isEmpty(singleChoiceQuestionRelationList)){
                testPaperDto.setSingleChoiceQuestionNum(singleChoiceQuestionRelationList.size());
                testPaperDto.setSingleChoiceQuestionScore(singleChoiceQuestionRelationList.get(0).getQuestionScore());
            }
            //判断题
            TestpaperQuestionRelationExample trueOrFalseQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria trueOrFalseQuestionRelationCriteria = trueOrFalseQuestionRelationExample.createCriteria();
            trueOrFalseQuestionRelationCriteria.andTestPaperIdEqualTo(id);
            trueOrFalseQuestionRelationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));
            List<TestpaperQuestionRelation> trueOrFalseQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(trueOrFalseQuestionRelationExample);
            if(!CollectionUtils.isEmpty(trueOrFalseQuestionRelationList)){
                testPaperDto.setTrueOrFalseQuestionNum(trueOrFalseQuestionRelationList.size());
                testPaperDto.setTrueOrFalseQuestionScore(trueOrFalseQuestionRelationList.get(0).getQuestionScore());
            }
            //填空题
            TestpaperQuestionRelationExample fillInBlankQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria fillInBlankQuestionRelationCriteria = fillInBlankQuestionRelationExample.createCriteria();
            fillInBlankQuestionRelationCriteria.andTestPaperIdEqualTo(id);
            fillInBlankQuestionRelationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
            List<TestpaperQuestionRelation> fillInBlankQuestionRelationList = testpaperQuestionRelationMapper.selectByExample(fillInBlankQuestionRelationExample);
            if(!CollectionUtils.isEmpty(fillInBlankQuestionRelationList)){
                TestpaperQuestionRelation testpaperQuestionRelation = fillInBlankQuestionRelationList.get(0);
                String questionId=testpaperQuestionRelation.getQuestionId();

                //查询出该填空题
                FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(questionId);
                testPaperDto.setFillInBlankQuestionScore(testpaperQuestionRelation.getQuestionScore().divide(new BigDecimal(fillInBlankQuestion.getBlankNum()),1,BigDecimal.ROUND_DOWN));
                System.out.println(testPaperDto.getFillInBlankQuestionScore());

                Integer questionNum=0;
                for(TestpaperQuestionRelation relation:fillInBlankQuestionRelationList){
                    questionNum+=fillInBlankQuestionMapper.selectByPrimaryKey(relation.getQuestionId()).getBlankNum();
                }
                testPaperDto.setFillInBlankQuestionNum(questionNum);
            }
        }
        return testPaperDto;
    }

    @Override
    public List<TestPaper> listTestPaper(TestPaperVo testPaperVo) throws Exception {
        TestPaperExample testPaperExample=new TestPaperExample();
        if(testPaperVo!=null){
            TestPaperExample.Criteria testPaperCriteria = testPaperExample.createCriteria();

            String testPaperVoName = testPaperVo.getName();
            if(!StringUtils.isBlank(testPaperVoName))
                testPaperCriteria.andNameEqualTo(testPaperVoName.trim());

            String testPaperVoCourseId = testPaperVo.getCourseId();
            if(!StringUtils.isBlank(testPaperVoCourseId))
                testPaperCriteria.andCourseIdEqualTo(testPaperVoCourseId);

            String testPaperVoSchoolYearId = testPaperVo.getSchoolYearId();
            if(!StringUtils.isBlank(testPaperVoSchoolYearId))
                testPaperCriteria.andSchoolYearIdEqualTo(testPaperVoSchoolYearId);

            Integer testPaperVoTerm = testPaperVo.getTerm();
            if(testPaperVoTerm!=null)
                testPaperCriteria.andTermEqualTo(testPaperVoTerm);
        }

        return testPaperMapper.selectByExample(testPaperExample);
    }

    @Override
    public ResultInfo saveTestPaper(TestPaperDto testPaperDto) throws Exception {

        //名字不能为空
        String testPaperDtoName = testPaperDto.getName();
        if(StringUtils.isBlank(testPaperDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NAME_NOT_NULL,null);

        //名字预处理
        testPaperDtoName=testPaperDtoName.trim();

        //课程id不能为空
        String testPaperDtoCourseId=testPaperDto.getCourseId();
        if(StringUtils.isBlank(testPaperDtoCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //学年id不能为空
        String testPaperDtoSchoolYearId=testPaperDto.getSchoolYearId();
        if(StringUtils.isBlank(testPaperDtoSchoolYearId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_ID_NOT_NULL,null);

        //学期不能为空
        Integer term = testPaperDto.getTerm();
        if(term==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TERM_NOT_NULL,null);

        //分数不能为空
        BigDecimal testPaperDtoScore =testPaperDto.getScore();
        if(testPaperDtoScore==null||testPaperDtoScore.equals(0))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

        String singleChoiceQuestionIds = testPaperDto.getSingleChoiceQuestionIds();

        List<String> singleIdsListCustom = new ArrayList<>();

        if(!StringUtils.isBlank(singleChoiceQuestionIds)){
            String[] singleIdsArr=singleChoiceQuestionIds.split(",");
            for(String id:singleIdsArr){
                if(!StringUtils.isBlank(id))
                    singleIdsListCustom.add(id.trim());
            }
        }

        String trueOrFalseQuestionIds = testPaperDto.getTrueOrFalseQuestionIds();

        List<String> trueOrFalseIdsListCustom = new ArrayList<>();
        if(!StringUtils.isBlank(trueOrFalseQuestionIds)){
            String[] trueOrFalseIdsArr=trueOrFalseQuestionIds.split(",");
            for(String id:trueOrFalseIdsArr){
                if(!StringUtils.isBlank(id))
                    trueOrFalseIdsListCustom.add(id.trim());
            }
        }


        String fillInBlankQuestionIds = testPaperDto.getFillInBlankQuestionIds();

        List<String> fillIdsListCustom = new ArrayList<>();
        if(!StringUtils.isBlank(fillInBlankQuestionIds)){
            String[] fillIdsArr=fillInBlankQuestionIds.split(",");
            for(String id:fillIdsArr){
                if(!StringUtils.isBlank(id))
                    fillIdsListCustom.add(id.trim());
            }
        }

        if(CollectionUtils.isEmpty(singleIdsListCustom)&&CollectionUtils.isEmpty(trueOrFalseIdsListCustom)&&CollectionUtils.isEmpty(fillIdsListCustom))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        BigDecimal singleChoiceQuestionSumScore=new BigDecimal(0);

        BigDecimal singleChoiceQuestionScore =null;
        if(!CollectionUtils.isEmpty(singleIdsListCustom)){
            singleChoiceQuestionScore = testPaperDto.getSingleChoiceQuestionScore();
            if(singleChoiceQuestionScore==null||singleChoiceQuestionScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            //添加的题目必须存在（课程id与试卷课程id一致）
            for(String id:singleIdsListCustom){
                SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(id);
                if(singleChoiceQuestion==null)
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
                if(!StringUtils.equals(testPaperDtoCourseId,singleChoiceQuestion.getCourseId()))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_SAME,null);
                if(!singleChoiceQuestion.getIsChecked())
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_CHECKED,null);

            }
            singleChoiceQuestionSumScore=new BigDecimal(singleIdsListCustom.size()).multiply(singleChoiceQuestionScore);
        }

        BigDecimal trueOrFalseQuestionSumScore=new BigDecimal(0);

        BigDecimal trueOrFalseQuestionScore=null;
        if(!CollectionUtils.isEmpty(trueOrFalseIdsListCustom)){
            trueOrFalseQuestionScore = testPaperDto.getTrueOrFalseQuestionScore();
            if(trueOrFalseQuestionScore==null||trueOrFalseQuestionScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            //添加的题目必须存在（课程id与试卷课程id一致）
            for(String id:trueOrFalseIdsListCustom){
                TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
                if(trueOrFalseQuestion==null)
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
                if(!StringUtils.equals(testPaperDtoCourseId,trueOrFalseQuestion.getCourseId()))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_SAME,null);
                if(!trueOrFalseQuestion.getIsChecked())
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_CHECKED,null);
            }
            trueOrFalseQuestionSumScore=new BigDecimal(trueOrFalseIdsListCustom.size()).multiply(trueOrFalseQuestionScore);
        }

        List<TestpaperQuestionRelation> fillInBlankQuestionList=new ArrayList<>();
        BigDecimal fillInblankQuestionSumScore=new BigDecimal(0);

        if(!CollectionUtils.isEmpty(fillIdsListCustom)){
            BigDecimal fillInBlankQuestionScore = testPaperDto.getFillInBlankQuestionScore();
            if(fillInBlankQuestionScore==null||fillInBlankQuestionScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            Integer blankNum=0;

            //添加的题目必须存在（课程id与试卷课程id一致）
            for(String id:fillIdsListCustom){
                System.out.println(id);
                FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(id);
                if(fillInBlankQuestion==null)
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
                if(!StringUtils.equals(testPaperDtoCourseId,fillInBlankQuestion.getCourseId()))
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_SAME,null);
                if(!fillInBlankQuestion.getIsChecked())
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_CHECKED,null);

                TestpaperQuestionRelation testpaperQuestionRelation=new TestpaperQuestionRelation();
                testpaperQuestionRelation.setQuestionId(id);
                testpaperQuestionRelation.setQuestionScore(fillInBlankQuestionScore.multiply(new BigDecimal(fillInBlankQuestion.getBlankNum())));
                fillInBlankQuestionList.add(testpaperQuestionRelation);

                blankNum+=fillInBlankQuestion.getBlankNum();
            }

            fillInblankQuestionSumScore=new BigDecimal(blankNum).multiply(fillInBlankQuestionScore);
        }

        //创建题目教师id不能为空
        String CreatedTeacherId = testPaperDto.getCreatedTeacherId();
        if(StringUtils.isBlank(CreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //添加的试卷所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(testPaperDtoCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //添加的试卷的学年必须存在
        SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(testPaperDtoSchoolYearId);
        if(schoolYear==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_NOT_EXIST,null);

        //添加的试卷的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(CreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //试卷总分与题目分数总分一样
        BigDecimal questionSumScore=singleChoiceQuestionSumScore.add(trueOrFalseQuestionSumScore);
        questionSumScore=questionSumScore.add(fillInblankQuestionSumScore);
        System.out.println("试卷："+testPaperDtoScore);
        System.out.println("题目："+questionSumScore);
        if(testPaperDtoScore.compareTo(questionSumScore)!=0)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_SAME,null);

        //添加试卷
        String testPaperId = UUIDBuild.getUUID();
        testPaperDto.setId(testPaperId);
        testPaperDto.setName(testPaperDtoName);
        testPaperDto.setCreatedTime(new Date());
        testPaperDto.setUpdatedTime(new Date());

        //添加试卷
        testPaperMapper.insert(testPaperDto);

        //添加试卷题目关系
        saveTestPaperRelation(testPaperId,DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE,singleIdsListCustom,singleChoiceQuestionScore);

        saveTestPaperRelation(testPaperId,DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE,trueOrFalseIdsListCustom,trueOrFalseQuestionScore);

        for(int i=0;i<fillInBlankQuestionList.size();i++){
            TestpaperQuestionRelation testpaperQuestionRelation=fillInBlankQuestionList.get(i);
            String relationId = UUIDBuild.getUUID();
            testpaperQuestionRelation.setId(relationId);
            testpaperQuestionRelation.setTestPaperId(testPaperId);
            testpaperQuestionRelation.setQuestionType(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
            testpaperQuestionRelation.setQuestionOrder(i+1);
            testpaperQuestionRelation.setCreatedTime(new Date());
            testpaperQuestionRelation.setUpdatedTime(new Date());

            System.out.println(testpaperQuestionRelation.getQuestionId()+"-----"+(i+1));

            testpaperQuestionRelationMapper.insert(testpaperQuestionRelation);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateTestPaper(String id, TestPaperDto testPaperDto) throws Exception {
        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_ID_NOT_NULL,null);

        //名字不能为空
        String testPaperDtoName = testPaperDto.getName();
        if(StringUtils.isBlank(testPaperDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NAME_NOT_NULL,null);

        //名字预处理
        testPaperDtoName=testPaperDtoName.trim();

        //学年id不能为空
        String testPaperDtoSchoolYearId=testPaperDto.getSchoolYearId();
        if(StringUtils.isBlank(testPaperDtoSchoolYearId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_ID_NOT_NULL,null);

        //学期不能为空
        Integer term = testPaperDto.getTerm();
        if(term==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TERM_NOT_NULL,null);

        //分数不能为空
        BigDecimal testPaperDtoScore =testPaperDto.getScore();
        if(testPaperDtoScore==null||testPaperDtoScore.equals(0))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

        //教师id不能为空
        String createdTeacherId=testPaperDto.getCreatedTeacherId();
        if(StringUtils.isBlank(createdTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_ID_NOT_NULL,null);

        Integer singleChoiceQuestionNum = testPaperDto.getSingleChoiceQuestionNum();
        Integer trueOrFalseQuestionNum = testPaperDto.getTrueOrFalseQuestionNum();
        Integer fillInBlankQuestionNum = testPaperDto.getFillInBlankQuestionNum();

        if(singleChoiceQuestionNum==null&&trueOrFalseQuestionNum==null&&fillInBlankQuestionNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NUM_NOT_NULL,null);

        //id对应试卷必须存在
        TestPaper testPaperDb = testPaperMapper.selectByPrimaryKey(id);
        if(testPaperDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NOT_EXIST,null);

        //只有该试卷创建教师才允许修改试卷
        if(!StringUtils.equals(testPaperDb.getCreatedTeacherId(),createdTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TESTPAPER_NOT_AUTHO,null);

        //修改的试卷的学年必须存在
        SchoolYear schoolYear = schoolYearMapper.selectByPrimaryKey(testPaperDtoSchoolYearId);
        if(schoolYear==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCHOOLYEAR_NOT_EXIST,null);

        //题目分数不能为空
        List<TestpaperQuestionRelation> singleChoiceRelationList=null;
        BigDecimal singleChoiceScore=testPaperDto.getSingleChoiceQuestionScore();
        if(singleChoiceQuestionNum!=null){
            if(singleChoiceScore==null||singleChoiceScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria relationCriteria = testpaperQuestionRelationExample.createCriteria();
            relationCriteria.andTestPaperIdEqualTo(id);
            relationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));
            singleChoiceRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);
        }

        List<TestpaperQuestionRelation> trueOrFalseRelationList=null;
        BigDecimal trueOrFalseScore=testPaperDto.getTrueOrFalseQuestionScore();
        if(trueOrFalseQuestionNum!=null){
            if(trueOrFalseScore==null||trueOrFalseScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria relationCriteria = testpaperQuestionRelationExample.createCriteria();
            relationCriteria.andTestPaperIdEqualTo(id);
            relationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));
            trueOrFalseRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);
        }

        List<TestpaperQuestionRelation> fillInBlankRelationList=null;
        BigDecimal fillInBlankScore=testPaperDto.getFillInBlankQuestionScore();
        if(fillInBlankQuestionNum!=null){
            if(fillInBlankScore==null||fillInBlankScore.equals(0))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_NULL,null);

            TestpaperQuestionRelationExample testpaperQuestionRelationExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria relationCriteria = testpaperQuestionRelationExample.createCriteria();
            relationCriteria.andTestPaperIdEqualTo(id);
            relationCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
            fillInBlankRelationList = testpaperQuestionRelationMapper.selectByExample(testpaperQuestionRelationExample);
        }

        //试卷总分与题目分数总分一样
        BigDecimal questionScore=new BigDecimal(0);
        if(!CollectionUtils.isEmpty(singleChoiceRelationList))
            questionScore=questionScore.add(new BigDecimal(singleChoiceRelationList.size()).multiply(singleChoiceScore));

        if(!CollectionUtils.isEmpty(trueOrFalseRelationList))
            questionScore=questionScore.add(new BigDecimal(trueOrFalseRelationList.size()).multiply(trueOrFalseScore));

        if(!CollectionUtils.isEmpty(fillInBlankRelationList)){
            Integer blankNum=0;
            for(TestpaperQuestionRelation relation:fillInBlankRelationList){
                FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(relation.getQuestionId());
                blankNum+=fillInBlankQuestion.getBlankNum();
            }
            questionScore=questionScore.add(new BigDecimal(blankNum).multiply(fillInBlankScore));
        }

        if(testPaperDtoScore.compareTo(questionScore)!=0)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_SCORE_NOT_SAME,null);

        testPaperDb.setName(testPaperDtoName);
        testPaperDb.setScore(testPaperDtoScore);
        testPaperDb.setSchoolYearId(testPaperDtoSchoolYearId);
        testPaperDb.setTerm(term);
        testPaperDto.setUpdatedTime(new Date());

        //修改试卷
        testPaperMapper.updateByPrimaryKey(testPaperDb);

        //修改试卷题目分数
        if(!CollectionUtils.isEmpty(singleChoiceRelationList)){
            for(TestpaperQuestionRelation relation:singleChoiceRelationList){
                relation.setQuestionScore(singleChoiceScore);
                testpaperQuestionRelationMapper.updateByPrimaryKey(relation);
            }
        }

        if(!CollectionUtils.isEmpty(fillInBlankRelationList)){
            for(TestpaperQuestionRelation relation:fillInBlankRelationList){
                FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(relation.getQuestionId());
                relation.setQuestionScore(fillInBlankScore.multiply(new BigDecimal(fillInBlankQuestion.getBlankNum())));

                testpaperQuestionRelationMapper.updateByPrimaryKey(relation);
            }
        }

        if(!CollectionUtils.isEmpty(trueOrFalseRelationList)){
            for(TestpaperQuestionRelation relation:trueOrFalseRelationList){
                relation.setQuestionScore(trueOrFalseScore);
                testpaperQuestionRelationMapper.updateByPrimaryKey(relation);
            }
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    private void saveTestPaperRelation(String testPaperId,String questionType,List<String>questionIds,BigDecimal questionScore){
        for(int i=0;i<questionIds.size();i++){
            TestpaperQuestionRelation testpaperQuestionRelation=new TestpaperQuestionRelation();
            String relationId = UUIDBuild.getUUID();
            testpaperQuestionRelation.setId(relationId);
            testpaperQuestionRelation.setTestPaperId(testPaperId);
            testpaperQuestionRelation.setQuestionId(questionIds.get(i));
            testpaperQuestionRelation.setQuestionType(new Integer(questionType));
            testpaperQuestionRelation.setQuestionScore(questionScore);
            testpaperQuestionRelation.setQuestionOrder(i+1);
            testpaperQuestionRelation.setCreatedTime(new Date());
            testpaperQuestionRelation.setUpdatedTime(new Date());

            System.out.println(questionIds.get(i)+"-----"+(i+1));

            testpaperQuestionRelationMapper.insert(testpaperQuestionRelation);
        }
    }

    @Override
    public TestPaperDto getTestPaperAndQuestionsByIdForLoginStudent(String id,String examStudentId) throws Exception {

        //查询试卷信息
        TestPaper testPaper = testPaperMapper.selectByPrimaryKey(id);

        TestPaperDto testPaperDto=null;

        if(testPaper!=null){
            //构造testPaperDto
            testPaperDto=new TestPaperDto();
            BeanUtils.copyProperties(testPaper,testPaperDto);

            //试卷中单选题信息
            List<TestPaperSingleChoiceQuestion> singleChoiceQuestionList=new ArrayList<>();

            //查询该试卷单选题信息
            TestpaperQuestionRelationExample testPaperSingleChoiceQuestionExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria singleChoiceQuestionCriteria = testPaperSingleChoiceQuestionExample.createCriteria();
            singleChoiceQuestionCriteria.andTestPaperIdEqualTo(id);
            singleChoiceQuestionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));
            testPaperSingleChoiceQuestionExample.setOrderByClause("question_order");
            List<TestpaperQuestionRelation> testPaperSingleChoiceQuestionList = testpaperQuestionRelationMapper.selectByExample(testPaperSingleChoiceQuestionExample);

            if(!CollectionUtils.isEmpty(testPaperSingleChoiceQuestionList)){
                for(int i=0;i<testPaperSingleChoiceQuestionList.size();i++){
                    TestpaperQuestionRelation relation=testPaperSingleChoiceQuestionList.get(i);

                    //构造试卷单选题信息
                    TestPaperSingleChoiceQuestion testPaperSingleChoiceQuestion=new TestPaperSingleChoiceQuestion();
                    BeanUtils.copyProperties(relation,testPaperSingleChoiceQuestion);

                    //查询某一条单选题信息
                    SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(relation.getQuestionId());
                    if(singleChoiceQuestion!=null){
                        testPaperSingleChoiceQuestion.setQuestionContent(singleChoiceQuestion.getContent());
                        testPaperSingleChoiceQuestion.setOptionA(singleChoiceQuestion.getOptionA());
                        testPaperSingleChoiceQuestion.setOptionB(singleChoiceQuestion.getOptionB());
                        testPaperSingleChoiceQuestion.setOptionC(singleChoiceQuestion.getOptionC());
                        testPaperSingleChoiceQuestion.setOptionD(singleChoiceQuestion.getOptionD());
                    }
                    singleChoiceQuestionList.add(testPaperSingleChoiceQuestion);
                }
            }

            //为题目打乱顺序
            List<Integer> singleChoiceQuestionOrder=new ArrayList<>();
            //如果缓存中有直接获取顺序
            String singleChoiceQuestionJson = jedisClient.hget(examStudentId, REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER);

            if(!StringUtils.isBlank(singleChoiceQuestionJson)){
               singleChoiceQuestionOrder= JsonUtils.jsonToList(singleChoiceQuestionJson,Integer.class);
            }else{
                singleChoiceQuestionOrder=RandomUtils.getDiffNO(singleChoiceQuestionList.size());

                //把顺序添加至缓存中
                jedisClient.hset(examStudentId,REDIS_KEY_SINGLE_CHOICE_QUESTION_ORDER,JsonUtils.objectToJson(singleChoiceQuestionOrder));
            }

            List<TestPaperSingleChoiceQuestion> singleChoiceQuestionListCustom=new ArrayList<>();

            for(Integer order:singleChoiceQuestionOrder){
                singleChoiceQuestionListCustom.add(singleChoiceQuestionList.get(order-1));
            }

            testPaperDto.setSingleChoiceQuestions(singleChoiceQuestionListCustom);



            //试卷中判断题信息
            List<TestPaperTrueOrFalseQuestion> trueOrFalseQuestionList=new ArrayList<>();

            //查询该试卷判断题信息
            TestpaperQuestionRelationExample testPaperTrueOrFalseQuestionExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria trueOrFalseQuestionCriteria = testPaperTrueOrFalseQuestionExample.createCriteria();
            trueOrFalseQuestionCriteria.andTestPaperIdEqualTo(id);
            trueOrFalseQuestionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));
            testPaperTrueOrFalseQuestionExample.setOrderByClause("question_order");
            List<TestpaperQuestionRelation> testPaperTrueOrFalseQuestionList = testpaperQuestionRelationMapper.selectByExample(testPaperTrueOrFalseQuestionExample);


            if(!CollectionUtils.isEmpty(testPaperTrueOrFalseQuestionList)){
                for(int i=0;i<testPaperTrueOrFalseQuestionList.size();i++){
                    TestpaperQuestionRelation relation=testPaperTrueOrFalseQuestionList.get(i);

                    //构造试卷判断题信息
                    TestPaperTrueOrFalseQuestion testPaperTrueOrFalseQuestion=new TestPaperTrueOrFalseQuestion();
                    BeanUtils.copyProperties(relation,testPaperTrueOrFalseQuestion);

                    //查询某一条判断题信息
                    TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(relation.getQuestionId());
                    if(trueOrFalseQuestion!=null){
                        testPaperTrueOrFalseQuestion.setQuestionContent(trueOrFalseQuestion.getContent());
                    }
                    trueOrFalseQuestionList.add(testPaperTrueOrFalseQuestion);
                }
            }


            //为题目打乱顺序
            List<Integer> trueOrFalseQuestionOrder=new ArrayList<>();
            //如果缓存中有直接获取顺序
            String trueOrFalseQuestionJson = jedisClient.hget(examStudentId, REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER);

            if(!StringUtils.isBlank(trueOrFalseQuestionJson)){
                trueOrFalseQuestionOrder= JsonUtils.jsonToList(trueOrFalseQuestionJson,Integer.class);
            }else{
                trueOrFalseQuestionOrder=RandomUtils.getDiffNO(trueOrFalseQuestionList.size());

                //把顺序添加至缓存中
                jedisClient.hset(examStudentId,REDIS_KEY_TRUE_OR_FALSE_QUESTION_ORDER,JsonUtils.objectToJson(trueOrFalseQuestionOrder));
            }

            List<TestPaperTrueOrFalseQuestion> trueOrFalseQuestionListCustom=new ArrayList<>();

            for(Integer order:trueOrFalseQuestionOrder){
                trueOrFalseQuestionListCustom.add(trueOrFalseQuestionList.get(order-1));
            }

            testPaperDto.setTrueOrFalseQuestions(trueOrFalseQuestionListCustom);




            //试卷中填空题信息
            List<TestPaperFillInBlankQuestion> fillInBlankQuestionList=new ArrayList<>();

            //查询该试卷填空题信息
            TestpaperQuestionRelationExample testPaperFillInBlankQuestionExample=new TestpaperQuestionRelationExample();
            TestpaperQuestionRelationExample.Criteria fillInBlankQuestionCriteria = testPaperFillInBlankQuestionExample.createCriteria();
            fillInBlankQuestionCriteria.andTestPaperIdEqualTo(id);
            fillInBlankQuestionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
            testPaperFillInBlankQuestionExample.setOrderByClause("question_order");
            List<TestpaperQuestionRelation> testPaperFillInBlankQuestionList = testpaperQuestionRelationMapper.selectByExample(testPaperFillInBlankQuestionExample);


            if(!CollectionUtils.isEmpty(testPaperFillInBlankQuestionList)){
                for(int i=0;i<testPaperFillInBlankQuestionList.size();i++){
                    TestpaperQuestionRelation relation=testPaperFillInBlankQuestionList.get(i);

                    //构造试卷填空题信息
                    TestPaperFillInBlankQuestion testPaperFillInBlankQuestion=new TestPaperFillInBlankQuestion();
                    BeanUtils.copyProperties(relation,testPaperFillInBlankQuestion);

                    //查询某一条填空题信息
                    FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(relation.getQuestionId());
                    if(fillInBlankQuestion!=null){
                        testPaperFillInBlankQuestion.setQuestionContent(fillInBlankQuestion.getContent());
                        testPaperFillInBlankQuestion.setBlankNum(fillInBlankQuestion.getBlankNum());
                    }
                    fillInBlankQuestionList.add(testPaperFillInBlankQuestion);
                }
            }


            //为题目打乱顺序
            List<Integer> fillInBlankQuestionOrder=new ArrayList<>();
            //如果缓存中有直接获取顺序
            String fillInBlankQuestionJson = jedisClient.hget(examStudentId, REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER);

            if(!StringUtils.isBlank(fillInBlankQuestionJson)){
                fillInBlankQuestionOrder= JsonUtils.jsonToList(fillInBlankQuestionJson,Integer.class);
            }else{
                fillInBlankQuestionOrder=RandomUtils.getDiffNO(fillInBlankQuestionList.size());

                //把顺序添加至缓存中
                jedisClient.hset(examStudentId,REDIS_KEY_FILL_IN_BLANK_QUESTION_ORDER,JsonUtils.objectToJson(fillInBlankQuestionOrder));
            }

            List<TestPaperFillInBlankQuestion> fillInBlankQuestionListCustom=new ArrayList<>();

            for(Integer order:fillInBlankQuestionOrder){
                fillInBlankQuestionListCustom.add(fillInBlankQuestionList.get(order-1));
            }

            testPaperDto.setFillInBlankQuestions(fillInBlankQuestionListCustom);


            //加载学生试卷中题目答案

            //获取单选题答案
            String singleChoiceQuestionAnswer = jedisClient.hget(examStudentId, REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER);
            if(!StringUtils.isBlank(singleChoiceQuestionAnswer))
                testPaperDto.setSingleChoiceQuestionAnswer(JsonUtils.jsonToMap(singleChoiceQuestionAnswer,Integer.class,String.class));

            //获取判断题答案
            String trueOrFalseQuestionAnswer = jedisClient.hget(examStudentId, REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER);
            if(!StringUtils.isBlank(trueOrFalseQuestionAnswer))
                testPaperDto.setTrueOrFalseQuestionAnswer(JsonUtils.jsonToMap(trueOrFalseQuestionAnswer,Integer.class,String.class));

            //获取单选题答案
            String fillInBlankQuestionAnswer = jedisClient.hget(examStudentId, REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER);
            if(!StringUtils.isBlank(fillInBlankQuestionAnswer))
                testPaperDto.setFillInBlankQuestionAnswer(JsonUtils.jsonToMap(fillInBlankQuestionAnswer,Integer.class,String.class));
        }


        return testPaperDto;
    }
}
