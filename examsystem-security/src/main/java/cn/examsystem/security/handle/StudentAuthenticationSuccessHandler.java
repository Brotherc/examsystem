package cn.examsystem.security.handle;

import cn.examsystem.common.utils.DateUtil;
import cn.examsystem.rest.mapper.ExamMapper;
import cn.examsystem.rest.mapper.ExamStudentRelationMapper;
import cn.examsystem.rest.mapper.StudentMapper;
import cn.examsystem.rest.pojo.po.Exam;
import cn.examsystem.rest.pojo.po.ExamStudentRelation;
import cn.examsystem.rest.pojo.po.ExamStudentRelationExample;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.security.pojo.dto.StudentDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 * 自定义认证成功处理器
 */
public class StudentAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ExamStudentRelationMapper examStudentRelationMapper;
    @Autowired
    private ExamMapper examMapper;

    @Value("${DICTINFO_STUDENT_EXAM_NOT_START_CODE}")
    private String DICTINFO_STUDENT_EXAM_NOT_START_CODE;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        StudentDto studentDto=(StudentDto)authentication.getPrincipal();

        System.out.println(studentDto.getId());
        System.out.println(request.getRemoteAddr());

        Student student = studentMapper.selectByPrimaryKey(studentDto.getId());

        //修改学生最后登录时间和ip
        student.setLastLoginTime(new Date());
        student.setLastLoginIp(request.getRemoteAddr());
        student.setUpdatedTime(new Date());

        studentMapper.updateByPrimaryKey(student);

        //查询学生当天是否有待考考试，如果有则设置不显示考试模块，否则显示
        //查询该学生的所有未考考试
        ExamStudentRelationExample examStudentRelationExample=new ExamStudentRelationExample();
        ExamStudentRelationExample.Criteria relationCriteria = examStudentRelationExample.createCriteria();
        relationCriteria.andStudentIdEqualTo(student.getId());
        relationCriteria.andStatusEqualTo(new Integer(DICTINFO_STUDENT_EXAM_NOT_START_CODE));//未考
        List<ExamStudentRelation> examStudentRelationList = examStudentRelationMapper.selectByExample(examStudentRelationExample);

        if(CollectionUtils.isEmpty(examStudentRelationList))//没有考试
            studentDto.setShowPracticeModule(true);
        else{
            studentDto.setShowPracticeModule(true);
            for(ExamStudentRelation relation:examStudentRelationList){//遍历所有待考考试
                Exam examDb = examMapper.selectByPrimaryKey(relation.getExamId());
                Date startTime = examDb.getStartTime();

                Date today=new Date();
                today.setHours(0);
                today.setMinutes(0);
                today.setSeconds(0);

                Date next= DateUtil.nextDay(today);


                if(startTime.compareTo(today)>0&&startTime.compareTo(next)<0){//考试在当天
                   studentDto.setShowPracticeModule(false);
                   break;
                }

            }
        }
    }
}
