package cn.examsystem.student.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.security.pojo.dto.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/1/29.
 * 首页控制器
 */
@RestController
public class IndexController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;

    //判断学生用户练习模块是否显示
    @GetMapping("/v1/user/details/module")
    public ResultInfo showPracticeModule(HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        StudentDto studentDto= (StudentDto) securityContext.getAuthentication().getPrincipal();

        return new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,studentDto.getShowPracticeModule());
    }
}
