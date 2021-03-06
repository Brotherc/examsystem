package cn.examsystem.manager.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.Course;
import cn.examsystem.rest.pojo.po.KnowledgePoint;
import cn.examsystem.rest.service.CourseService;
import cn.examsystem.rest.service.KnowledgePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/28.
 * 知识点控制器类
 */
@RestController
public class KnowledgePointController {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${COURSE_URL}")
    private String COURSE_URL;
    @Value("${KNOWLEDGEPOINT_URL}")
    private String KNOWLEDGEPOINT_URL;

    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;


    @Value("${MESSAGE_GET_FAIL}")
    private String MESSAGE_GET_FAIL;
    @Value("${MESSAGE_DELETE_FAIL}")
    private String MESSAGE_DELETE_FAIL;
    @Value("${MESSAGE_SAVE_FAIL}")
    private String MESSAGE_SAVE_FAIL;
    @Value("${MESSAGE_UPDATE_FAIL}")
    private String MESSAGE_UPDATE_FAIL;

    @Autowired
    private KnowledgePointService knowledgePointService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/v1/knowledgePoint/course/{courseId}")
    public ResultInfo getKnowledgePoint(@PathVariable String courseId) throws Exception{

        ResultInfo resultInfo;
        try{
            //调用rest服务，查询对应id课程
            Course course = courseService.getCourse(courseId);

            if(course==null)
                return new ResultInfo(ResultInfo.STATUS_RESULT_GONE,MESSAGE_COURSE_ID_NOT_NULL,null);

            //获得该课程对应的知识点树父节点id
            String knowledgePointId = course.getKnowledgePointId();

            //调用rest服务
            resultInfo = knowledgePointService.getKnowledgePoint(knowledgePointId);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_GET_FAIL,null);
        }
        return resultInfo;
    }

    @DeleteMapping("/v1/knowledgePoint/{id}")
    public ResultInfo deleteKnowledgePoint(@PathVariable String id, KnowledgePoint knowledgePoint) throws Exception{

        System.out.println(knowledgePoint.getParentId());

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=knowledgePointService.deleteKnowledgePoint(id,knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_DELETE_FAIL,null);
        }
        return resultInfo;
    }

    @PostMapping("/v1/knowledgePoint")
    public ResultInfo saveKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=knowledgePointService.saveKnowledgePoint(knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_SAVE_FAIL,null);
        }
        return resultInfo;
    }

    @PutMapping("/v1/knowledgePoint/{id}")
    public ResultInfo updateKnowledgePoint(@PathVariable String id, KnowledgePoint knowledgePoint) throws Exception{

        ResultInfo resultInfo;
        try {
            //调用rest服务
            resultInfo=knowledgePointService.updateKnowlesgePoint(id,knowledgePoint);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultInfo(ResultInfo.STATUS_RESULT_INTERANL_SERVER_ERROR,MESSAGE_UPDATE_FAIL,null);
        }
        return resultInfo;
    }
}
