package cn.examsystem.rest.pojo.po;

public class FillInBlankQuestionWithBLOBs extends FillInBlankQuestion {
    private String content;

    private String answer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}