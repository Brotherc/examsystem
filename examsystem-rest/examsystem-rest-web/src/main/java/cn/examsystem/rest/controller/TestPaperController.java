package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.dto.TestPaperDto;
import cn.examsystem.rest.pojo.po.TestPaper;
import cn.examsystem.rest.pojo.vo.TestPaperVo;
import cn.examsystem.rest.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 试卷服务控制类
 */
@RestController
public class TestPaperController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private TestPaperService testPaperService;

    @GetMapping("/v1/testPaper/{id}")
    public ResultInfo getTestPaper(@PathVariable String id) throws Exception{
        TestPaperDto testPaperDto = testPaperService.getTestPaper(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        return resultInfo;
    }

    @GetMapping("/v1/testPaper")
    public ResultInfo listTestPaper(TestPaperVo testPaperVo) throws Exception{
        List<TestPaper> testPaperList = testPaperService.listTestPaper(testPaperVo);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperList);
        return resultInfo;
    }

    @PostMapping("/v1/testPaper")
    public ResultInfo saveTestPaper(@RequestBody TestPaperDto testPaperDto) throws Exception{
        System.out.println(testPaperDto.getSingleChoiceQuestionIds());
        System.out.println(testPaperDto.getFillInBlankQuestionIds());
        System.out.println(testPaperDto.getSingleChoiceQuestionScore());
        System.out.println(testPaperDto.getFillInBlankQuestionScore());
        return testPaperService.saveTestPaper(testPaperDto);
    }
    @PutMapping("/v1/testPaper/{id}")
    public ResultInfo updateTestPaper(@PathVariable String id,@RequestBody TestPaperDto testPaperDto) throws Exception{
        return testPaperService.updateTestPaper(id,testPaperDto);
    }

    @GetMapping("/v1/test/testPaper/{testPaperId}")
    public ResultInfo getTestPaperAndQuestionsByIdForLoginStudent(@PathVariable String testPaperId,String examStudentId) throws Exception{
        System.out.println("------------------------------------------");
        TestPaperDto testPaperDto = testPaperService.getTestPaperAndQuestionsByIdForLoginStudent(testPaperId,examStudentId);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        return resultInfo;
    }

    @GetMapping("/v1/testPaper/{id}/question")
    public ResultInfo listTestPaperQuestionByTestPaperId(@PathVariable String id) throws Exception{
        TestPaperDto testPaperDto = testPaperService.listTestPaperQuestionByTestPaperId(id);
        ResultInfo resultInfo=new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,testPaperDto);
        return resultInfo;
    }

    @DeleteMapping(value = "/v1/testPaper/{testPaperId}/question")
    public ResultInfo removeQuestionFromTestPaper(@PathVariable String testPaperId,@RequestBody String[] ids) throws Exception{
        return testPaperService.removeQuestionFromTestPaper(testPaperId,ids);
    }

    @PutMapping("/v1/testPaper/{id}/question/{questionId}")
    public ResultInfo updateTestPaperQuestionOrder(@PathVariable String id,@PathVariable String questionId,@RequestBody Integer order) throws Exception{
        return testPaperService.updateTestPaperQuestionOrder(id,questionId,order);
    }
}
