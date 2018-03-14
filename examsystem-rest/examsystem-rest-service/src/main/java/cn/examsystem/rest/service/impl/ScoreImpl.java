package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.*;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.service.ScoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/1/30.
 * 成绩业务层实现
 */
@Service
public class ScoreImpl implements ScoreService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ExamMapperCustom examMapperCustom;
    @Autowired
    private ExamStudentRelationMapper examStudentRelationMapper;
    @Autowired
    private ExamstudentAnswerMapper examstudentAnswerMapper;
    @Autowired
    private TestpaperQuestionRelationMapper testpaperQuestionRelationMapper;
    @Autowired
    private SingleChoiceQuestionMapper singleChoiceQuestionMapper;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Autowired
    private FillInBlankQuestionMapper fillInBlankQuestionMapper;
    @Autowired
    private QuestionMatcherRelationMapper questionMatcherRelationMapper;
    @Autowired
    private TestPaperMapper testPaperMapper;


    @Value("${MESSAGE_EXAM_ID_NOT_NULL}")
    private String MESSAGE_EXAM_ID_NOT_NULL;
    @Value("${MESSAGE_EXAM_NOT_EXIST}")
    private String MESSAGE_EXAM_NOT_EXIST;

    @Value("${DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE}")
    private String DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${DICTINFO_FILLINBLANKQUESTION_TYPE_CODE}")
    private String DICTINFO_FILLINBLANKQUESTION_TYPE_CODE;
    @Value("${DICTINFO_MATCHER_IS_NOT_ORDER_CODE}")
    private String DICTINFO_MATCHER_IS_NOT_ORDER_CODE;


    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Override
    public List<ExamStudentRelationDto> listStudentScore(String examId,ExamStudentRelationVo examStudentRelationVo) throws Exception {

        examStudentRelationVo=examStudentRelationVo==null?new ExamStudentRelationVo():examStudentRelationVo;

        examStudentRelationVo.setExamId(examId);
        return examMapperCustom.listExamStudent(examStudentRelationVo);
    }

    @Override
    public ResultInfo autoGradeForExam(String examId) throws Exception {

        //考试id不允许为空
        if(StringUtils.isBlank(examId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_ID_NOT_NULL,null);

        //id对应专业必须存在
        Exam examDb = examMapper.selectByPrimaryKey(examId);
        if(examDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_EXAM_NOT_EXIST,null);

        //查询该考试下的所有考试学生
        ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
        ExamStudentRelationExample.Criteria examStudentCriteria = examStudentRelationExample.createCriteria();
        examStudentCriteria.andExamIdEqualTo(examId);
        List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);

        for(ExamStudentRelation relation:examStudentRelationList){
            //如果该考试学生未完成评分，再给它评分
            if(!relation.getIsGraded()){
                //查询该考试学生的答题信息
                ExamstudentAnswerExample examstudentAnswerExample=new ExamstudentAnswerExample();
                ExamstudentAnswerExample.Criteria examstudentAnswerCriteria = examstudentAnswerExample.createCriteria();
                examstudentAnswerCriteria.andExamStudentIdEqualTo(relation.getId());
                List<ExamstudentAnswer> examstudentAnswerList = examstudentAnswerMapper.selectByExampleWithBLOBs(examstudentAnswerExample);

                for(ExamstudentAnswer examstudentAnswer:examstudentAnswerList){//考试学生答题信息
                    //学生答案
                    String studentAnswer=examstudentAnswer.getStudentAnswer();
System.out.println(studentAnswer);
                    //查询对应试卷题目
                    TestpaperQuestionRelation testPaperQuestion = testpaperQuestionRelationMapper.selectByPrimaryKey(examstudentAnswer.getTestpaperQuestionId());

                    System.out.println(testPaperQuestion.getQuestionType()+""+new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE));

                    if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE))){//单选题
                        System.out.println("单选题");
                        //查询单选题信息
                        SingleChoiceQuestion singleChoiceQuestion = singleChoiceQuestionMapper.selectByPrimaryKey(testPaperQuestion.getQuestionId());
                        //单选题答案
                        if(StringUtils.isBlank(studentAnswer)||!studentAnswer.trim().equals(singleChoiceQuestion.getAnswer())){//答错
                            examstudentAnswer.setIsGraded(true);
                            examstudentAnswer.setUpdatedTime(new Date());
                            examstudentAnswerMapper.updateByPrimaryKey(examstudentAnswer);
                        }else{
                            //修改得分
                            examstudentAnswer.setScore(testPaperQuestion.getQuestionScore());
                            examstudentAnswer.setIsGraded(true);
                            examstudentAnswer.setUpdatedTime(new Date());
                            examstudentAnswerMapper.updateByPrimaryKey(examstudentAnswer);

                            //考试学生总分加上该题目得分
                            relation.setScore(relation.getScore().add(testPaperQuestion.getQuestionScore()));
                        }
                    }
                    else if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE))){//判断题
                        System.out.println("判断题");
                        //查询判断题信息
                        TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(testPaperQuestion.getQuestionId());
                        //判断题答案
                        if(StringUtils.isBlank(studentAnswer)||!studentAnswer.trim().equals(trueOrFalseQuestion.getAnswer())){//答错
                            examstudentAnswer.setIsGraded(true);
                            examstudentAnswer.setUpdatedTime(new Date());
                            examstudentAnswerMapper.updateByPrimaryKey(examstudentAnswer);
                        }else{
                            //修改得分
                            examstudentAnswer.setScore(testPaperQuestion.getQuestionScore());
                            examstudentAnswer.setIsGraded(true);
                            examstudentAnswer.setUpdatedTime(new Date());
                            examstudentAnswerMapper.updateByPrimaryKey(examstudentAnswer);

                            //考试学生总分加上该题目得分
                            relation.setScore(relation.getScore().add(testPaperQuestion.getQuestionScore()));

                        }
                    }
                    else if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE))){
                        System.out.println("填空题");
                        //查询填空题信息
                        FillInBlankQuestion fillInBlankQuestion = fillInBlankQuestionMapper.selectByPrimaryKey(testPaperQuestion.getQuestionId());
                        //填空题答案


                        //解析答案

                        //该题目每个空可能的答案，Integer是题目空格号，List是该空可能的答案
                        Map<Integer, List> answerMap = JsonUtils.jsonToMap(fillInBlankQuestion.getAnswer(), Integer.class, List.class);

                        //该学生填空题每个空的答案
                        List<String> studentAnswerList = JsonUtils.jsonToList(studentAnswer, String.class);

                        //每个空的分数
                        BigDecimal aBlankScore = testPaperQuestion.getQuestionScore().divide(new BigDecimal(fillInBlankQuestion.getBlankNum()));

                        //获取该填空题的匹配模式
                        QuestionMatcherRelationExample questionMatcherRelationExample=new QuestionMatcherRelationExample();
                        QuestionMatcherRelationExample.Criteria questionMatcherRelationCriteria = questionMatcherRelationExample.createCriteria();
                        questionMatcherRelationCriteria.andQuestionIdEqualTo(fillInBlankQuestion.getId());
                        List<QuestionMatcherRelation> questionMatcherRelationList = questionMatcherRelationMapper.selectByExample(questionMatcherRelationExample);

                        boolean isOrder=true;

                        for(QuestionMatcherRelation matcher:questionMatcherRelationList){
                            if(matcher.getMatcherCode().equals(new Integer(DICTINFO_MATCHER_IS_NOT_ORDER_CODE))){
                                isOrder=false;
                                break;
                            }
                        }

                        if(isOrder){
                            //遍历学生该题从第一个空到最后一个空的答案
                            for(int i=0;i<studentAnswerList.size();i++){
                                //学生该空的答案
                                String ans = studentAnswerList.get(i);
                                //该空的所有答案
                                List<String> list = answerMap.get(i+1);
                                if(list.contains(ans)){
                                    //获取该题目的分数，并设置分数，修改状态为已评分
                                    examstudentAnswer.setScore(examstudentAnswer.getScore().add(aBlankScore));
                                }

                            }
                        }else{
                            //无序题目一般不会出现相同答案
                            //遍历学生该题从第一个空到最后一个空的答案,去除重复的答案
                            for (int x = 0; x < studentAnswerList.size() - 1; x++) {
                                for (int y = x + 1; y < studentAnswerList.size(); y++) {
                                    if (studentAnswerList.get(x).equals(studentAnswerList.get(y))) {
                                        studentAnswerList.remove(y);
                                        y--;
                                    }
                                }
                            }

                            //获取该题目的所有答案
                            List<String> allAnswerlist =new ArrayList<>();
                            Set<Map.Entry<Integer, List>> entrySet = answerMap.entrySet();
                            for(Map.Entry<Integer, List> entry:entrySet){
                                List list = entry.getValue();
                                allAnswerlist.addAll(list);
                            }
                            System.out.println(studentAnswerList+"-------------"+allAnswerlist);

                            //遍历去除重复后的答案，只要出现在答案里面，就能得分
                            for(int i=0;i<studentAnswerList.size();i++){
                                //学生该空的答案
                                String ans = studentAnswerList.get(i);
                                //该空的所有答案
                                if(allAnswerlist.contains(ans)){
                                    //获取该题目的分数，并设置分数，修改状态为已评分
                                    examstudentAnswer.setScore(examstudentAnswer.getScore().add(aBlankScore));
                                }

                            }
                        }

                        examstudentAnswer.setIsGraded(true);
                        examstudentAnswer.setUpdatedTime(new Date());
                        //更新学生试卷答案
                        examstudentAnswerMapper.updateByPrimaryKey(examstudentAnswer);

                        relation.setScore(relation.getScore().add(examstudentAnswer.getScore()));

                    }
                }
                relation.setUpdatedTime(new Date());
                relation.setIsGraded(true);
                //更新
                examStudentRelationMapper.updateByPrimaryKey(relation);
            }
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public TestPaperDto getTestPaperByExamStudent(String examStudentId) throws Exception {
        ExamStudentRelation examStudentRelation = examStudentRelationMapper.selectByPrimaryKey(examStudentId);

        TestPaperDto testPaperDto=null;

        if(examStudentRelation!=null){
            Exam exam = examMapper.selectByPrimaryKey(examStudentRelation.getExamId());
            if(exam!=null){
                if(examStudentRelation!=null){
                    //查询试卷信息
                    TestPaper testPaper = testPaperMapper.selectByPrimaryKey(exam.getTestPaperId());

                    //构造testPaperDto
                    testPaperDto=new TestPaperDto();
                    BeanUtils.copyProperties(testPaper,testPaperDto);

                    //试卷中单选题信息
                    List<TestPaperSingleChoiceQuestion> singleChoiceQuestionList=new ArrayList<>();

                    //查询该试卷单选题信息
                    TestpaperQuestionRelationExample testPaperSingleChoiceQuestionExample=new TestpaperQuestionRelationExample();
                    TestpaperQuestionRelationExample.Criteria singleChoiceQuestionCriteria = testPaperSingleChoiceQuestionExample.createCriteria();
                    singleChoiceQuestionCriteria.andTestPaperIdEqualTo(exam.getTestPaperId());
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

                    testPaperDto.setSingleChoiceQuestions(singleChoiceQuestionList);



                    //试卷中判断题信息
                    List<TestPaperTrueOrFalseQuestion> trueOrFalseQuestionList=new ArrayList<>();

                    //查询该试卷判断题信息
                    TestpaperQuestionRelationExample testPaperTrueOrFalseQuestionExample=new TestpaperQuestionRelationExample();
                    TestpaperQuestionRelationExample.Criteria trueOrFalseQuestionCriteria = testPaperTrueOrFalseQuestionExample.createCriteria();
                    trueOrFalseQuestionCriteria.andTestPaperIdEqualTo(exam.getTestPaperId());
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

                    testPaperDto.setTrueOrFalseQuestions(trueOrFalseQuestionList);




                    //试卷中填空题信息
                    List<TestPaperFillInBlankQuestion> fillInBlankQuestionList=new ArrayList<>();

                    //查询该试卷填空题信息
                    TestpaperQuestionRelationExample testPaperFillInBlankQuestionExample=new TestpaperQuestionRelationExample();
                    TestpaperQuestionRelationExample.Criteria fillInBlankQuestionCriteria = testPaperFillInBlankQuestionExample.createCriteria();
                    fillInBlankQuestionCriteria.andTestPaperIdEqualTo(exam.getTestPaperId());
                    fillInBlankQuestionCriteria.andQuestionTypeEqualTo(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE));
                    testPaperFillInBlankQuestionExample.setOrderByClause("question_order");
                    List<TestpaperQuestionRelation> testPaperFillInBlankQuestionList = testpaperQuestionRelationMapper.selectByExample(testPaperFillInBlankQuestionExample);

                    System.out.println(testPaperFillInBlankQuestionList.size());

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

                    testPaperDto.setFillInBlankQuestions(fillInBlankQuestionList);

System.out.println(testPaperDto.getFillInBlankQuestions().get(0));

                    //加载学生试卷中题目答案
                    ExamstudentAnswerExample examstudentAnswerExample=new ExamstudentAnswerExample();
                    ExamstudentAnswerExample.Criteria examstudentAnswerCriteria = examstudentAnswerExample.createCriteria();
                    examstudentAnswerCriteria.andExamStudentIdEqualTo(examStudentId);
                    List<ExamstudentAnswer> examstudentAnswerList = examstudentAnswerMapper.selectByExampleWithBLOBs(examstudentAnswerExample);

                    Map<Integer, String> singleChoiceQuestionAnswer=new HashMap<>();
                    Map<Integer, String> trueOrFalseQuestionAnswer=new HashMap<>();
                    Map<Integer, List> fillInBlankQuestionAnswer=new HashMap<>();

                    Map<Integer, BigDecimal> singleChoiceQuestionAnswerScore=new HashMap<>();
                    Map<Integer, BigDecimal> trueOrFalseQuestionAnswerScore=new HashMap<>();
                    Map<Integer, BigDecimal> fillInBlankQuestionAnswerScore=new HashMap<>();


                    for(ExamstudentAnswer studentAnswer:examstudentAnswerList){
                        //获取试卷题目
                        TestpaperQuestionRelation testPaperQuestion = testpaperQuestionRelationMapper.selectByPrimaryKey(studentAnswer.getTestpaperQuestionId());

                        System.out.println(studentAnswer.getStudentAnswer());

                        if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_SINGLECHOICEQUESTION_TYPE_CODE))){//单选题
                            singleChoiceQuestionAnswer.put(testPaperQuestion.getQuestionOrder(),studentAnswer.getStudentAnswer());
                            singleChoiceQuestionAnswerScore.put(testPaperQuestion.getQuestionOrder(),studentAnswer.getScore());
                        }

                        if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE))){//判断题
                            trueOrFalseQuestionAnswer.put(testPaperQuestion.getQuestionOrder(),studentAnswer.getStudentAnswer());
                            trueOrFalseQuestionAnswerScore.put(testPaperQuestion.getQuestionOrder(),studentAnswer.getScore());
                        }
                        if(testPaperQuestion.getQuestionType().equals(new Integer(DICTINFO_FILLINBLANKQUESTION_TYPE_CODE))){//填空题
                            List list=JsonUtils.jsonToList(studentAnswer.getStudentAnswer(),String.class);

                            fillInBlankQuestionAnswer.put(testPaperQuestion.getQuestionOrder(),list);
                            fillInBlankQuestionAnswerScore.put(testPaperQuestion.getQuestionOrder(),studentAnswer.getScore());
                        }
                    }
                    testPaperDto.setSingleChoiceQuestionAnswer(singleChoiceQuestionAnswer);
                    testPaperDto.setTrueOrFalseQuestionAnswer(trueOrFalseQuestionAnswer);
                    testPaperDto.setFillInBlankQuestionAnswer(fillInBlankQuestionAnswer);

                    testPaperDto.setSingleChoiceQuestionAnswerScore(singleChoiceQuestionAnswerScore);
                    testPaperDto.setTrueOrFalseQuestionAnswerScore(trueOrFalseQuestionAnswerScore);
                    testPaperDto.setFillInBlankQuestionAnswerScore(fillInBlankQuestionAnswerScore);

                    System.out.println(testPaperDto.getSingleChoiceQuestionAnswer());
                    System.out.println(testPaperDto.getTrueOrFalseQuestionAnswer());
                    System.out.println(testPaperDto.getFillInBlankQuestionAnswer());

                }
            }
        }

        return testPaperDto;
    }
}
