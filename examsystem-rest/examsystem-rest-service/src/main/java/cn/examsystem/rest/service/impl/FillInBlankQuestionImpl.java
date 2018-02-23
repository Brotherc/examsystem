package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.FillInBlankQuestionDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.FillInBlankQuestionVo;
import cn.examsystem.rest.service.FillInBlankQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2018/1/30.
 * 填空题业务层实现
 */
@Service
public class FillInBlankQuestionImpl implements FillInBlankQuestionService {

    @Value("${DICTTYPE_DIFFICULTY_ID}")
    private String DICTTYPE_DIFFICULTY_ID;
    @Value("${DICTTYPE_MATCHER_ID}")
    private String DICTTYPE_MATCHER_ID;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Value("${MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST}")
    private String MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST;
    @Value("${MESSAGE_DIFFICULTY_NOT_EXIST}")
    private String MESSAGE_DIFFICULTY_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_NOT_EXIST}")
    private String MESSAGE_CREATED_TEACHER_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_CREATED_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;
    @Value("${MESSAGE_DIFFICULTY_ID_NOT_NULL}")
    private String MESSAGE_DIFFICULTY_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_BLANKNUM_NOT_NULL}")
    private String MESSAGE_QUESTION_BLANKNUM_NOT_NULL;
    @Value("${MESSAGE_QUESTION_CONTENT_NOT_NULL}")
    private String MESSAGE_QUESTION_CONTENT_NOT_NULL;
    @Value("${MESSAGE_QUESTION_ANSWER_NOT_NULL}")
    private String MESSAGE_QUESTION_ANSWER_NOT_NULL;
    @Value("${MESSAGE_QUESTION_MATCHER_NOT_EXIST}")
    private String MESSAGE_QUESTION_MATCHER_NOT_EXIST;

    @Value("${MESSAGE_QUESTION_IS_CHECKED}")
    private String MESSAGE_QUESTION_IS_CHECKED;
    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;

    @Autowired
    private FillInBlankQuestionMapper fillInBlankQuestionMapper;
    @Autowired
    private FillInBlankQuestionMapperCustom fillInBlankQuestionMapperCustom;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private DictInfoMapper dictInfoMapper;
    @Autowired
    private QuestionMatcherRelationMapper questionMatcherRelationMapper;
    @Autowired
    private CourseTeacherRelationMapper courseTeacherRelationMapper;
    @Autowired
    private QuestionKnowledgepointRelationMapper questionKnowledgepointRelationMapper;


    @Override
    public FillInBlankQuestionDto getFillInBlankQuestion(String id) throws Exception {
        FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(id);
        FillInBlankQuestionDto fillInBlankQuestionDto=null;
        if(fillInBlankQuestion!=null){
            fillInBlankQuestionDto=new FillInBlankQuestionDto();

            //构造fillInBlankQuestionDto
            BeanUtils.copyProperties(fillInBlankQuestion,fillInBlankQuestionDto);

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(fillInBlankQuestion.getCourseId());
            if(course!=null){
                fillInBlankQuestionDto.setCourseName(course.getName());
            }

            //查询创建教师信息
            Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(fillInBlankQuestion.getCreatedTeacherId());
            if(createdTeacher!=null){
                fillInBlankQuestionDto.setCreatedTeacher(createdTeacher);
            }

            //查询难度信息
            DictInfoExample dictInfoDifficultyExample=new DictInfoExample();
            DictInfoExample.Criteria dictInfoDifficultyCriteria = dictInfoDifficultyExample.createCriteria();
            dictInfoDifficultyCriteria.andCodeEqualTo(String.valueOf(fillInBlankQuestion.getDifficulty()));
            dictInfoDifficultyCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
            List<DictInfo> dictInfoDifficultyList = dictInfoMapper.selectByExample(dictInfoDifficultyExample);
            if(!CollectionUtils.isEmpty(dictInfoDifficultyList)){
                fillInBlankQuestionDto.setDifficultyName(dictInfoDifficultyList.get(0).getName());
            }

            //查询答案匹配信息
            QuestionMatcherRelationExample questionMatcherRelationExample=new QuestionMatcherRelationExample();
            QuestionMatcherRelationExample.Criteria questionMatcherRelationCriteria = questionMatcherRelationExample.createCriteria();
            questionMatcherRelationCriteria.andQuestionIdEqualTo(id);
            List<QuestionMatcherRelation> questionMatcherRelationList = questionMatcherRelationMapper.selectByExample(questionMatcherRelationExample);
            if(!CollectionUtils.isEmpty(questionMatcherRelationList)){
                List<String> matcherCodeList=new ArrayList<>();
                for(QuestionMatcherRelation questionMatcherRelation :questionMatcherRelationList){
                    matcherCodeList.add(questionMatcherRelation.getMatcherCode().toString());
                }

                DictInfoExample dictInfoMatcherExample=new DictInfoExample();
                DictInfoExample.Criteria dictInfoMatcherCriteria = dictInfoMatcherExample.createCriteria();
                dictInfoMatcherCriteria.andCodeIn(matcherCodeList);
                dictInfoMatcherCriteria.andDictTypeIdEqualTo(DICTTYPE_MATCHER_ID);
                List<DictInfo> dictInfoMatcherList = dictInfoMapper.selectByExample(dictInfoMatcherExample);
                List<String> matcherNameList=new ArrayList<>();
                for(DictInfo matcherDictInfo:dictInfoMatcherList){
                    matcherNameList.add(matcherDictInfo.getName());
                }
                fillInBlankQuestionDto.setMatcherNames(matcherNameList);
            }
        }
        return fillInBlankQuestionDto;
    }

    @Override
    public List<FillInBlankQuestionDto> listFillInBlankQuestion(FillInBlankQuestionVo fillInBlankQuestionVo) throws Exception {
        return fillInBlankQuestionMapperCustom.listFillInBlankQuestion(fillInBlankQuestionVo);
    }

    @Override
    public ResultInfo saveFillInBlankQuestion(FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception {
        //内容不能为空
        String questionContent = fillInBlankQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //空格数不能为空
        Integer blankNum = fillInBlankQuestionDto.getBlankNum();
        if(blankNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_BLANKNUM_NOT_NULL,null);

        //课程id不能为空
        String questionCourseId = fillInBlankQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = fillInBlankQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = fillInBlankQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //答案不能为空
        List<String> answerList = fillInBlankQuestionDto.getAnswerList();

        Map<String,List<String>> answerMap=new HashMap<>();

        for(int i=1;i<=blankNum;i++){
            String answer=answerList.get(i-1);

            if(StringUtils.isBlank(answer))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);
            answer=answer.trim();
            String[] answerArr=answer.split(",");
            List<String> answerListCustom = new ArrayList<>();
            for(String ans:answerArr){
                if(!StringUtils.isBlank(ans))
                    answerListCustom.add(ans.replace(" ",""));
            }
            if(CollectionUtils.isEmpty(answerListCustom))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

            answerMap.put(i+"",answerListCustom);
        }

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
        List<DictInfo> dictInfoDifficultyList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoDifficultyList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }

        String matchersCodeArr[]=null;

        //如果存在答案匹配模式，则必须存在
        String matchersCode = fillInBlankQuestionDto.getMatchersCode();
        if(!StringUtils.isBlank(matchersCode)){
            matchersCodeArr=stirngCodesToArrIds(matchersCode);
            for(String matcherCode:matchersCodeArr){
                DictInfoExample matcherExample=new DictInfoExample();
                DictInfoExample.Criteria matcherCriteria = matcherExample.createCriteria();
                matcherCriteria.andCodeEqualTo(matcherCode);
                dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_MATCHER_ID);
                List<DictInfo> dictInfoMatcherList = dictInfoMapper.selectByExample(matcherExample);
                if(CollectionUtils.isEmpty(dictInfoMatcherList)){
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_MATCHER_NOT_EXIST,null);
                }
            }
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
        fillInBlankQuestionDto.setId(questionId);
        fillInBlankQuestionDto.setType(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));

        //添加审核状态
        fillInBlankQuestionDto.setIsChecked(false);

        fillInBlankQuestionDto.setCreatedTime(new Date());
        fillInBlankQuestionDto.setUpdatedTime(new Date());

        //解析答案
        String answerJson = JsonUtils.objectToJson(answerMap);
        fillInBlankQuestionDto.setAnswer(answerJson);

        //如果存在答案匹配模式，则添加题目答案匹配模式关系
        if(matchersCodeArr!=null){
            for(String matcherCode:matchersCodeArr){
                QuestionMatcherRelation questionMatcherRelation=new QuestionMatcherRelation();
                String questionMatcherRelationId=UUIDBuild.getUUID();
                questionMatcherRelation.setId(questionMatcherRelationId);
                questionMatcherRelation.setQuestionId(questionId);
                questionMatcherRelation.setMatcherCode(new Integer(matcherCode));
                questionMatcherRelation.setCreatedTime(new Date());
                questionMatcherRelation.setUpdatedTime(new Date());

                questionMatcherRelationMapper.insert(questionMatcherRelation);
            }
        }

        //添加题目
        fillInBlankQuestionMapper.insert(fillInBlankQuestionDto);
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateFillInBlankQuestion(String id, FillInBlankQuestionDto fillInBlankQuestionDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        //内容不能为空
        String questionContent = fillInBlankQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //空格数不能为空
        Integer blankNum = fillInBlankQuestionDto.getBlankNum();
        if(blankNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_BLANKNUM_NOT_NULL,null);

        //课程id不能为空
        String questionCourseId = fillInBlankQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = fillInBlankQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = fillInBlankQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //答案不能为空
        List<String> answerList = fillInBlankQuestionDto.getAnswerList();
        System.out.println(answerList);

        Map<String,List<String>> answerMap=new HashMap<>();

        for(int i=1;i<=blankNum;i++){
            String answer=answerList.get(i-1);

            if(StringUtils.isBlank(answer))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);
            answer=answer.trim();
            String[] answerArr=answer.split(",");
            List<String> answerListCustom = new ArrayList<>();
            for(String ans:answerArr){
                if(!StringUtils.isBlank(ans))
                    answerListCustom.add(ans.replace(" ",""));
            }
            if(CollectionUtils.isEmpty(answerList))
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

            answerMap.put(i+"",answerListCustom);
        }

        //id对应题目必须存在
        FillInBlankQuestion fillInBlankQuestionDb = fillInBlankQuestionMapper.selectByPrimaryKey(id);
        if(fillInBlankQuestionDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

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
        List<DictInfo> dictInfoDifficultyList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoDifficultyList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }

        String matchersCodeArr[]=null;

        //如果存在答案匹配模式，则必须存在
        String matchersCode = fillInBlankQuestionDto.getMatchersCode();
        if(!StringUtils.isBlank(matchersCode)){
            matchersCodeArr=stirngCodesToArrIds(matchersCode);
            for(String matcherCode:matchersCodeArr){
                DictInfoExample matcherExample=new DictInfoExample();
                DictInfoExample.Criteria matcherCriteria = matcherExample.createCriteria();
                matcherCriteria.andCodeEqualTo(matcherCode);
                dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_MATCHER_ID);
                List<DictInfo> dictInfoMatcherList = dictInfoMapper.selectByExample(matcherExample);
                if(CollectionUtils.isEmpty(dictInfoMatcherList)){
                    return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_MATCHER_NOT_EXIST,null);
                }
            }
        }

        //只有审核待通过的题目才允许修改
        Boolean isChecked = fillInBlankQuestionDto.getIsChecked();
        if(isChecked)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_IS_CHECKED,null);

        //删除修改前的答案匹配模式
        QuestionMatcherRelationExample questionMatcherRelationExample=new QuestionMatcherRelationExample();
        QuestionMatcherRelationExample.Criteria questionMatcherRelationCriteria = questionMatcherRelationExample.createCriteria();
        questionMatcherRelationCriteria.andQuestionIdEqualTo(id);

        questionMatcherRelationMapper.deleteByExample(questionMatcherRelationExample);

        //如果存在答案匹配模式，则添加题目答案匹配模式关系
        if(matchersCodeArr!=null){
            for(String matcherCode:matchersCodeArr){
                QuestionMatcherRelation questionMatcherRelation=new QuestionMatcherRelation();
                String questionMatcherRelationId=UUIDBuild.getUUID();
                questionMatcherRelation.setId(questionMatcherRelationId);
                questionMatcherRelation.setQuestionId(id);
                questionMatcherRelation.setMatcherCode(new Integer(matcherCode));
                questionMatcherRelation.setCreatedTime(new Date());
                questionMatcherRelation.setUpdatedTime(new Date());

                questionMatcherRelationMapper.insert(questionMatcherRelation);
            }
        }

        //该题目对应课程必须在教师任课范围内
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationExampleCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationExampleCriteria.andCourseIdEqualTo(questionCourseId);
        courseTeacherRelationExampleCriteria.andTeacherIdEqualTo(questionCreatedTeacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(CollectionUtils.isEmpty(courseTeacherRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST,null);

        //如果修改了课程id,则该题目对应知识点要删除
        if(!StringUtils.equals(questionCourseId,fillInBlankQuestionDb.getCourseId())){
            //删除该题目对应知识点
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationExampleCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationExampleCriteria.andQuestionIdEqualTo(id);
            questionKnowledgepointRelationMapper.deleteByExample(questionKnowledgepointRelationExample);
        }

        //更新题目
        fillInBlankQuestionDb.setContent(questionContent);
        fillInBlankQuestionDb.setBlankNum(blankNum);
        fillInBlankQuestionDb.setCourseId(questionCourseId);
        fillInBlankQuestionDb.setDifficulty(questionDifficultyId);

        //解析答案
        String answerJson = JsonUtils.objectToJson(answerMap);
        System.out.println(answerJson);
        fillInBlankQuestionDb.setAnswer(answerJson);

        fillInBlankQuestionDb.setUpdatedTime(new Date());
        fillInBlankQuestionMapper.updateByPrimaryKeyWithBLOBs(fillInBlankQuestionDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    /**
     * 将code字符串转换成数组
     * @param codes 要转成数组的id字符串
     * @return
     */
    private String[] stirngCodesToArrIds(String codes){
        if(codes.contains(","))//超过1个code
            return codes.split(",");
        else
            return new String[]{codes};
    }
}
