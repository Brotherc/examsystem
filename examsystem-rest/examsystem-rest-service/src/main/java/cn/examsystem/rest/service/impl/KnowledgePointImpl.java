package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.pojo.TreeNode;
import cn.examsystem.common.pojo.TreeNodeState;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.KnowledgePointMapper;
import cn.examsystem.rest.mapper.QuestionKnowledgepointRelationMapper;
import cn.examsystem.rest.pojo.po.KnowledgePoint;
import cn.examsystem.rest.pojo.po.KnowledgePointExample;
import cn.examsystem.rest.pojo.po.QuestionKnowledgepointRelationExample;
import cn.examsystem.rest.service.KnowledgePointService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 知识点业务层实现
 */
@Service
public class KnowledgePointImpl implements KnowledgePointService {

    @Value("${MESSAGE_GET_SUCCESS}")
    private String MESSAGE_GET_SUCCESS;
    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;
    @Value("${MESSAGE_DELETE_SUCCESS}")
    private String MESSAGE_DELETE_SUCCESS;


    @Value("${MESSAGE_PARENT_ID_NOT_NULL}")
    private String MESSAGE_PARENT_ID_NOT_NULL;

    @Value("${MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL}")
    private String MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL;
    @Value("${MESSAGE_KNOWLEDGEPOINT_NAME_NOT_NULL}")
    private String MESSAGE_KNOWLEDGEPOINT_NAME_NOT_NULL;
    @Value("${MESSAGE_KNOWLEDGEPOINT_NOT_EXIST}")
    private String MESSAGE_KNOWLEDGEPOINT_NOT_EXIST;
    @Value("${MESSAGE_PARENT_NOT_EXIST}")
    private String MESSAGE_PARENT_NOT_EXIST;

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    @Autowired
    private QuestionKnowledgepointRelationMapper questionKnowledgepointRelationMapper;

    @Override
    public ResultInfo getKnowledgePoint(String parentId) throws Exception {

        if(StringUtils.isBlank(parentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARENT_ID_NOT_NULL,null);

        KnowledgePoint knowledgePoint=knowledgePointMapper.selectByPrimaryKey(parentId);

        KnowledgePointExample knowledgePointOneExample=new KnowledgePointExample();
        KnowledgePointExample.Criteria knowledgePointOneCriteria = knowledgePointOneExample.createCriteria();
        knowledgePointOneCriteria.andParentIdEqualTo(parentId);
        knowledgePointOneExample.setOrderByClause("SORT_ORDER ASC");
        List<KnowledgePoint> knowledgePointOneList = knowledgePointMapper.selectByExample(knowledgePointOneExample);

        List<TreeNode>oneTreeNodeList=new ArrayList<>();

        int oneTreeNodeIndex=0;

        for(KnowledgePoint knowledgePointOne:knowledgePointOneList){

            KnowledgePointExample knowledgePointTwoExample=new KnowledgePointExample();
            KnowledgePointExample.Criteria knowledgePointTwoCriteria = knowledgePointTwoExample.createCriteria();
            knowledgePointTwoCriteria.andParentIdEqualTo(knowledgePointOne.getId());
            List<KnowledgePoint> knowledgePointTwoList = knowledgePointMapper.selectByExample(knowledgePointTwoExample);

            List<TreeNode>twoTreeNodeList=new ArrayList<>();

            for(KnowledgePoint knowledgePointTwo:knowledgePointTwoList){
                TreeNode twoTreeNode=new TreeNode();
                twoTreeNode.setId(knowledgePointTwo.getId());
                twoTreeNode.setText(knowledgePointTwo.getName());
                twoTreeNode.setChildren(null);
                twoTreeNode.setState(new TreeNodeState(false,false));
                twoTreeNodeList.add(twoTreeNode);
            }

            TreeNode oneTreeNode=new TreeNode();
            oneTreeNode.setId(knowledgePointOne.getId());
            oneTreeNode.setText(knowledgePointOne.getName());
            oneTreeNode.setChildren(twoTreeNodeList);

            if(oneTreeNodeIndex==0)
                oneTreeNode.setState(new TreeNodeState(false,true));
            else
                oneTreeNode.setState(new TreeNodeState(false,false));

            oneTreeNodeList.add(oneTreeNode);

            oneTreeNodeIndex++;
        }

        TreeNode treeNode=new TreeNode(knowledgePoint.getId(),knowledgePoint.getName(),new TreeNodeState(true,true),oneTreeNodeList,"#");

        List<TreeNode> resultTreeList=new ArrayList<>();
        resultTreeList.add(treeNode);

        return new ResultInfo(ResultInfo.STATUS_RESULT_OK,MESSAGE_GET_SUCCESS,resultTreeList);
    }

    @Override
    public ResultInfo saveKnowledgePoint(KnowledgePoint knowledgePoint) throws Exception {

        //父id不允许为空
        String knowledgePointParentId = knowledgePoint.getParentId();
        if(StringUtils.isBlank(knowledgePointParentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARENT_ID_NOT_NULL,null);

        //名字不允许为空
        String knowledgePointName = knowledgePoint.getName();
        if(StringUtils.isBlank(knowledgePointName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NAME_NOT_NULL,null);
        knowledgePointName=knowledgePointName.trim();

        KnowledgePoint knowledgePointDb=new KnowledgePoint();
        String knowledgePointDbId = UUIDBuild.getUUID();
        knowledgePointDb.setId(knowledgePointDbId);
        knowledgePointDb.setName(knowledgePointName);

        //获取所添加的节点兄弟的最高顺序
        KnowledgePointExample knowledgePointExample=new KnowledgePointExample();
        KnowledgePointExample.Criteria knowledgePointCriteria = knowledgePointExample.createCriteria();
        knowledgePointCriteria.andParentIdEqualTo(knowledgePointParentId);
        knowledgePointExample.setOrderByClause("SORT_ORDER ASC");
        List<KnowledgePoint> knowledgePointList = knowledgePointMapper.selectByExample(knowledgePointExample);

        //如果没有兄弟
        if(CollectionUtils.isEmpty(knowledgePointList)){
            knowledgePointDb.setSortOrder(1);
        }else{
            int sortOrder=knowledgePointList.get(knowledgePointList.size()-1).getSortOrder();
            knowledgePointDb.setSortOrder(sortOrder+1);
        }

        knowledgePointDb.setParentId(knowledgePointParentId);
        knowledgePointDb.setIsParent(false);
        knowledgePointDb.setCreatedTime(new Date());
        knowledgePointDb.setUpdatedTime(new Date());

        //保存知识点
        knowledgePointMapper.insert(knowledgePointDb);

        //判断如果父节点的isParent不是true，修改为true
        KnowledgePoint knowledgePointParent = knowledgePointMapper.selectByPrimaryKey(knowledgePointParentId);

        if(!knowledgePointParent.getIsParent()){
            knowledgePointParent.setIsParent(true);
            knowledgePointParent.setUpdatedTime(new Date());
            //更新父节点
            knowledgePointMapper.updateByPrimaryKey(knowledgePointParent);
        }
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,knowledgePointDb);
    }

    @Override
    public ResultInfo updateKnowlesgePoint(String id,KnowledgePoint knowledgePoint) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL,null);

        //名字不能为空
        String knowledgePointName = knowledgePoint.getName();
        if(StringUtils.isBlank(knowledgePointName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NAME_NOT_NULL,null);

        //名字预处理
        knowledgePointName=knowledgePointName.trim();

        //id对应知识点必须存在
        KnowledgePoint knowledgePointDb = knowledgePointMapper.selectByPrimaryKey(id);

        if(knowledgePointDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NOT_EXIST,null);

        knowledgePointDb.setName(knowledgePointName);
        knowledgePointDb.setUpdatedTime(new Date());

        //更新知识点
        knowledgePointMapper.updateByPrimaryKey(knowledgePointDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public ResultInfo deleteKnowledgePoint(String id, KnowledgePoint knowledgePoint) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_ID_NOT_NULL,null);

        //父id不允许为空
        String knowledgePointParentId = knowledgePoint.getParentId();
        if(StringUtils.isBlank(knowledgePointParentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARENT_ID_NOT_NULL,null);

        //id对应知识点必须存在
        KnowledgePoint knowledgePointDb = knowledgePointMapper.selectByPrimaryKey(id);
        if(knowledgePointDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_KNOWLEDGEPOINT_NOT_EXIST,null);

        //parentId对应知识点必须存在
        KnowledgePoint knowledgePointParentDb = knowledgePointMapper.selectByPrimaryKey(knowledgePointParentId);
        if(knowledgePointParentDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARENT_NOT_EXIST,null);

        //删除知识点
        knowledgePointMapper.deleteByPrimaryKey(id);

        //如果删除的节点的父亲没有孩子，则修改isParent为false
        KnowledgePointExample knowledgePointExample=new KnowledgePointExample();
        KnowledgePointExample.Criteria knowledgePointCriteria = knowledgePointExample.createCriteria();
        knowledgePointCriteria.andParentIdEqualTo(knowledgePointParentId);
        List<KnowledgePoint> knowledgePointList = knowledgePointMapper.selectByExample(knowledgePointExample);
        if(CollectionUtils.isEmpty(knowledgePointList)){
            knowledgePointParentDb.setIsParent(false);
            knowledgePointParentDb.setUpdatedTime(new Date());
            knowledgePointMapper.updateByPrimaryKey(knowledgePointParentDb);
        }

        //将该知识点对应的题目删除
        QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
        QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationCriteria = questionKnowledgepointRelationExample.createCriteria();
        questionKnowledgepointRelationCriteria.andKnowledgePointIdEqualTo(id);
        questionKnowledgepointRelationMapper.deleteByExample(questionKnowledgepointRelationExample);

        return new ResultInfo(ResultInfo.STATUS_RESULT_NO_CONTENT,MESSAGE_DELETE_SUCCESS,null);
    }
}
