package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;
import cn.examsystem.rest.service.TrueOrFalseQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 判断题业务层实现
 */
@Service
public class TrueOrFalseQuestionImpl implements TrueOrFalseQuestionService {

    @Autowired
    private TrueOrFalseQuestionMapperCustom trueOrFalseQuestionMapperCustom;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
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
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;

    @Override
    public TrueOrFalseQuestionDto getTrueOrFalseQuestion(String id) throws Exception {

        TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
        TrueOrFalseQuestionDto trueOrFalseQuestionDto=null;
        if(trueOrFalseQuestion!=null){
            trueOrFalseQuestionDto=new TrueOrFalseQuestionDto();

            //构造trueOrFalseQuestionDto
            BeanUtils.copyProperties(trueOrFalseQuestion,trueOrFalseQuestionDto);

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(trueOrFalseQuestion.getCourseId());
            if(course!=null){
                trueOrFalseQuestionDto.setCourseName(course.getName());
            }

            //查询创建教师信息
            Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(trueOrFalseQuestion.getCreatedTeacherId());
            if(createdTeacher!=null){
                trueOrFalseQuestionDto.setCreatedTeacher(createdTeacher);
            }

            //查询难度信息
            DictInfoExample dictInfoExample=new DictInfoExample();
            DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
            dictInfoCriteria.andCodeEqualTo(String.valueOf(trueOrFalseQuestion.getDifficulty()));
            dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
            List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
            if(!CollectionUtils.isEmpty(dictInfoList)){
                trueOrFalseQuestionDto.setDifficultyName(dictInfoList.get(0).getName());
            }
        }
        return trueOrFalseQuestionDto;
    }

    @Override
    public List<TrueOrFalseQuestionDto> listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo) throws Exception {
        return trueOrFalseQuestionMapperCustom.listTrueOrFalseQuestion(trueOrFalseQuestionVo);
    }

    @Override
    public ResultInfo saveTrueOrFalseQuestion(TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception {

        //内容不能为空
        String questionContent = trueOrFalseQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //答案不能为空
        Boolean questionAnswer = trueOrFalseQuestionDto.getAnswer();
        if(questionAnswer==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);


        //课程id不能为空
        String questionCourseId = trueOrFalseQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = trueOrFalseQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = trueOrFalseQuestionDto.getCreatedTeacherId();
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
        trueOrFalseQuestionDto.setId(questionId);
        trueOrFalseQuestionDto.setType(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));

        //添加审核状态
        trueOrFalseQuestionDto.setIsChecked(false);

        trueOrFalseQuestionDto.setCreatedTime(new Date());
        trueOrFalseQuestionDto.setUpdatedTime(new Date());

        //添加题目
        trueOrFalseQuestionMapper.insert(trueOrFalseQuestionDto);
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateTrueOrFalseQuestion(String id, TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        //内容不能为空
        String questionContent = trueOrFalseQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //答案不能为空
        Boolean questionAnswer = trueOrFalseQuestionDto.getAnswer();
        if(questionAnswer==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

        //课程id不能为空
        String questionCourseId = trueOrFalseQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = trueOrFalseQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = trueOrFalseQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //id对应题目必须存在
        TrueOrFalseQuestion trueOrFalseQuestionDb = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
        if(trueOrFalseQuestionDb==null)
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
        Boolean isChecked = trueOrFalseQuestionDto.getIsChecked();
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
        if(!StringUtils.equals(questionCourseId,trueOrFalseQuestionDb.getCourseId())){
            //删除该题目对应知识点
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationExampleCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationExampleCriteria.andQuestionIdEqualTo(id);
            questionKnowledgepointRelationMapper.deleteByExample(questionKnowledgepointRelationExample);
        }

        trueOrFalseQuestionDb.setContent(questionContent);
        trueOrFalseQuestionDb.setAnswer(questionAnswer);
        trueOrFalseQuestionDb.setCourseId(questionCourseId);
        trueOrFalseQuestionDb.setDifficulty(questionDifficultyId);
        trueOrFalseQuestionDb.setUpdatedTime(new Date());

        //更新题目
        trueOrFalseQuestionMapper.updateByPrimaryKey(trueOrFalseQuestionDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
