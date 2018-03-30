package cn.examsystem.rest.pojo.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExamExample() {
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

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(String value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(String value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(String value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(String value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(String value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(String value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLike(String value) {
            addCriterion("course_id like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotLike(String value) {
            addCriterion("course_id not like", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<String> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<String> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(String value1, String value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(String value1, String value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdIsNull() {
            addCriterion("test_paper_id is null");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdIsNotNull() {
            addCriterion("test_paper_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdEqualTo(String value) {
            addCriterion("test_paper_id =", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdNotEqualTo(String value) {
            addCriterion("test_paper_id <>", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdGreaterThan(String value) {
            addCriterion("test_paper_id >", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdGreaterThanOrEqualTo(String value) {
            addCriterion("test_paper_id >=", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdLessThan(String value) {
            addCriterion("test_paper_id <", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdLessThanOrEqualTo(String value) {
            addCriterion("test_paper_id <=", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdLike(String value) {
            addCriterion("test_paper_id like", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdNotLike(String value) {
            addCriterion("test_paper_id not like", value, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdIn(List<String> values) {
            addCriterion("test_paper_id in", values, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdNotIn(List<String> values) {
            addCriterion("test_paper_id not in", values, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdBetween(String value1, String value2) {
            addCriterion("test_paper_id between", value1, value2, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTestPaperIdNotBetween(String value1, String value2) {
            addCriterion("test_paper_id not between", value1, value2, "testPaperId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdIsNull() {
            addCriterion("school_year_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdIsNotNull() {
            addCriterion("school_year_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdEqualTo(String value) {
            addCriterion("school_year_id =", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdNotEqualTo(String value) {
            addCriterion("school_year_id <>", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdGreaterThan(String value) {
            addCriterion("school_year_id >", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdGreaterThanOrEqualTo(String value) {
            addCriterion("school_year_id >=", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdLessThan(String value) {
            addCriterion("school_year_id <", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdLessThanOrEqualTo(String value) {
            addCriterion("school_year_id <=", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdLike(String value) {
            addCriterion("school_year_id like", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdNotLike(String value) {
            addCriterion("school_year_id not like", value, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdIn(List<String> values) {
            addCriterion("school_year_id in", values, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdNotIn(List<String> values) {
            addCriterion("school_year_id not in", values, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdBetween(String value1, String value2) {
            addCriterion("school_year_id between", value1, value2, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andSchoolYearIdNotBetween(String value1, String value2) {
            addCriterion("school_year_id not between", value1, value2, "schoolYearId");
            return (Criteria) this;
        }

        public Criteria andTermIsNull() {
            addCriterion("term is null");
            return (Criteria) this;
        }

        public Criteria andTermIsNotNull() {
            addCriterion("term is not null");
            return (Criteria) this;
        }

        public Criteria andTermEqualTo(Integer value) {
            addCriterion("term =", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotEqualTo(Integer value) {
            addCriterion("term <>", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThan(Integer value) {
            addCriterion("term >", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThanOrEqualTo(Integer value) {
            addCriterion("term >=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThan(Integer value) {
            addCriterion("term <", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThanOrEqualTo(Integer value) {
            addCriterion("term <=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermIn(List<Integer> values) {
            addCriterion("term in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotIn(List<Integer> values) {
            addCriterion("term not in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermBetween(Integer value1, Integer value2) {
            addCriterion("term between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotBetween(Integer value1, Integer value2) {
            addCriterion("term not between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdIsNull() {
            addCriterion("created_teacher_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdIsNotNull() {
            addCriterion("created_teacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdEqualTo(String value) {
            addCriterion("created_teacher_id =", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdNotEqualTo(String value) {
            addCriterion("created_teacher_id <>", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdGreaterThan(String value) {
            addCriterion("created_teacher_id >", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdGreaterThanOrEqualTo(String value) {
            addCriterion("created_teacher_id >=", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdLessThan(String value) {
            addCriterion("created_teacher_id <", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdLessThanOrEqualTo(String value) {
            addCriterion("created_teacher_id <=", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdLike(String value) {
            addCriterion("created_teacher_id like", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdNotLike(String value) {
            addCriterion("created_teacher_id not like", value, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdIn(List<String> values) {
            addCriterion("created_teacher_id in", values, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdNotIn(List<String> values) {
            addCriterion("created_teacher_id not in", values, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdBetween(String value1, String value2) {
            addCriterion("created_teacher_id between", value1, value2, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andCreatedTeacherIdNotBetween(String value1, String value2) {
            addCriterion("created_teacher_id not between", value1, value2, "createdTeacherId");
            return (Criteria) this;
        }

        public Criteria andPartNumIsNull() {
            addCriterion("part_num is null");
            return (Criteria) this;
        }

        public Criteria andPartNumIsNotNull() {
            addCriterion("part_num is not null");
            return (Criteria) this;
        }

        public Criteria andPartNumEqualTo(Integer value) {
            addCriterion("part_num =", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumNotEqualTo(Integer value) {
            addCriterion("part_num <>", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumGreaterThan(Integer value) {
            addCriterion("part_num >", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("part_num >=", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumLessThan(Integer value) {
            addCriterion("part_num <", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumLessThanOrEqualTo(Integer value) {
            addCriterion("part_num <=", value, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumIn(List<Integer> values) {
            addCriterion("part_num in", values, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumNotIn(List<Integer> values) {
            addCriterion("part_num not in", values, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumBetween(Integer value1, Integer value2) {
            addCriterion("part_num between", value1, value2, "partNum");
            return (Criteria) this;
        }

        public Criteria andPartNumNotBetween(Integer value1, Integer value2) {
            addCriterion("part_num not between", value1, value2, "partNum");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIsNull() {
            addCriterion("interval_time is null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIsNotNull() {
            addCriterion("interval_time is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeEqualTo(Integer value) {
            addCriterion("interval_time =", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotEqualTo(Integer value) {
            addCriterion("interval_time <>", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThan(Integer value) {
            addCriterion("interval_time >", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("interval_time >=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThan(Integer value) {
            addCriterion("interval_time <", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeLessThanOrEqualTo(Integer value) {
            addCriterion("interval_time <=", value, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeIn(List<Integer> values) {
            addCriterion("interval_time in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotIn(List<Integer> values) {
            addCriterion("interval_time not in", values, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeBetween(Integer value1, Integer value2) {
            addCriterion("interval_time between", value1, value2, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andIntervalTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("interval_time not between", value1, value2, "intervalTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Integer value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Integer value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Integer value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Integer value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Integer value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Integer> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Integer> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Integer value1, Integer value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andExamPwdIsNull() {
            addCriterion("exam_pwd is null");
            return (Criteria) this;
        }

        public Criteria andExamPwdIsNotNull() {
            addCriterion("exam_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andExamPwdEqualTo(String value) {
            addCriterion("exam_pwd =", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdNotEqualTo(String value) {
            addCriterion("exam_pwd <>", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdGreaterThan(String value) {
            addCriterion("exam_pwd >", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdGreaterThanOrEqualTo(String value) {
            addCriterion("exam_pwd >=", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdLessThan(String value) {
            addCriterion("exam_pwd <", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdLessThanOrEqualTo(String value) {
            addCriterion("exam_pwd <=", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdLike(String value) {
            addCriterion("exam_pwd like", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdNotLike(String value) {
            addCriterion("exam_pwd not like", value, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdIn(List<String> values) {
            addCriterion("exam_pwd in", values, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdNotIn(List<String> values) {
            addCriterion("exam_pwd not in", values, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdBetween(String value1, String value2) {
            addCriterion("exam_pwd between", value1, value2, "examPwd");
            return (Criteria) this;
        }

        public Criteria andExamPwdNotBetween(String value1, String value2) {
            addCriterion("exam_pwd not between", value1, value2, "examPwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdIsNull() {
            addCriterion("invigilate_pwd is null");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdIsNotNull() {
            addCriterion("invigilate_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdEqualTo(String value) {
            addCriterion("invigilate_pwd =", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdNotEqualTo(String value) {
            addCriterion("invigilate_pwd <>", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdGreaterThan(String value) {
            addCriterion("invigilate_pwd >", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdGreaterThanOrEqualTo(String value) {
            addCriterion("invigilate_pwd >=", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdLessThan(String value) {
            addCriterion("invigilate_pwd <", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdLessThanOrEqualTo(String value) {
            addCriterion("invigilate_pwd <=", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdLike(String value) {
            addCriterion("invigilate_pwd like", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdNotLike(String value) {
            addCriterion("invigilate_pwd not like", value, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdIn(List<String> values) {
            addCriterion("invigilate_pwd in", values, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdNotIn(List<String> values) {
            addCriterion("invigilate_pwd not in", values, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdBetween(String value1, String value2) {
            addCriterion("invigilate_pwd between", value1, value2, "invigilatePwd");
            return (Criteria) this;
        }

        public Criteria andInvigilatePwdNotBetween(String value1, String value2) {
            addCriterion("invigilate_pwd not between", value1, value2, "invigilatePwd");
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