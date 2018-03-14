package cn.examsystem.security.handle;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.JsonUtils;
import cn.examsystem.security.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/26.
 * 自定义认证失败处理器
 */
public class SysuserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Value("${MESSAGE_USSERNAME_PWD_ERROR}")
    private String MESSAGE_USSERNAME_PWD_ERROR;
    @Value("${MESSAGE_USER_IS_UNABLED}")
    private String MESSAGE_USER_IS_UNABLED;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        //返回成功响应
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");

        //构造认证失败结果信息
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_UNAUTHORIZED,null,null);

        if(exception instanceof ValidateCodeException){
            //设置验证码认证失败提示信息
            resultInfo.setMessage(exception.getMessage());
        }else if(exception instanceof DisabledException){

            //设置认证失败提示信息
            resultInfo.setMessage(MESSAGE_USER_IS_UNABLED);
        }else{
            //设置认证失败提示信息
            resultInfo.setMessage(MESSAGE_USSERNAME_PWD_ERROR);
        }
        response.getWriter().write(JsonUtils.objectToJson(resultInfo));

    }
}
