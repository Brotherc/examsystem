package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.DateUtil;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.GradeVo;
import cn.examsystem.rest.pojo.vo.SchoolYearVo;
import cn.examsystem.rest.pojo.vo.SysuserVo;
import cn.examsystem.rest.pojo.vo.TestPaperVo;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.examsystem.common.utils.UrlUtils.expandURL;

/**
 * Created by Administrator on 2018/1/25.
 * 页面跳转类
 */
@Controller
public class PageController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${DEPARTMENT_URL}")
    private String DEPARTMENT_URL;
    @Value("${MAJOR_URL}")
    private String MAJOR_URL;
    @Value("${SYSUSER_URL}")
    private String SYSUSER_URL;
    @Value("${GRADE_URL}")
    private String GRADE_URL;
    @Value("${ROLE_URL}")
    private String ROLE_URL;
    @Value("${COURSE_URL}")
    private String COURSE_URL;
    @Value("${DICTINFO_URL}")
    private String DICTINFO_URL;
    @Value("${SCHOOL_YEAR_URL}")
    private String SCHOOL_YEAR_URL;
    @Value("${TESTPAPER_URL}")
    private String TESTPAPER_URL;
    @Value("${CLASS_URL}")
    private String CLASS_URL;

    @Value("${ROLE_TEACHER_ID}")
    private String ROLE_TEACHER_ID;
    @Value("${DICT_TYPE_DIFFICULTY_ID}")
    private String DICT_TYPE_DIFFICULTY_ID;
    @Value("${DICT_TYPE_MATCHER_ID}")
    private String DICT_TYPE_MATCHER_ID;
    @Value("${ROLE_CHECKER_CODE}")
    private String ROLE_CHECKER_CODE;
    @Value("${DICT_TYPE_QUESTIONTYPE_ID}")
    private String DICT_TYPE_QUESTIONTYPE_ID;
    @Value("${DICT_TYPE_STATUS_ID}")
    private String DICT_TYPE_STATUS_ID;
    @Value("${DICT_TYPE_EXAMSTUDENT_STATUS_ID}")
    private String DICT_TYPE_EXAMSTUDENT_STATUS_ID;


    @Value("${MODEL_KEY_DEPARTMENTS}")
    private String MODEL_KEY_DEPARTMENTS;
    @Value("${MODEL_KEY_MAJORS}")
    private String MODEL_KEY_MAJORS;
    @Value("${MODEL_KEY_TEACHERS}")
    private String MODEL_KEY_TEACHERS;
    @Value("${MODEL_KEY_GRADES}")
    private String MODEL_KEY_GRADES;
    @Value("${MODEL_KEY_ROLES}")
    private String MODEL_KEY_ROLES;
    @Value("${MODEL_KEY_COURSES}")
    private String MODEL_KEY_COURSES;
    @Value("${MODEL_KEY_COURSE}")
    private String MODEL_KEY_COURSE;
    @Value("${MODEL_KEY_DIFFICULTYS}")
    private String MODEL_KEY_DIFFICULTYS;
    @Value("${MODEL_KEY_MATCHERS}")
    private String MODEL_KEY_MATCHERS;
    @Value("${MODEL_KEY_CHECKER_CODE}")
    private String MODEL_KEY_CHECKER_CODE;
    @Value("${MODEL_KEY_QUESTIONTYPES}")
    private String MODEL_KEY_QUESTIONTYPES;
    @Value("${MODEL_KEY_SCHOOLYEARS}")
    private String MODEL_KEY_SCHOOLYEARS;
    @Value("${MODEL_KEY_STATUSES}")
    private String MODEL_KEY_STATUSES;
    @Value("${MODEL_KEY_TESTPAPERS}")
    private String MODEL_KEY_TESTPAPERS;
    @Value("${MODEL_KEY_CLASSES}")
    private String MODEL_KEY_CLASSES;


    @RequestMapping("/v1/department/list")
    public String toDepartmentPage() throws Exception{
        System.out.println("走department");
        return "department/list";
    }
    @RequestMapping("/v1/major/list")
    public String toMajorPage(Model model) throws Exception{
        //前台搜索用到的条件（系）
        List<Department> departmentList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+DEPARTMENT_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            departmentList=(List<Department>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存到前台
        model.addAttribute(MODEL_KEY_DEPARTMENTS,departmentList);
        return "major/list";
    }
    @RequestMapping("/v1/course/list")
    public String toCoursePage(Model model) throws Exception{
        //前台搜索用到的条件（专业）
        List<Major> majorList=null;
        try {
            //调用rest服务(查询可用的教师用户)
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+MAJOR_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            majorList=(List<Major>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（教师）
        List<Sysuser> teacherList=null;
        //构造查询条件
        SysuserVo sysuserVo=new SysuserVo();;
        sysuserVo.setRoleId(ROLE_TEACHER_ID);
        sysuserVo.setStatus(1);
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(sysuserVo);
        String url = expandURL(REST_BASE_URL + SYSUSER_URL+"?", obj);
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            teacherList=(List<Sysuser>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_MAJORS,majorList);
        model.addAttribute(MODEL_KEY_TEACHERS,teacherList);
        return "course/list";
    }

    @RequestMapping("/v1/class/list")
    public String toClassPage(Model model) throws Exception{

        //前台搜索用到的条件（系）
        List<Department> departmentList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+DEPARTMENT_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            departmentList=(List<Department>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（专业）
        List<Major> majorList=null;
        try {
            //调用rest服务(查询可用的教师用户)
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+MAJOR_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            majorList=(List<Major>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（年级）
        List<Grade> gradeList=null;

        //构造查询条件
        GradeVo gradeVo=new GradeVo();
        gradeVo.setLessName(String.valueOf(DateUtil.getYear(new Date())));
        System.out.println(String.valueOf(DateUtil.getYear(new Date())));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(gradeVo);
        String url = expandURL(REST_BASE_URL + GRADE_URL+"?", obj);

        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            gradeList=(List<Grade>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_DEPARTMENTS,departmentList);
        model.addAttribute(MODEL_KEY_MAJORS,majorList);
        model.addAttribute(MODEL_KEY_GRADES,gradeList);

        return "class/list";
    }

    @RequestMapping("/v1/sysuser/list")
    public String toSysuserPage(Model model) throws Exception{

        //前台搜索用到的条件（系）
        List<Department> departmentList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+DEPARTMENT_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            departmentList=(List<Department>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（角色）
        List<Role> roleList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+ROLE_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            roleList=(List<Role>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_DEPARTMENTS,departmentList);
        model.addAttribute(MODEL_KEY_ROLES,roleList);

        return "sysuser/list";
    }

    @RequestMapping("/v1/knowledgePoint/list")
    public String toLabelPage(Model model, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));

        return "knowledgePonit/list";
    }

    @RequestMapping("/v1/singleChoiceQuestion/list")
    public String toSingleChoiceQuestionPage(Model model,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        for(GrantedAuthority authority:authorities){
            if(StringUtils.equals(ROLE_CHECKER_CODE,authority.getAuthority())){
                model.addAttribute(MODEL_KEY_CHECKER_CODE,ROLE_CHECKER_CODE);
                break;
            }
        }

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo dictInfo=new DictInfo();
        dictInfo.setDictTypeId(DICT_TYPE_DIFFICULTY_ID);
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(dictInfo);
        String url = expandURL(REST_BASE_URL + DICTINFO_URL+"?", obj);

        //前台搜索用到的条件（难度）
        List<DictInfo> difficultyList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            difficultyList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_DIFFICULTYS,difficultyList);

        return "singleChoiceQuestion/list";
    }

    @RequestMapping("/v1/trueOrFalseQuestion/list")
    public String toTrueOrFalseQuestionPage(Model model,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        for(GrantedAuthority authority:authorities){
            if(StringUtils.equals(ROLE_CHECKER_CODE,authority.getAuthority())){
                model.addAttribute(MODEL_KEY_CHECKER_CODE,ROLE_CHECKER_CODE);
                break;
            }
        }

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo dictInfo=new DictInfo();
        dictInfo.setDictTypeId(DICT_TYPE_DIFFICULTY_ID);
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(dictInfo);
        String url = expandURL(REST_BASE_URL + DICTINFO_URL+"?", obj);

        //前台搜索用到的条件（难度）
        List<DictInfo> difficultyList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            difficultyList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_DIFFICULTYS,difficultyList);

        return "trueOrFalseQuestion/list";
    }

    @RequestMapping("/v1/fillInBlankQuestion/list")
    public String toFillInBlankQuestionPage(Model model,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        for(GrantedAuthority authority:authorities){
            if(StringUtils.equals(ROLE_CHECKER_CODE,authority.getAuthority())){
                model.addAttribute(MODEL_KEY_CHECKER_CODE,ROLE_CHECKER_CODE);
                break;
            }
        }

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo difficultyDictInfo=new DictInfo();
        difficultyDictInfo.setDictTypeId(DICT_TYPE_DIFFICULTY_ID);
        //将查询参数构建在url后面
        JSONObject difficultyObj=new JSONObject(difficultyDictInfo);
        String difficultyUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", difficultyObj);

        //前台搜索用到的条件（难度）
        List<DictInfo> difficultyList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(difficultyUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            difficultyList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo matcherDictInfo=new DictInfo();
        matcherDictInfo.setDictTypeId(DICT_TYPE_MATCHER_ID);
        //将查询参数构建在url后面
        JSONObject matcherObj=new JSONObject(matcherDictInfo);
        String matcherUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", matcherObj);

        //前台搜索用到的条件（填空题答案匹配模式）
        List<DictInfo> matcherList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(matcherUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            matcherList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_DIFFICULTYS,difficultyList);
        model.addAttribute(MODEL_KEY_MATCHERS,matcherList);

        return "fillInBlankQuestion/list";
    }

    @RequestMapping("/v1/testPaper/question/list")
    public String toTestpaperQuestionPage(HttpSession session,Model model) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo difficultyDictInfo=new DictInfo();
        difficultyDictInfo.setDictTypeId(DICT_TYPE_DIFFICULTY_ID);
        //将查询参数构建在url后面
        JSONObject difficultyObj=new JSONObject(difficultyDictInfo);
        String difficultyUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", difficultyObj);

        //前台搜索用到的条件（难度）
        List<DictInfo> difficultyList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(difficultyUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            difficultyList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }


        //构造查询条件
        DictInfo questionTypeDictInfo=new DictInfo();
        questionTypeDictInfo.setDictTypeId(DICT_TYPE_QUESTIONTYPE_ID);
        //将查询参数构建在url后面
        JSONObject questionTypeObj=new JSONObject(questionTypeDictInfo);
        String questionTypeUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", questionTypeObj);

        //前台数据展示
        List<DictInfo> questionTypeList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(questionTypeUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            questionTypeList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        SchoolYearVo schoolYearVo=new SchoolYearVo();
        int year=DateUtil.getYear(new Date());
        schoolYearVo.setLessName(year+"-"+(year+1));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(schoolYearVo);
        String url = expandURL(REST_BASE_URL + SCHOOL_YEAR_URL+"?", obj);

        //前台组卷用到的数据（学年）
        List<SchoolYear> schoolYearList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            schoolYearList=(List<SchoolYear>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }


        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_DIFFICULTYS,difficultyList);
        model.addAttribute(MODEL_KEY_QUESTIONTYPES,questionTypeList);
        model.addAttribute(MODEL_KEY_SCHOOLYEARS,schoolYearList);

        return "testPaper/question/list";
    }

    @RequestMapping("/v1/testPaper/list")
    public String toTestPaperPage(HttpSession session ,Model model) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        SchoolYearVo schoolYearVo=new SchoolYearVo();
        int year=DateUtil.getYear(new Date());
        schoolYearVo.setLessName(year+"-"+(year+1));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(schoolYearVo);
        String url = expandURL(REST_BASE_URL + SCHOOL_YEAR_URL+"?", obj);

        //前台组卷用到的数据（学年）
        List<SchoolYear> schoolYearList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            schoolYearList=(List<SchoolYear>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_SCHOOLYEARS,schoolYearList);

        return "testPaper/list";
    }

    @RequestMapping("/v1/exam/list")
    public String toExamPage(HttpSession session,Model model) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        SchoolYearVo schoolYearVo=new SchoolYearVo();
        int year=DateUtil.getYear(new Date());
        schoolYearVo.setLessName(year+"-"+(year+1));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(schoolYearVo);
        String url = expandURL(REST_BASE_URL + SCHOOL_YEAR_URL+"?", obj);

        //前台组卷用到的数据（学年）
        List<SchoolYear> schoolYearList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            schoolYearList=(List<SchoolYear>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo statusDictInfo=new DictInfo();
        statusDictInfo.setDictTypeId(DICT_TYPE_STATUS_ID);
        //将查询参数构建在url后面
        JSONObject statusObj=new JSONObject(statusDictInfo);
        String statusUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", statusObj);

        //前台搜索用到的条件（考试状态）
        List<DictInfo> statusList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(statusUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            statusList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        TestPaperVo testPaperVo=new TestPaperVo();
        System.out.println(courseList);
        String courseId=((Map<String,String>)courseList.get(0)).get("id");
        testPaperVo.setCourseId(courseId);
        //将查询参数构建在url后面
        JSONObject testPaperObj=new JSONObject(testPaperVo);
        String testPaperUrl = expandURL(REST_BASE_URL + TESTPAPER_URL+"?", testPaperObj);

        //前台添加考试用到的数据（试卷）
        List<TestPaper> testPaperList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(testPaperUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            testPaperList=(List<TestPaper>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台添加考试学生用到的数据（班级）
        List<ClassDto> classList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+CLASS_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            classList=(List<ClassDto>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }


        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_SCHOOLYEARS,schoolYearList);
        model.addAttribute(MODEL_KEY_STATUSES,statusList);
        model.addAttribute(MODEL_KEY_TESTPAPERS,testPaperList);
        model.addAttribute(MODEL_KEY_CLASSES,classList);

        return "exam/list";
    }

    @RequestMapping("/v1/score/list")
    public String toScorePage(HttpSession session,Model model) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        SchoolYearVo schoolYearVo=new SchoolYearVo();
        int year=DateUtil.getYear(new Date());
        schoolYearVo.setLessName(year+"-"+(year+1));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(schoolYearVo);
        String url = expandURL(REST_BASE_URL + SCHOOL_YEAR_URL+"?", obj);

        //前台搜索用到的条件（学年）
        List<SchoolYear> schoolYearList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            schoolYearList=(List<SchoolYear>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（班级）
        List<ClassDto> classList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+CLASS_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            classList=(List<ClassDto>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //保存到前台
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_COURSE,courseList.get(0));
        model.addAttribute(MODEL_KEY_SCHOOLYEARS,schoolYearList);
        model.addAttribute(MODEL_KEY_CLASSES,classList);

        return "score/list";
    }

    @RequestMapping("/v1/student/list")
    public String toStudentPage(Model model) throws Exception{

        //前台搜索用到的条件（班级）
        List<ClassDto> classList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+CLASS_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            classList=(List<ClassDto>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute(MODEL_KEY_CLASSES,classList);

        return "student/list";
    }

    @RequestMapping("/v1/exam/invigilation")
    public String toInvigilationPage(Model model,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //前台搜索用到的条件（课程）
        List<Course> courseList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+COURSE_URL+"/teacher/{teacherId}", HttpMethod.GET, ResultInfo.class,new Object[]{sysuserDto.getId()});
            courseList=(List<Course>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        SchoolYearVo schoolYearVo=new SchoolYearVo();
        int year=DateUtil.getYear(new Date());
        schoolYearVo.setLessName(year+"-"+(year+1));
        //将查询参数构建在url后面
        JSONObject obj=new JSONObject(schoolYearVo);
        String url = expandURL(REST_BASE_URL + SCHOOL_YEAR_URL+"?", obj);

        //前台搜索用到的条件（学年）
        List<SchoolYear> schoolYearList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            schoolYearList=(List<SchoolYear>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //前台搜索用到的条件（班级）
        List<ClassDto> classList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+CLASS_URL, HttpMethod.GET, ResultInfo.class,new Object[]{});
            classList=(List<ClassDto>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        //构造查询条件
        DictInfo statusDictInfo=new DictInfo();
        statusDictInfo.setDictTypeId(DICT_TYPE_EXAMSTUDENT_STATUS_ID);
        //将查询参数构建在url后面
        JSONObject statusObj=new JSONObject(statusDictInfo);
        String statusUrl = expandURL(REST_BASE_URL + DICTINFO_URL+"?", statusObj);

        //前台搜索用到的条件（学生考试状态）
        List<DictInfo> statusList=null;
        try {
            //调用rest服务
            ResultInfo resultInfo = RestTemplateUtils.exchange(statusUrl, HttpMethod.GET, ResultInfo.class,new Object[]{});
            statusList=(List<DictInfo>) resultInfo.getData();
        }catch (Exception e){
            e.printStackTrace();
        }


        model.addAttribute(MODEL_KEY_CLASSES,classList);
        model.addAttribute(MODEL_KEY_STATUSES,statusList);
        model.addAttribute(MODEL_KEY_COURSES,courseList);
        model.addAttribute(MODEL_KEY_SCHOOLYEARS,schoolYearList);

        return "exam/invigilation";
    }
}
