package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.service.CheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/30.
 * 审核服务控制类
 */
@RestController
public class CheckerController {

    @Autowired
    private CheckerService checkerService;

    @PutMapping("/v1/checker/question/{id}")
    public ResultInfo checkQuestion(@PathVariable String id,@RequestBody String questionType) throws Exception{
        System.out.println(questionType);
        return checkerService.checkQuestion(id,questionType);
    }
}
