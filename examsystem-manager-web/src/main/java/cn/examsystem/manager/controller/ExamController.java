package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.StudentDto;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.pojo.po.ExamStudentRelation;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.pojo.vo.StudentVo;
import cn.examsystem.rest.service.ExamService;
import cn.examsystem.rest.service.StudentService;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    @Value("${EXAM_INVIGILATION_URL}")
    private String EXAM_INVIGILATION_URL;

    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;
    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private ExamService examService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/v1/exam")
    public ResultInfo listExam(Exam exam) throws Exception{

        ResultInfo resultInfo;
        try{

            //调用rest服务
            List<ExamDto> examDtoList = examService.listExam(exam);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examDtoList);
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
            ExamDto exam = examService.getExam(id);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,exam);
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
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
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
            resultInfo=examService.saveExam(exam);
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
            resultInfo=examService.updateExam(id,exam);
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
            resultInfo=examService.addStudentForExam(id,studentDto);
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
            resultInfo=examService.addStudentsForExam(id,studentIds);
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
            resultInfo=examService.addStudentForExamByExcel(id,upload.getOriginalFilename(),upload.getBytes());
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


            //调用rest服务
            examStudentRelationVo=examStudentRelationVo==null?new ExamStudentRelationVo() : examStudentRelationVo;
            examStudentRelationVo.setExamId(id);

            List<ExamStudentRelationDto> examStudentRelationDtoList = examService.listExamStudent(examStudentRelationVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);
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
            resultInfo=examService.removeStudentFromExam(examStudentRelationId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/student/{examStudentRelationId}/partOrder")
    public ResultInfo updateExamStudentPartOrder(@PathVariable String examStudentRelationId, ExamStudentRelation examStudentRelation) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=examService.updateExamStudentPartOrder(examStudentRelationId,examStudentRelation.getPartOrder());
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/student/{examStudentRelationId}/status")
    public ResultInfo updateExamStudentStatus(@PathVariable String examStudentRelationId, ExamStudentRelation examStudentRelation) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=examService.updateExamStudentStatus(examStudentRelationId,examStudentRelation.getStatus());
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/exam/{examId}/student/{studentId}")
    public ResultInfo updateStudent(@PathVariable String examId,@PathVariable String studentId, Student student) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=examService.updateExamStudent(examId,studentId,student);
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

            //调用rest服务
            List<Student> studentList = examService.listStudentNoExistExam(id,studentVo);
            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,studentList);
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
            resultInfo=examService.startExam(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }

    @GetMapping("/v1/exam/{examId}/invigilation")
    public ResultInfo listInvigilationExamStudent(@PathVariable String examId, ExamStudentRelationVo examStudentRelationVo) throws Exception{

        ResultInfo resultInfo;
        try{


            //调用rest服务
            List<ExamStudentRelationDto> examStudentRelationDtoList = examService.listInvigilationExamStudent(examId, examStudentRelationVo);

            resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("---------"+"失败");
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

}
