package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ValidateCode;
import cn.examsystem.common.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片验证码处理器
 *
 * @author brotherChun
 *
 */
@Controller
public class ValidateCodeController {

    @Value("${SESSION_KEY_IMAGE_CODE}")
    private String SESSION_KEY_IMAGE_CODE;

    @RequestMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException{

        //生成图形验证码
        ValidateCode validateCode = ValidateCodeUtils.createCode();

        //将验证码的值保存到session中
        HttpSessionSessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY_IMAGE_CODE,validateCode.getCode());

        //发送图形验证码，将其写到响应中
        ImageIO.write(validateCode.getImage(), "JPEG", response.getOutputStream());

    }
}
