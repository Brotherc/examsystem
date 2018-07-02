package cn.examsystem.rest.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 考试服务控制类
 */
@RestController
public class ExamController {

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
        List<ExamDto> examDtoList = examService.listExam(exam);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examDtoList);
        //System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }
    @GetMapping("/v1/exam/{id}")
    public ResultInfo getExam(@PathVariable String id) throws Exception{
        ExamDto exam = examService.getExam(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,exam);
        //System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }

    @DeleteMapping(value = "/v1/exam")
    public ResultInfo btchDeleteExam(@RequestBody String[] ids) throws Exception{

        for(String s:ids){
            //System.out.println(s);
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_DELETE_SUCCESS,null);
    }

    @PostMapping("/v1/exam")
    public ResultInfo saveExam(@RequestBody Exam exam) throws Exception{
        //System.out.println(exam.getStartTime());
        return examService.saveExam(exam);
    }

    @PutMapping("/v1/exam/{id}")
    public ResultInfo updateExam(@PathVariable String id,@RequestBody Exam exam) throws Exception{
        return examService.updateExam(id,exam);
    }

    @PostMapping("/v1/exam/{id}/student")
    public ResultInfo addStudentForExam(@PathVariable String id,@RequestBody StudentDto studentDto) throws Exception{
        return examService.addStudentForExam(id,studentDto);
    }

    @PostMapping("/v1/exam/{id}/students")
    public ResultInfo addStudentsForExam(@PathVariable String id,@RequestBody String[] studentIds) throws Exception{
        return examService.addStudentsForExam(id,studentIds);
    }

    @PostMapping("/v1/exam/{id}/student/file")
    public ResultInfo addStudentForExamByExcel(@PathVariable String id, @RequestBody byte[] uploadData,String fileName) throws Exception{
        return examService.addStudentForExamByExcel(id,fileName,uploadData);
    }

    @GetMapping("/v1/exam/{id}/student")
    public ResultInfo listExamStudent(@PathVariable String id, ExamStudentRelationVo examStudentRelationVo) throws Exception{
        examStudentRelationVo=examStudentRelationVo==null?new ExamStudentRelationVo() : examStudentRelationVo;
        examStudentRelationVo.setExamId(id);

        List<ExamStudentRelationDto> examStudentRelationDtoList = examService.listExamStudent(examStudentRelationVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);
        //System.out.println("rest调用成功，返回manager");

        return resultInfo;
    }

    @DeleteMapping("/v1/exam/student/{examStudentRelationId}")
    public ResultInfo removeStudentFromExam(@PathVariable String examStudentRelationId) throws Exception{
        return examService.removeStudentFromExam(examStudentRelationId);
    }

    @PutMapping("/v1/exam/student/{examStudentRelationId}/partOrder")
    public ResultInfo updateExamStudentPartOrder(@PathVariable String examStudentRelationId, @RequestBody ExamStudentRelation examStudentRelation) throws Exception{
        return examService.updateExamStudentPartOrder(examStudentRelationId,examStudentRelation.getPartOrder());
    }

    @PutMapping("/v1/exam/student/{examStudentRelationId}/status")
    public ResultInfo updateExamStudentStatus(@PathVariable String examStudentRelationId, @RequestBody ExamStudentRelation examStudentRelation) throws Exception{
        return examService.updateExamStudentStatus(examStudentRelationId,examStudentRelation.getStatus());
    }

    @PutMapping("/v1/exam/{examId}/student/{studentId}")
    public ResultInfo updateStudent(@PathVariable String examId,@PathVariable String studentId,@RequestBody Student student) throws Exception{
        return examService.updateExamStudent(examId,studentId,student);
    }

    @GetMapping("/v1/examStudent/exam/{id}")
    public ResultInfo listStudentNoExistExam(@PathVariable String id,StudentVo studentVo) throws Exception{
        List<Student> studentList = examService.listStudentNoExistExam(id,studentVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,studentList);

        return resultInfo;
    }

    @PutMapping("/v1/exam/{id}/status")
    public ResultInfo startExam(@PathVariable String id) throws Exception{
        return examService.startExam(id);
    }

    @GetMapping("/v1/exam/loginStudent/{studentId}")
    public ResultInfo getProceedExamByLoginStudentId(@PathVariable String studentId) throws Exception{
        return examService.getProceedExamByLoginStudentId(studentId);
    }

    @PostMapping("/v1/test")
    public ResultInfo test(@RequestBody ExamStudentRelationDto examStudentRelationDto) throws Exception{
        return examService.test(examStudentRelationDto);
    }

    @PostMapping("/v1/test/testPaper/{testPaperId}")
    public ResultInfo submitTestPaper(@RequestBody ExamStudentRelationDto examStudentRelationDto,@PathVariable String testPaperId) throws Exception{
        return examService.submitTestPaper(examStudentRelationDto,testPaperId);
    }

    @GetMapping("/v1/exam/{examId}/invigilation")
    public ResultInfo listInvigilationExamStudent(@PathVariable String examId,ExamStudentRelationVo examStudentRelationVo) throws Exception{
        List<ExamStudentRelationDto> examStudentRelationDtoList = examService.listInvigilationExamStudent(examId, examStudentRelationVo);

        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,examStudentRelationDtoList);

        return resultInfo;

    }
}
