package cn.examsystem.student.service.impl;

import cn.examsystem.common.jedis.JedisClient;
import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.student.service.TestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/17.
 * 考试业务层实现
 */
@Service
public class TestImpl implements TestService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${MESSAGE_QUESTION_NUM_NOT_NULL}")
    private String MESSAGE_QUESTION_NUM_NOT_NULL;
    @Value("${REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER}")
    private String REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER}")
    private String REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER;
    @Value("${REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER}")
    private String REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER;

    @Value("${MESSAGE_SAVE_SUCCESS}")
    private String MESSAGE_SAVE_SUCCESS;

    @Override
    public ResultInfo saveSingleChoiceQuestionAnswer(String examStudentId,TestPaperDto testPaperDto) throws Exception {

        //单选题数量不能为空
        Integer singleChoiceQuestionNum = testPaperDto.getSingleChoiceQuestionNum();
        if(singleChoiceQuestionNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NUM_NOT_NULL,null);

        //学生试卷单选题答题信息
        Map<Integer, String> singleChoiceQuestionAnswer = testPaperDto.getSingleChoiceQuestionAnswer();

        Map< Integer, String> redisMap=new LinkedHashMap<Integer, String>();
        //如果用户没写就提交，设置答案全为空

        //如果用户全写了，则中间为null的答案设置为“”

        if(singleChoiceQuestionAnswer==null||singleChoiceQuestionAnswer.size()<1){
            for(int i=1;i<=singleChoiceQuestionNum;i++){
                redisMap.put(i, "");
            }
        }else{
            for(int i=1;i<=singleChoiceQuestionNum;i++){
                String answer = singleChoiceQuestionAnswer.get(i);
                if(StringUtils.isBlank(answer))
                    answer="";
                redisMap.put(i, answer);
            }
        }
        jedisClient.hset(examStudentId, REDIS_KEY_SINGLE_CHOICE_QUESTION_ANSWER, JsonUtils.objectToJson(redisMap));

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_SAVE_SUCCESS,null);
    }

    @Override
    public ResultInfo saveTrueOrFalseQuestionAnswer(String examStudentId,TestPaperDto testPaperDto) throws Exception {

        //判断题数量不能为空
        Integer trueOrFalseQuestionNum = testPaperDto.getTrueOrFalseQuestionNum();
        if(trueOrFalseQuestionNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NUM_NOT_NULL,null);

        //学生试卷判断题答题信息
        Map<Integer, String> trueOrFalseQuestionAnswer = testPaperDto.getTrueOrFalseQuestionAnswer();


        Map< Integer, String> redisMap=new LinkedHashMap<Integer, String>();
        //如果用户没写就提交，设置答案全为空

        //如果用户全写了，则中间为null的答案设置为“”

        if(trueOrFalseQuestionAnswer==null||trueOrFalseQuestionAnswer.size()<1){
            for(int i=1;i<=trueOrFalseQuestionNum;i++){
                redisMap.put(i, "");
            }
        }else{
            for(int i=1;i<=trueOrFalseQuestionNum;i++){
                String answer = trueOrFalseQuestionAnswer.get(i);
                if(StringUtils.isBlank(answer))
                    answer="";
                redisMap.put(i, answer);
            }
        }
        jedisClient.hset(examStudentId, REDIS_KEY_TRUE_OR_FALSE_QUESTION_ANSWER, JsonUtils.objectToJson(redisMap));

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_SAVE_SUCCESS,null);
    }

    @Override
    public ResultInfo saveFillInBlankQuestionAnswer(String examStudentId,TestPaperDto testPaperDto) throws Exception {

        //填空题数量不能为空
        Integer fillInBlankQuestionNum = testPaperDto.getFillInBlankQuestionNum();
        if(fillInBlankQuestionNum==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NUM_NOT_NULL,null);

        //学生试卷填空题答题信息
        Map<Integer, List> fillInBlankQuestionAnswer = testPaperDto.getFillInBlankQuestionAnswer();

        Map< Integer, List> redisMap=new LinkedHashMap<Integer, List>();

        for(int i=1;i<=fillInBlankQuestionAnswer.size();i++){
            List<String> list = fillInBlankQuestionAnswer.get(i);
            redisMap.put(i, list);
        }
        jedisClient.hset(examStudentId, REDIS_KEY_FILL_IN_BLANK_QUESTION_ANSWER, JsonUtils.objectToJson(redisMap));

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_SAVE_SUCCESS,null);
    }
}
