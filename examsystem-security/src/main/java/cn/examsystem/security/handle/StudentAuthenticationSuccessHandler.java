package cn.examsystem.security.handle;

import cn.examsystem.rest.mapper.StudentMapper;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.security.pojo.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/26.
 * 自定义认证成功处理器
 */
public class StudentAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    private StudentMapper studentMapper;

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
    }
}
