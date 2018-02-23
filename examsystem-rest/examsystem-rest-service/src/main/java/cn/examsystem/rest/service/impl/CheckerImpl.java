package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.mapper.FillInBlankQuestionMapper;
import cn.examsystem.rest.mapper.SingleChoiceQuestionMapper;
import cn.examsystem.rest.mapper.TrueOrFalseQuestionMapper;
import cn.examsystem.rest.pojo.po.FillInBlankQuestion;
import cn.examsystem.rest.pojo.po.SingleChoiceQuestion;
import cn.examsystem.rest.pojo.po.TrueOrFalseQuestion;
import cn.examsystem.rest.service.CheckerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/2/17.
 * 审核业务层实现
 */
@Service
public class CheckerImpl implements CheckerService{

    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Autowired
    private FillInBlankQuestionMapper fillInBlankQuestionMapper;

    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;

    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_TYPE_NOT_NULL}")
    private String MESSAGE_QUESTION_TYPE_NOT_NULL;
    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;


    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Override
    public ResultInfo checkQuestion(String id,String questionType) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        //题目类型不允许为空
        if(StringUtils.isBlank(questionType))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_TYPE_NOT_NULL,null);

        //审核的题目必须存在
        if(StringUtils.equals(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE,questionType)){//单选题
            SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(id);
            if(singleChoiceQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

            //审核题目
            singleChoiceQuestion.setIsChecked(!singleChoiceQuestion.getIsChecked());
            singleChoiceQuestionMapper.updateByPrimaryKey(singleChoiceQuestion);

        }else if(StringUtils.equals(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE,questionType)){//判断题
            TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
            if(trueOrFalseQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

            //审核题目
            trueOrFalseQuestion.setIsChecked(!trueOrFalseQuestion.getIsChecked());
            trueOrFalseQuestionMapper.updateByPrimaryKey(trueOrFalseQuestion);

        }else if(StringUtils.equals(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE,questionType)){//填空题
            FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(id);
            if(fillInBlankQuestion==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

            //审核题目
            fillInBlankQuestion.setIsChecked(!fillInBlankQuestion.getIsChecked());
            fillInBlankQuestionMapper.updateByPrimaryKey(fillInBlankQuestion);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }
}
