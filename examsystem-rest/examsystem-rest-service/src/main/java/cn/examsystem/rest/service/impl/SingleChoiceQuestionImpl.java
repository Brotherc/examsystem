package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.SingleChoiceQuestionDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.SingleChoiceQuestionVo;
import cn.examsystem.rest.service.SingleChoiceQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 单选题业务层实现
 */
@Service
public class SingleChoiceQuestionImpl implements SingleChoiceQuestionService {

    @Autowired
    private SingleChoiceQuestionMapperCustom singleChoiceQuestionMapperCustom;
    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private DictInfoMapper dictInfoMapper;
    @Autowired
    private CourseTeacherRelationMapper courseTeacherRelationMapper;
    @Autowired
    private QuestionKnowledgepointRelationMapper questionKnowledgepointRelationMapper;
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Value("${MESSAGE_QUESTION_IS_CHECKED}")
    private String MESSAGE_QUESTION_IS_CHECKED;
    @Value("${MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST}")
    private String MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST;
    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;
    @Value("${MESSAGE_QUESTION_CONTENT_NOT_NULL}")
    private String MESSAGE_QUESTION_CONTENT_NOT_NULL;
    @Value("${MESSAGE_QUESTION_OPTION_A_NOT_NULL}")
    private String MESSAGE_QUESTION_OPTION_A_NOT_NULL;
    @Value("${MESSAGE_QUESTION_OPTION_B_NOT_NULL}")
    private String MESSAGE_QUESTION_OPTION_B_NOT_NULL;
    @Value("${MESSAGE_QUESTION_OPTION_C_NOT_NULL}")
    private String MESSAGE_QUESTION_OPTION_C_NOT_NULL;
    @Value("${MESSAGE_QUESTION_OPTION_D_NOT_NULL}")
    private String MESSAGE_QUESTION_OPTION_D_NOT_NULL;
    @Value("${MESSAGE_QUESTION_ANSWER_NOT_NULL}")
    private String MESSAGE_QUESTION_ANSWER_NOT_NULL;
    @Value("${MESSAGE_DIFFICULTY_ID_NOT_NULL}")
    private String MESSAGE_DIFFICULTY_ID_NOT_NULL;
    @Value("${MESSAGE_DIFFICULTY_NOT_EXIST}")
    private String MESSAGE_DIFFICULTY_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_CREATED_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_CREATED_TEACHER_NOT_EXIST}")
    private String MESSAGE_CREATED_TEACHER_NOT_EXIST;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Value("${DICTTYPE_DIFFICULTY_ID}")
    private String DICTTYPE_DIFFICULTY_ID;
    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;

    @Override
    public SingleChoiceQuestionDto getSingleChoiceQuestion(String id) throws Exception {

        SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(id);
        SingleChoiceQuestionDto singleChoiceQuestionDto=null;
        if(singleChoiceQuestion!=null){
            singleChoiceQuestionDto=new SingleChoiceQuestionDto();

            //构造singleChoiceQuestionDto
            BeanUtils.copyProperties(singleChoiceQuestion,singleChoiceQuestionDto);

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(singleChoiceQuestion.getCourseId());
            if(course!=null){
                singleChoiceQuestionDto.setCourseName(course.getName());
            }

            //查询创建教师信息
            Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(singleChoiceQuestion.getCreatedTeacherId());
            if(createdTeacher!=null){
                singleChoiceQuestionDto.setCreatedTeacher(createdTeacher);
            }

            //查询难度信息
            DictInfoExample dictInfoExample=new DictInfoExample();
            DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
            dictInfoCriteria.andCodeEqualTo(String.valueOf(singleChoiceQuestion.getDifficulty()));
            dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
            List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
            if(!CollectionUtils.isEmpty(dictInfoList)){
                singleChoiceQuestionDto.setDifficultyName(dictInfoList.get(0).getName());
            }

            //查询知识点信息
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationCriteria.andQuestionIdEqualTo(id);
            List<QuestionKnowledgepointRelation> questionKnowledgepointRelationList = questionKnowledgepointRelationMapper.selectByExample(questionKnowledgepointRelationExample);
            if(!CollectionUtils.isEmpty(questionKnowledgepointRelationList)){
                List<String> knowledgePointIdList=new ArrayList<>();
                for(QuestionKnowledgepointRelation relation:questionKnowledgepointRelationList){
                    knowledgePointIdList.add(relation.getKnowledgePointId());
                }
                KnowledgePointExample knowledgePointExample=new KnowledgePointExample();
                KnowledgePointExample.Criteria knowledgePointCriteria = knowledgePointExample.createCriteria();
                knowledgePointCriteria.andIdIn(knowledgePointIdList);
                List<KnowledgePoint> knowledgePointList = knowledgePointMapper.selectByExample(knowledgePointExample);

                List<String> knowledgePointNameList=new ArrayList<>();
                for(KnowledgePoint knowledgePoint:knowledgePointList){
                    knowledgePointNameList.add(knowledgePoint.getName());
                }
                singleChoiceQuestionDto.setKnowledgePoints(knowledgePointNameList);
            }
        }
        return singleChoiceQuestionDto;
    }

    @Override
    public List<SingleChoiceQuestionDto> listSingleChoiceQuestion(SingleChoiceQuestionVo singleChoiceQuestionVo) throws Exception {
        return singleChoiceQuestionMapperCustom.listSingleChoiceQuestion(singleChoiceQuestionVo);
    }

    @Override
    public ResultInfo saveSingleChoiceQuestion(SingleChoiceQuestionDto singleChoiceQuestionDto) throws Exception {

        //内容不能为空
        String questionContent = singleChoiceQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //选项A不能为空
        String optionA = singleChoiceQuestionDto.getOptionA();
        if(StringUtils.isBlank(optionA))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_A_NOT_NULL,null);

        //选项A预处理
        optionA=optionA.trim();

        //选项B不能为空
        String optionB = singleChoiceQuestionDto.getOptionB();
        if(StringUtils.isBlank(optionB))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_B_NOT_NULL,null);

        //选项B预处理
        optionB=optionB.trim();

        //选项C不能为空
        String optionC = singleChoiceQuestionDto.getOptionC();
        if(StringUtils.isBlank(optionC))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_C_NOT_NULL,null);

        //选项C预处理
        optionC=optionC.trim();

        //选项D不能为空
        String optionD = singleChoiceQuestionDto.getOptionD();
        if(StringUtils.isBlank(optionD))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_D_NOT_NULL,null);

        //选项D预处理
        optionD=optionD.trim();

        //答案不能为空
        String questionAnswer = singleChoiceQuestionDto.getAnswer();
        if(StringUtils.isBlank(questionAnswer))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

        //答案预处理
        questionAnswer=questionAnswer.trim();

        //课程id不能为空
        String questionCourseId = singleChoiceQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = singleChoiceQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = singleChoiceQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //添加的题目所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(questionCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //添加的题目的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(questionCreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //添加的题目所属的难度必须存在
        DictInfoExample dictInfoExample=new DictInfoExample();
        DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
        dictInfoCriteria.andCodeEqualTo(String.valueOf(questionDifficultyId));
        dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
        List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }

        //该题目对应课程必须在教师任课范围内
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationExampleCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationExampleCriteria.andCourseIdEqualTo(questionCourseId);
        courseTeacherRelationExampleCriteria.andTeacherIdEqualTo(questionCreatedTeacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(CollectionUtils.isEmpty(courseTeacherRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST,null);

        //补全uuid
        String questionId = UUIDBuild.getUUID();
        singleChoiceQuestionDto.setId(questionId);
        singleChoiceQuestionDto.setType(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));


        //添加审核状态
        singleChoiceQuestionDto.setIsChecked(false);

        singleChoiceQuestionDto.setCreatedTime(new Date());
        singleChoiceQuestionDto.setUpdatedTime(new Date());

        //添加题目
        singleChoiceQuestionMapper.insert(singleChoiceQuestionDto);
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateSingleChoiceQuestion(String id, SingleChoiceQuestionDto singleChoiceQuestionDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        //内容不能为空
        String questionContent = singleChoiceQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //选项A不能为空
        String optionA = singleChoiceQuestionDto.getOptionA();
        if(StringUtils.isBlank(optionA))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_A_NOT_NULL,null);

        //选项A预处理
        optionA=optionA.trim();

        //选项B不能为空
        String optionB = singleChoiceQuestionDto.getOptionB();
        if(StringUtils.isBlank(optionB))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_B_NOT_NULL,null);

        //选项B预处理
        optionB=optionB.trim();

        //选项C不能为空
        String optionC = singleChoiceQuestionDto.getOptionC();
        if(StringUtils.isBlank(optionC))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_C_NOT_NULL,null);

        //选项C预处理
        optionC=optionC.trim();

        //选项D不能为空
        String optionD = singleChoiceQuestionDto.getOptionD();
        if(StringUtils.isBlank(optionD))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_OPTION_D_NOT_NULL,null);

        //选项D预处理
        optionD=optionD.trim();

        //答案不能为空
        String questionAnswer = singleChoiceQuestionDto.getAnswer();
        if(StringUtils.isBlank(questionAnswer))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

        //答案预处理
        questionAnswer=questionAnswer.trim();

        //课程id不能为空
        String questionCourseId = singleChoiceQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = singleChoiceQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = singleChoiceQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //id对应题目必须存在
        SingleChoiceQuestion singleChoiceQuestionDb = singleChoiceQuestionMapper.selectByPrimaryKey(id);
        if(singleChoiceQuestionDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

        //修改的题目所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(questionCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //修改的题目的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(questionCreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //修改的题目所属的难度必须存在
        DictInfoExample dictInfoExample=new DictInfoExample();
        DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
        dictInfoCriteria.andCodeEqualTo(String.valueOf(questionDifficultyId));
        dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
        List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }


        //只有审核待通过的题目才允许修改
        Boolean isChecked = singleChoiceQuestionDto.getIsChecked();
        if(isChecked)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_IS_CHECKED,null);

        //该题目对应课程必须在教师任课范围内
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationExampleCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationExampleCriteria.andCourseIdEqualTo(questionCourseId);
        courseTeacherRelationExampleCriteria.andTeacherIdEqualTo(questionCreatedTeacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(CollectionUtils.isEmpty(courseTeacherRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST,null);

        //如果修改了课程id,则该题目对应知识点要删除
        if(!StringUtils.equals(questionCourseId,singleChoiceQuestionDb.getCourseId())){
            //删除该题目对应知识点
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationExampleCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationExampleCriteria.andQuestionIdEqualTo(id);
            questionKnowledgepointRelationMapper.deleteByExample(questionKnowledgepointRelationExample);
        }

        singleChoiceQuestionDb.setContent(questionContent);
        singleChoiceQuestionDb.setOptionA(optionA);
        singleChoiceQuestionDb.setOptionB(optionB);
        singleChoiceQuestionDb.setOptionC(optionC);
        singleChoiceQuestionDb.setOptionD(optionD);
        singleChoiceQuestionDb.setAnswer(questionAnswer);
        singleChoiceQuestionDb.setCourseId(questionCourseId);
        singleChoiceQuestionDb.setDifficulty(questionDifficultyId);
        singleChoiceQuestionDb.setUpdatedTime(new Date());

        //更新题目
        singleChoiceQuestionMapper.updateByPrimaryKey(singleChoiceQuestionDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
