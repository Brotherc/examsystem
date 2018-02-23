package cn.examsystem.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Administrator on 2018/1/27.
 * 自定义验证码认证失败异常
 */
public class ValidateCodeException extends AuthenticationException{

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
