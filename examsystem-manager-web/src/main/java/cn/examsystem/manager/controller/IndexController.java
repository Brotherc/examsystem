package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/1/29.
 * 首页控制器
 */
@RestController
public class IndexController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${USER_URL}")
    private String USER_URL;

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;


    @GetMapping("/v1/user/details")
    public ResultInfo getUserDetails(HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //将用户的密码设置为空
        SysuserDto sysuserVo=new SysuserDto();
        BeanUtils.copyProperties(sysuserDto,sysuserVo);
        sysuserVo.setPassword(null);

        System.out.println(sysuserDto.getAuthorities());

        return new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,sysuserVo);
    }

    @PutMapping("/v1/user/details")
    public ResultInfo updateUserDetails(cn.examsystem.rest.pojo.dto.SysuserDto sysuserDto,HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto user= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo= RestTemplateUtils.exchange(REST_BASE_URL+USER_URL+"/details/{id}", HttpMethod.PUT,sysuserDto,ResultInfo.class,new Object[]{user.getId()});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
