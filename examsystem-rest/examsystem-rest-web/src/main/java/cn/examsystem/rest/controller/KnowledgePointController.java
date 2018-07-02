package cn.examsystem.rest.controller;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.pojo.po.KnowledgePoint;
import cn.examsystem.rest.service.KnowledgePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/30.
 * 知识点服务控制类
 */
@RestController
public class KnowledgePointController {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;

    @Autowired
    private KnowledgePointService knowledgePointService;

    @GetMapping("/v1/knowledgePoint/{parentId}")
    public ResultInfo getKnowledgePoint(@PathVariable String parentId) throws Exception{
        return knowledgePointService.getKnowledgePoint(parentId);
    }

    @DeleteMapping(value = "/v1/knowledgePoint/{id}")
    public ResultInfo deleteKnowledgePoint(@PathVariable String id,@RequestBody KnowledgePoint knowledgePoint) throws Exception{
        //System.out.println(knowledgePoint.getParentId());
        return knowledgePointService.deleteKnowledgePoint(id,knowledgePoint);
    }

    @PostMapping("/v1/knowledgePoint")
    public ResultInfo saveKnowledgePoint(@RequestBody KnowledgePoint knowledgePoint) throws Exception{
        return knowledgePointService.saveKnowledgePoint(knowledgePoint);
    }

    @PutMapping("/v1/knowledgePoint/{id}")
    public ResultInfo updateKnowledgePoint(@PathVariable String id,@RequestBody KnowledgePoint knowledgePoint) throws Exception{
        return knowledgePointService.updateKnowlesgePoint(id,knowledgePoint);
    }
}
