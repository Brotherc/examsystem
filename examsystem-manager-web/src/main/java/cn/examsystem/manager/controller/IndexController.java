package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.springframework.beans.BeanUtils;
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


    @GetMapping("/v1/user/details")
    public ResultInfo getUserDetails(HttpSession session) throws Exception{

        //从session中获取springsecurity认证的用户信息
        SecurityContext securityContext= (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        SysuserDto sysuserDto= (SysuserDto) securityContext.getAuthentication().getPrincipal();

        //将用户的密码设置为空
        SysuserDto sysuserVo=new SysuserDto();
        BeanUtils.copyProperties(sysuserDto,sysuserVo);
        sysuserVo.setPassword(null);

        return new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,sysuserVo);
    }
}
