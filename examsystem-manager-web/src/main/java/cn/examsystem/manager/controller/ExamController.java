package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.rest.pojo.dto.StudentDto;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.pojo.po.ExamStudentRelation;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.pojo.vo.StudentVo;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import static cn.examsystem.common.utils.UrlUtils.expandURL;

/**
 * Created by Administrator on 2018/1/28.
 * 考试控制器类
 */
@RestController
public class ExamController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${EXAM_URL}")
    private String EXAM_URL;
    @Value("${EXAM_STUDENT_URL}")
    private String EXAM_STUDENT_URL;
    @Value("${EXAM_FILE_URL}")
    private String EXAM_FILE_URL;
    @Value("${EXAMSTUDENT_URL}")
    private String EXAMSTUDENT_URL;
    @Value("${EXAMSTUDENT_EXAM_URL}")
    private String EXAMSTUDENT_EXAM_URL;
    @Value("${EXAM_STATUS_URL}")
    private String EXAM_STATUS_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @GetMapping("/v1/exam")
    public ResultInfo listExam(Exam exam) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(exam);
            String url = expandURL(REST_BASE_URL + EXAM_URL+"?", obj);

            System.out.print(url);

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/exam/{id}")
    public ResultInfo getExam(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}", HttpMethod.GET, ResultInfo.class,new Object[]{id});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/exam")
    public ResultInfo btchDeleteExam(@RequestParam(value = "ids[]") String[] ids) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL,HttpMethod.DELETE,ids,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/exam")
    public ResultInfo saveExam(Exam exam, HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        exam.setCreatedTeacherId(sysuserDto.getId());
        System.out.println(exam.getStartTime());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL,HttpMethod.POST,exam,ResultInfo.class,new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/{id}")
    public ResultInfo updateExam(@PathVariable String id, Exam exam) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}",HttpMethod.PUT,exam,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/exam/{id}/student")
    public ResultInfo addStudentForExam(@PathVariable String id,StudentDto studentDto) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}"+EXAM_STUDENT_URL,HttpMethod.POST,studentDto,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/exam/{id}/students")
    public ResultInfo addStudentsForExam(@PathVariable String id,@RequestParam(value = "studentIds[]") String[] studentIds) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}"+EXAM_STUDENT_URL+"s",HttpMethod.POST,studentIds,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/exam/{id}/student/file")
    public ResultInfo addStudentForExamByExcel(@PathVariable String id, MultipartFile upload) throws Exception{

        System.out.println(upload.getOriginalFilename());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}"+EXAM_STUDENT_URL+EXAM_FILE_URL+"?fileName="+upload.getOriginalFilename(),HttpMethod.POST,upload.getBytes(),ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }


    @GetMapping("/v1/exam/{id}/student")
    public ResultInfo listExamStudent(@PathVariable String id, ExamStudentRelationVo examStudentRelationVo) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(examStudentRelationVo);
            String url = expandURL(REST_BASE_URL + EXAM_URL+"/{id}"+EXAM_STUDENT_URL+"?", obj);

            System.out.print(url);

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{id});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/exam/student/{examStudentRelationId}")
    public ResultInfo removeStudentFromExam(@PathVariable String examStudentRelationId) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+EXAM_STUDENT_URL+"/{examStudentRelationId}",HttpMethod.DELETE,ResultInfo.class,new Object[]{examStudentRelationId});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/student/{examStudentRelationId}")
    public ResultInfo updateExamStudentPartOrder(@PathVariable String examStudentRelationId, ExamStudentRelation examStudentRelation) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+EXAM_STUDENT_URL+"/{examStudentRelationId}",HttpMethod.PUT,examStudentRelation,ResultInfo.class,new Object[]{examStudentRelationId});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/examStudent/{id}")
    public ResultInfo updateStudent(@PathVariable String id, Student student) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAMSTUDENT_URL+"/{id}",HttpMethod.PUT,student,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }


    @GetMapping("/v1/examStudent/exam/{id}")
    public ResultInfo listStudentNoExistExam(@PathVariable String id, StudentVo studentVo) throws Exception{

        ResultInfo resultInfo;
        try{
            //将查询参数构建在url后面
            JSONObject obj=new JSONObject(studentVo);
            String url = expandURL(REST_BASE_URL+EXAMSTUDENT_URL+EXAMSTUDENT_EXAM_URL+"/{id}"+"?", obj);

            System.out.print(url);

            //调用rest服务
            resultInfo = RestTemplateUtils.exchange(url, HttpMethod.GET, ResultInfo.class,new Object[]{id});
            System.out.println("---------"+resultInfo);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/{id}/status")
    public ResultInfo startExam(@PathVariable String id) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+EXAM_URL+"/{id}"+EXAM_STATUS_URL,HttpMethod.PUT,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

}
