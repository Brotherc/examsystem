package cn.examsystem.rest.pojo.po;

public class ProgramQuestionWithBLOBs extends ProgramQuestion {
    private String description;

    private String inputDescription;

    private String outputDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getInputDescription() {
        return inputDescription;
    }

    public void setInputDescription(String inputDescription) {
        this.inputDescription = inputDescription == null ? null : inputDescription.trim();
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription == null ? null : outputDescription.trim();
    }
}