package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.ClassMapper;
import cn.examsystem.rest.mapper.ClassMapperCustom;
import cn.examsystem.rest.mapper.GradeMapper;
import cn.examsystem.rest.mapper.MajorMapper;
import cn.examsystem.rest.pojo.dto.ClassDto;
import cn.examsystem.rest.pojo.po.Class;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.ClassVo;
import cn.examsystem.rest.service.ClassService;
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
 * 班级业务层实现
 */
@Service
public class ClassImpl implements ClassService {

    @Value("${MESSAGE_MAJOR_NAME_NOT_NULL}")
    private String MESSAGE_MAJOR_NAME_NOT_NULL;
    @Value("${MESSAGE_MAJOR_NAME_NOT_REPEAT}")
    private String MESSAGE_MAJOR_NAME_NOT_REPEAT;
    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_MAJOR_ID_NOT_NULL}")
    private String MESSAGE_MAJOR_ID_NOT_NULL;
    @Value("${MESSAGE_MAJOR_NOT_EXIST}")
    private String MESSAGE_MAJOR_NOT_EXIST;


    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Value("${MESSAGE_CLASS_NAME_NOT_NULL}")
    private String MESSAGE_CLASS_NAME_NOT_NULL;
    @Value("${MESSAGE_GRADE_ID_NOT_NULL}")
    private String MESSAGE_GRADE_ID_NOT_NULL;
    @Value("${MESSAGE_GRADE_NOT_EXIST}")
    private String MESSAGE_GRADE_NOT_EXIST;

    @Autowired
    private ClassMapperCustom classMapperCustom;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public List<ClassDto> listClass(ClassVo classVo) throws Exception {
        return classMapperCustom.listClass(classVo);
    }

    @Override
    public ResultInfo btchSaveClass(ClassDto classDto) throws Exception {

        //名字不能为空
        String classDtoNames = classDto.getNames();
        if(StringUtils.isBlank(classDtoNames))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_NAME_NOT_NULL,null);

        //名字预处理
        classDtoNames=classDtoNames.trim();

        //专业id不能为空
        String majorId=classDto.getMajorId();
        if(StringUtils.isBlank(majorId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_ID_NOT_NULL,null);

        //年级id不能为空
        String gradeId=classDto.getGradeId();
        if(StringUtils.isBlank(gradeId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_GRADE_ID_NOT_NULL,null);

        //添加的班级所属的专业必须存在
        Major major = majorMapper.selectByPrimaryKey(majorId);
        if(major==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_MAJOR_NOT_EXIST,null);

        //添加的专业所属的年级必须存在
        Grade grade = gradeMapper.selectByPrimaryKey(gradeId);
        if(grade==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_GRADE_NOT_EXIST,null);

        //添加的班级不允许重复
        String[] classDtoNameScope=classDtoNames.split("-");

        int start=Integer.valueOf(classDtoNameScope[0]);
        int end=Integer.valueOf(classDtoNameScope[1]);

        //处理成功的数量
        Integer countSuccess = 0;
        //处理失败的数量
        Integer countError = 0;
        //错误信息
        List<ResultInfo> errorDetails=new ArrayList<>();


        for(int i=start;i<=end;i++){

            //名字不能重复
            ClassExample classExample=new ClassExample();
            ClassExample.Criteria classCriteria = classExample.createCriteria();
            classCriteria.andMajorIdEqualTo(majorId);
            classCriteria.andGradeIdEqualTo(gradeId);
            classCriteria.andNameEqualTo(i);
            List<Class> classList = classMapper.selectByExample(classExample);

            if(!CollectionUtils.isEmpty(classList)){//名字重复
                countError++;
                ResultInfo resultInfo = new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY, i + "", null);
                errorDetails.add(resultInfo);
            }else{//进行添加
                Class aClass=new Class();
                String classId=UUIDBuild.getUUID();
                //补全id
                aClass.setId(classId);
                aClass.setName(i);
                aClass.setMajorId(majorId);
                aClass.setGradeId(gradeId);
                //补全创建时间，更新时间
                aClass.setCreatedTime(new Date());
                aClass.setUpdatedTime(new Date());

                //添加班级
                classMapper.insert(aClass);
                countSuccess++;
            }
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,countSuccess,errorDetails);
    }

}
