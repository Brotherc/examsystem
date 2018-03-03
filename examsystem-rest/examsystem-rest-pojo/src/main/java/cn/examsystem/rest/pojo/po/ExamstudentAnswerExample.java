package cn.examsystem.rest.pojo.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamstudentAnswerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExamstudentAnswerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdIsNull() {
            addCriterion("exam_student_id is null");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdIsNotNull() {
            addCriterion("exam_student_id is not null");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdEqualTo(String value) {
            addCriterion("exam_student_id =", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdNotEqualTo(String value) {
            addCriterion("exam_student_id <>", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdGreaterThan(String value) {
            addCriterion("exam_student_id >", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdGreaterThanOrEqualTo(String value) {
            addCriterion("exam_student_id >=", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdLessThan(String value) {
            addCriterion("exam_student_id <", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdLessThanOrEqualTo(String value) {
            addCriterion("exam_student_id <=", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdLike(String value) {
            addCriterion("exam_student_id like", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdNotLike(String value) {
            addCriterion("exam_student_id not like", value, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdIn(List<String> values) {
            addCriterion("exam_student_id in", values, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdNotIn(List<String> values) {
            addCriterion("exam_student_id not in", values, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdBetween(String value1, String value2) {
            addCriterion("exam_student_id between", value1, value2, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andExamStudentIdNotBetween(String value1, String value2) {
            addCriterion("exam_student_id not between", value1, value2, "examStudentId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdIsNull() {
            addCriterion("testpaper_question_id is null");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdIsNotNull() {
            addCriterion("testpaper_question_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdEqualTo(String value) {
            addCriterion("testpaper_question_id =", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdNotEqualTo(String value) {
            addCriterion("testpaper_question_id <>", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdGreaterThan(String value) {
            addCriterion("testpaper_question_id >", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdGreaterThanOrEqualTo(String value) {
            addCriterion("testpaper_question_id >=", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdLessThan(String value) {
            addCriterion("testpaper_question_id <", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdLessThanOrEqualTo(String value) {
            addCriterion("testpaper_question_id <=", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdLike(String value) {
            addCriterion("testpaper_question_id like", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdNotLike(String value) {
            addCriterion("testpaper_question_id not like", value, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdIn(List<String> values) {
            addCriterion("testpaper_question_id in", values, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdNotIn(List<String> values) {
            addCriterion("testpaper_question_id not in", values, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdBetween(String value1, String value2) {
            addCriterion("testpaper_question_id between", value1, value2, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andTestpaperQuestionIdNotBetween(String value1, String value2) {
            addCriterion("testpaper_question_id not between", value1, value2, "testpaperQuestionId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andIsGradedIsNull() {
            addCriterion("is_graded is null");
            return (Criteria) this;
        }

        public Criteria andIsGradedIsNotNull() {
            addCriterion("is_graded is not null");
            return (Criteria) this;
        }

        public Criteria andIsGradedEqualTo(Boolean value) {
            addCriterion("is_graded =", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedNotEqualTo(Boolean value) {
            addCriterion("is_graded <>", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedGreaterThan(Boolean value) {
            addCriterion("is_graded >", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_graded >=", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedLessThan(Boolean value) {
            addCriterion("is_graded <", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_graded <=", value, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedIn(List<Boolean> values) {
            addCriterion("is_graded in", values, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedNotIn(List<Boolean> values) {
            addCriterion("is_graded not in", values, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graded between", value1, value2, "isGraded");
            return (Criteria) this;
        }

        public Criteria andIsGradedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graded not between", value1, value2, "isGraded");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNull() {
            addCriterion("updated_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNotNull() {
            addCriterion("updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeEqualTo(Date value) {
            addCriterion("updated_time =", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotEqualTo(Date value) {
            addCriterion("updated_time <>", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThan(Date value) {
            addCriterion("updated_time >", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_time >=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThan(Date value) {
            addCriterion("updated_time <", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("updated_time <=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIn(List<Date> values) {
            addCriterion("updated_time in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotIn(List<Date> values) {
            addCriterion("updated_time not in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("updated_time between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("updated_time not between", value1, value2, "updatedTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}