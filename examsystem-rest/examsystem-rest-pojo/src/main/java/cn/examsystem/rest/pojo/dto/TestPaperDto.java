package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.TestPaper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/6.
 * 试卷（包括题目信息）
 */
public class TestPaperDto extends TestPaper{

    private String schoolYearName;
    private String courseName;

    private Integer singleChoiceQuestionNum;
    private Integer trueOrFalseQuestionNum;
    private Integer fillInBlankQuestionNum;
    private Integer programQuestionNum;

    private String singleChoiceQuestionIds;
    private BigDecimal singleChoiceQuestionScore;

    private String trueOrFalseQuestionIds;
    private BigDecimal trueOrFalseQuestionScore;

    private String fillInBlankQuestionIds;
    private BigDecimal fillInBlankQuestionScore;

    private String programQuestionIds;
    private BigDecimal programQuestionScore;

    private List<TestPaperSingleChoiceQuestion> singleChoiceQuestions;
    private List<TestPaperTrueOrFalseQuestion> trueOrFalseQuestions;
    private List<TestPaperFillInBlankQuestion> fillInBlankQuestions;
    private List<TestPaperProgramQuestion> programQuestions;

    private Map<Integer,String> singleChoiceQuestionAnswer;
    private Map<Integer,String> trueOrFalseQuestionAnswer;
    private Map<Integer,List> fillInBlankQuestionAnswer;
    private Map<Integer,String> programQuestionAnswer;

    private Map<Integer,BigDecimal> singleChoiceQuestionAnswerScore;
    private Map<Integer,BigDecimal> trueOrFalseQuestionAnswerScore;
    private Map<Integer,BigDecimal> fillInBlankQuestionAnswerScore;
    private Map<Integer,BigDecimal> programQuestionAnswerScore;

    public String getSingleChoiceQuestionIds() {
        return singleChoiceQuestionIds;
    }

    public void setSingleChoiceQuestionIds(String singleChoiceQuestionIds) {
        this.singleChoiceQuestionIds = singleChoiceQuestionIds;
    }

    public BigDecimal getSingleChoiceQuestionScore() {
        return singleChoiceQuestionScore;
    }

    public void setSingleChoiceQuestionScore(BigDecimal singleChoiceQuestionScore) {
        this.singleChoiceQuestionScore = singleChoiceQuestionScore;
    }

    public String getTrueOrFalseQuestionIds() {
        return trueOrFalseQuestionIds;
    }

    public void setTrueOrFalseQuestionIds(String trueOrFalseQuestionIds) {
        this.trueOrFalseQuestionIds = trueOrFalseQuestionIds;
    }

    public BigDecimal getTrueOrFalseQuestionScore() {
        return trueOrFalseQuestionScore;
    }

    public void setTrueOrFalseQuestionScore(BigDecimal trueOrFalseQuestionScore) {
        this.trueOrFalseQuestionScore = trueOrFalseQuestionScore;
    }

    public String getFillInBlankQuestionIds() {
        return fillInBlankQuestionIds;
    }

    public void setFillInBlankQuestionIds(String fillInBlankQuestionIds) {
        this.fillInBlankQuestionIds = fillInBlankQuestionIds;
    }

    public BigDecimal getFillInBlankQuestionScore() {
        return fillInBlankQuestionScore;
    }

    public void setFillInBlankQuestionScore(BigDecimal fillInBlankQuestionScore) {
        this.fillInBlankQuestionScore = fillInBlankQuestionScore;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolYearName() {
        return schoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        this.schoolYearName = schoolYearName;
    }

    public Integer getSingleChoiceQuestionNum() {
        return singleChoiceQuestionNum;
    }

    public void setSingleChoiceQuestionNum(Integer singleChoiceQuestionNum) {
        this.singleChoiceQuestionNum = singleChoiceQuestionNum;
    }

    public Integer getTrueOrFalseQuestionNum() {
        return trueOrFalseQuestionNum;
    }

    public void setTrueOrFalseQuestionNum(Integer trueOrFalseQuestionNum) {
        this.trueOrFalseQuestionNum = trueOrFalseQuestionNum;
    }

    public Integer getFillInBlankQuestionNum() {
        return fillInBlankQuestionNum;
    }

    public void setFillInBlankQuestionNum(Integer fillInBlankQuestionNum) {
        this.fillInBlankQuestionNum = fillInBlankQuestionNum;
    }

    public List<TestPaperSingleChoiceQuestion> getSingleChoiceQuestions() {
        return singleChoiceQuestions;
    }

    public void setSingleChoiceQuestions(List<TestPaperSingleChoiceQuestion> singleChoiceQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
    }

    public List<TestPaperTrueOrFalseQuestion> getTrueOrFalseQuestions() {
        return trueOrFalseQuestions;
    }

    public void setTrueOrFalseQuestions(List<TestPaperTrueOrFalseQuestion> trueOrFalseQuestions) {
        this.trueOrFalseQuestions = trueOrFalseQuestions;
    }

    public List<TestPaperFillInBlankQuestion> getFillInBlankQuestions() {
        return fillInBlankQuestions;
    }

    public void setFillInBlankQuestions(List<TestPaperFillInBlankQuestion> fillInBlankQuestions) {
        this.fillInBlankQuestions = fillInBlankQuestions;
    }

    public Map<Integer, String> getSingleChoiceQuestionAnswer() {
        return singleChoiceQuestionAnswer;
    }

    public void setSingleChoiceQuestionAnswer(Map<Integer, String> singleChoiceQuestionAnswer) {
        this.singleChoiceQuestionAnswer = singleChoiceQuestionAnswer;
    }

    public Map<Integer, String> getTrueOrFalseQuestionAnswer() {
        return trueOrFalseQuestionAnswer;
    }

    public void setTrueOrFalseQuestionAnswer(Map<Integer, String> trueOrFalseQuestionAnswer) {
        this.trueOrFalseQuestionAnswer = trueOrFalseQuestionAnswer;
    }

    public Map<Integer, List> getFillInBlankQuestionAnswer() {
        return fillInBlankQuestionAnswer;
    }

    public void setFillInBlankQuestionAnswer(Map<Integer, List> fillInBlankQuestionAnswer) {
        this.fillInBlankQuestionAnswer = fillInBlankQuestionAnswer;
    }

    public Map<Integer, BigDecimal> getSingleChoiceQuestionAnswerScore() {
        return singleChoiceQuestionAnswerScore;
    }

    public void setSingleChoiceQuestionAnswerScore(Map<Integer, BigDecimal> singleChoiceQuestionAnswerScore) {
        this.singleChoiceQuestionAnswerScore = singleChoiceQuestionAnswerScore;
    }

    public Map<Integer, BigDecimal> getTrueOrFalseQuestionAnswerScore() {
        return trueOrFalseQuestionAnswerScore;
    }

    public void setTrueOrFalseQuestionAnswerScore(Map<Integer, BigDecimal> trueOrFalseQuestionAnswerScore) {
        this.trueOrFalseQuestionAnswerScore = trueOrFalseQuestionAnswerScore;
    }

    public Map<Integer, BigDecimal> getFillInBlankQuestionAnswerScore() {
        return fillInBlankQuestionAnswerScore;
    }

    public void setFillInBlankQuestionAnswerScore(Map<Integer, BigDecimal> fillInBlankQuestionAnswerScore) {
        this.fillInBlankQuestionAnswerScore = fillInBlankQuestionAnswerScore;
    }

    public Integer getProgramQuestionNum() {
        return programQuestionNum;
    }

    public void setProgramQuestionNum(Integer programQuestionNum) {
        this.programQuestionNum = programQuestionNum;
    }

    public String getProgramQuestionIds() {
        return programQuestionIds;
    }

    public void setProgramQuestionIds(String programQuestionIds) {
        this.programQuestionIds = programQuestionIds;
    }

    public BigDecimal getProgramQuestionScore() {
        return programQuestionScore;
    }

    public void setProgramQuestionScore(BigDecimal programQuestionScore) {
        this.programQuestionScore = programQuestionScore;
    }

    public List<TestPaperProgramQuestion> getProgramQuestions() {
        return programQuestions;
    }

    public void setProgramQuestions(List<TestPaperProgramQuestion> programQuestions) {
        this.programQuestions = programQuestions;
    }

    public Map<Integer, String> getProgramQuestionAnswer() {
        return programQuestionAnswer;
    }

    public void setProgramQuestionAnswer(Map<Integer, String> programQuestionAnswer) {
        this.programQuestionAnswer = programQuestionAnswer;
    }

    public Map<Integer, BigDecimal> getProgramQuestionAnswerScore() {
        return programQuestionAnswerScore;
    }

    public void setProgramQuestionAnswerScore(Map<Integer, BigDecimal> programQuestionAnswerScore) {
        this.programQuestionAnswerScore = programQuestionAnswerScore;
    }
}
