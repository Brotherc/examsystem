package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.SingleChoiceQuestion;
import cn.examsystem.rest.pojo.po.SingleChoiceQuestionWithBLOBs;
import cn.examsystem.rest.pojo.po.Sysuser;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 * 单选题（包括知识点）
 */
public class SingleChoiceQuestionDto extends SingleChoiceQuestionWithBLOBs{

    private String courseName;
    private String difficultyName;
    private Sysuser createdTeacher;

    private List<String> knowledgePoints;

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
}
