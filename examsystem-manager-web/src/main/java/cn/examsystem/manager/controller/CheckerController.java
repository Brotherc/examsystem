package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.service.CheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private CheckerService checkerService;

    @PutMapping("/v1/checker/question/{questionType}")
    public ResultInfo checkQuestion(@PathVariable String questionType,@RequestParam(value = "questionIds[]") String[] questionIds) throws Exception{

        ResultInfo resultInfo;
        System.out.println(questionType);
        try {
            //调用rest服务
            resultInfo=checkerService.checkQuestion(questionIds,questionType);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
