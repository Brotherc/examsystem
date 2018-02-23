package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.manager.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/28.
 * 审核控制器类
 */
@RestController
public class CheckerController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${CHECKER_URL}")
    private String CHECKER_URL;
    @Value("${CHECKER_QUESTION_URL}")
    private String CHECKER_QUESTION_URL;

    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @PutMapping("/v1/checker/question/{id}")
    public ResultInfo checkQuestion(@PathVariable String id, String questionType) throws Exception{

        ResultInfo resultInfo;
        System.out.println(questionType);
        try {
            //调用rest服务
            resultInfo=RestTemplateUtils.exchange(REST_BASE_URL+CHECKER_URL+CHECKER_QUESTION_URL+"/{id}",HttpMethod.PUT,questionType,ResultInfo.class,new Object[]{id});
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
