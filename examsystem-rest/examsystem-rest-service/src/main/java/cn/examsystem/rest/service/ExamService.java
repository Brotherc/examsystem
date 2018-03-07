package cn.examsystem.rest.service;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.ExamDto;
import cn.examsystem.rest.pojo.dto.ExamStudentRelationDto;
import cn.examsystem.rest.pojo.dto.StudentDto;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.vo.ExamStudentRelationVo;
import cn.examsystem.rest.pojo.vo.StudentVo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 考试业务层接口
 */
public interface ExamService {

    //根据条件查询考试
    public List<ExamDto>listExam(Exam exam) throws Exception;

    //根据id查询考试
    public ExamDto getExam(String id) throws Exception;

    //添加考试
    public ResultInfo saveExam(Exam exam) throws Exception;

    //更新考试
    public ResultInfo updateExam(String id, Exam exam) throws Exception;

    //为考试添加学生
    public ResultInfo addStudentForExam(String id, StudentDto studentDto) throws Exception;

    public ResultInfo addStudentsForExam(String id, String[] studentIds) throws Exception;

    //为考试添加学生（Excel导入）
    public ResultInfo addStudentForExamByExcel(String id,String fileName, byte[] uploadData) throws Exception;



    //根据条件查询考试学生
    public List<ExamStudentRelationDto>listExamStudent(ExamStudentRelationVo examStudentRelationVo) throws Exception;

    //将学生从考试中移除
    public ResultInfo removeStudentFromExam(String examStudentRelationId) throws Exception;

    //修改考试学生场次
    public ResultInfo updateExamStudentPartOrder(String examStudentRelationId,Integer partOrdeer) throws Exception;

    //查询某门考试未添加的正常状态的学生
    public List<Student> listStudentNoExistExam(String examId,StudentVo studentVo) throws Exception;

    //启动考试
    public ResultInfo startExam(String id)throws Exception;



    //根据学生id查询正在进行的考试
    public ExamStudentRelationDto getProceedExamByLoginStudentId(String studentId) throws Exception;

    //考试
    public ResultInfo test(ExamStudentRelationDto examStudentRelationDto) throws Exception;

    //提交试卷
    public ResultInfo submitTestPape(ExamStudentRelationDto examStudentRelationDto,String testPaperId) throws Exception;

}
