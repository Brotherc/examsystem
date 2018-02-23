package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.QuestionKnowledgePointRelationDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.service.QuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 题目业务层实现
 */
@Service
public class QuestionImpl implements QuestionService {

    @Value("${MESSAGE_QUESTION_TYPE_NOT_NULL}")
    private String MESSAGE_QUESTION_TYPE_NOT_NULL;

    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;

    @Value("${MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL}")
    private String MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL;

    @Value("${MESSAGE_KNOWLEDGEPOINT_NOT_EXIST}")
    private String MESSAGE_KNOWLEDGEPOINT_NOT_EXIST;

    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;

    @Value("${MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_REPEAT}")
    private String MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_REPEAT;

    @Value("${MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_EXIST}")
    private String MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_EXIST;

    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;


    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;


    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    @Autowired
    private QuestionKnowledgepointRelationMapper questionKnowledgepointRelationMapper;
    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Autowired
    private FillInBlankQuestionMapper fillInBlankQuestionMapper;

    @Override
    public ResultInfo addKnowledgePointToQuestion(String id,String knowledgePointId,QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception {

        //题目类型不能为空
        Integer questionType = questionKnowledgePointRelationDto.getQuestionType();
        if(questionType==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_TYPE_NOT_NULL,null);

        //题目id不能为空
        if(StringUtils.isBlank(id)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);
        }

        //知识点id不能为空
        if(StringUtils.isBlank(knowledgePointId)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL,null);
        }

        //知识点id对应知识点必须存在
        KnowledgePoint knowledgePoint = knowledgePointMapper.selectByPrimaryKey(knowledgePointId);
        if(knowledgePoint==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NOT_EXIST,null);

        //题目id对应题目必须存在
        if(questionType==new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE)){//单选题
            SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(id);
            if(singleChoiceQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }else if(questionType==new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE)){//判断题
            TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
            if(trueOrFalseQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }else if(questionType==new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE)){//填空题
            FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(id);
            if(fillInBlankQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }

        //如果改知识点中已存在该题目，则不允许录入
        QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
        QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationCriteria = questionKnowledgepointRelationExample.createCriteria();
        questionKnowledgepointRelationCriteria.andQuestionIdEqualTo(id);
        questionKnowledgepointRelationCriteria.andKnowledgePointIdEqualTo(knowledgePointId);
        List<QuestionKnowledgepointRelation> questionKnowledgepointRelationList = questionKnowledgepointRelationMapper.selectByExample(questionKnowledgepointRelationExample);
        if(!CollectionUtils.isEmpty(questionKnowledgepointRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_REPEAT,null);

        //补全id
        String questionKnowledgePointRelationId = UUIDBuild.getUUID();
        questionKnowledgePointRelationDto.setId(questionKnowledgePointRelationId);

        questionKnowledgePointRelationDto.setQuestionId(id);
        questionKnowledgePointRelationDto.setKnowledgePointId(knowledgePointId);

        //补全创建时间，更新时间
        questionKnowledgePointRelationDto.setCreatedTime(new Date());
        questionKnowledgePointRelationDto.setUpdatedTime(new Date());

        //添加题目知识点关系
        questionKnowledgepointRelationMapper.insert(questionKnowledgePointRelationDto);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo removeKnowledgePointForQuestion(String id,String knowledgePointId,QuestionKnowledgePointRelationDto questionKnowledgePointRelationDto) throws Exception {

        //题目类型不能为空
        Integer questionType = questionKnowledgePointRelationDto.getQuestionType();
        if(questionType==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_TYPE_NOT_NULL,null);

        //题目id不能为空
        if(StringUtils.isBlank(id)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);
        }

        //知识点id不能为空
        if(StringUtils.isBlank(knowledgePointId)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL,null);
        }

        //知识点id对应知识点必须存在
        KnowledgePoint knowledgePoint = knowledgePointMapper.selectByPrimaryKey(knowledgePointId);
        if(knowledgePoint==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NOT_EXIST,null);

        //题目id对应题目必须存在
        if(questionType==new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE)){//单选题
            SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(id);
            if(singleChoiceQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }else if(questionType==new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE)){//判断题
            TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
            if(trueOrFalseQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }else if(questionType==new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE)){//填空题
            FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(id);
            if(fillInBlankQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);
        }

        //如果改知识点中并未存在该课程，则不允许移除
        QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
        QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationCriteria = questionKnowledgepointRelationExample.createCriteria();
        questionKnowledgepointRelationCriteria.andQuestionIdEqualTo(id);
        questionKnowledgepointRelationCriteria.andKnowledgePointIdEqualTo(knowledgePointId);
        List<QuestionKnowledgepointRelation> questionKnowledgepointRelationList = questionKnowledgepointRelationMapper.selectByExample(questionKnowledgepointRelationExample);
        if(CollectionUtils.isEmpty(questionKnowledgepointRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_KNOWLEDGEPOINT_NOT_EXIST,null);

        //待删除的题目知识点
        QuestionKnowledgepointRelation questionKnowledgepointRelation = questionKnowledgepointRelationList.get(0);

        //删除题目知识点关系
        questionKnowledgepointRelationMapper.deleteByPrimaryKey(questionKnowledgepointRelation.getId());

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }
}
