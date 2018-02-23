package cn.examsystem.security.filter;

import cn.examsystem.security.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/27.
 * 自定义图形验证码锅过滤器
 */
public class ValidateCodeFilter extends OncePerRequestFilter{

    @Value("${SESSION_KEY_IMAGE_CODE}")
    private String SESSION_KEY_IMAGE_CODE;
    @Value("${REQUEST_NAME_IMAGE_CODE}")
    private String REQUEST_NAME_IMAGE_CODE;

    @Value("${MESSAGE_IMG_CODE_GET_FAIL}")
    private String MESSAGE_IMG_CODE_GET_FAIL;
    @Value("${MESSAGE_IMG_CODE_IS_NULL}")
    private String MESSAGE_IMG_CODE_IS_NULL;
    @Value("${MESSAGE_IMG_CODE_NOT_EXIST}")
    private String MESSAGE_IMG_CODE_NOT_EXIST;
    @Value("${MESSAGE_IMG_CODE_NOT_MATCH}")
    private String MESSAGE_IMG_CODE_NOT_MATCH;

    /**
     * 认证失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("进入验证码拦截器");

        //如果是登录时的post请求
        if(StringUtils.equals("/login",request.getRequestURI())&&StringUtils.equalsIgnoreCase(request.getMethod(),"post")){
            try {
                //则进行验证码校验
                validate(new ServletWebRequest(request));
            }catch (ValidateCodeException e){
                //将自定义验证码校验失败异常抛给失败处理器进行处理
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    private void validate(ServletWebRequest request){
        HttpSessionSessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

        //获取session中图形验证码的值
        String codeInSession = (String) sessionStrategy.getAttribute(request, SESSION_KEY_IMAGE_CODE);

        String codeInRequest;
        try {
            System.out.println(REQUEST_NAME_IMAGE_CODE);
            //获取用户所填验证码的值
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),REQUEST_NAME_IMAGE_CODE);
            System.out.println("-------------"+codeInRequest);
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(MESSAGE_IMG_CODE_GET_FAIL);
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(MESSAGE_IMG_CODE_IS_NULL);
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(MESSAGE_IMG_CODE_NOT_EXIST);
        }

/*        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY_IMAGE_CODE);
            throw new ValidateCodeException("验证码已过期");
        }*/

        if (!StringUtils.equals(codeInSession, codeInRequest)) {
            throw new ValidateCodeException(MESSAGE_IMG_CODE_NOT_MATCH);
        }

        //校验过后删除session中保存到的验证码的值
        sessionStrategy.removeAttribute(request, SESSION_KEY_IMAGE_CODE);
    }
}
