package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.FillInBlankQuestion;
import cn.examsystem.rest.pojo.po.Sysuser;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 * 填空题（包括知识点）
 */
public class  FillInBlankQuestionDto extends FillInBlankQuestion {

    private String courseName;
    private String difficultyName;
    private Sysuser createdTeacher;

    private List<String> knowledgePoints;
    private List<String> matcherCodes;
    private List<String> matcherNames;

    private List<String> answerList;

    private String matchersCode;


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public Sysuser getCreatedTeacher() {
        return createdTeacher;
    }

    public void setCreatedTeacher(Sysuser createdTeacher) {
        this.createdTeacher = createdTeacher;
    }

    public List<String> getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(List<String> knowledgePoints) {
        this.knowledgePoints = knowledgePoints;
    }

    public List<String> getMatcherCodes() {
        return matcherCodes;
    }

    public void setMatcherCodes(List<String> matcherCodes) {
        this.matcherCodes = matcherCodes;
    }

    public List<String> getMatcherNames() {
        return matcherNames;
    }

    public void setMatcherNames(List<String> matcherNames) {
        this.matcherNames = matcherNames;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public String getMatchersCode() {
        return matchersCode;
    }

    public void setMatchersCode(String matchersCode) {
        this.matchersCode = matchersCode;
    }
}
