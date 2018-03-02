package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.rest.mapper.ClassMapper;
import cn.examsystem.rest.mapper.StudentMapper;
import cn.examsystem.rest.pojo.po.Class;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.service.StudentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/17.
 * 学生业务层实现
 */
@Service
public class StudentImpl implements StudentService{

    @Value("${MESSAGE_STUDENT_ID_NOT_NULL}")
    private String MESSAGE_STUDENT_ID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_STUDENTID_NOT_NULL}")
    private String MESSAGE_STUDENT_STUDENTID_NOT_NULL;
    @Value("${MESSAGE_STUDENT_NAME_NOT_NULL}")
    private String MESSAGE_STUDENT_NAME_NOT_NULL;
    @Value("${MESSAGE_CLASS_ID_NOT_NULL}")
    private String MESSAGE_CLASS_ID_NOT_NULL;
    @Value("${MESSAGE_CLASS_NOT_EXIST}")
    private String MESSAGE_CLASS_NOT_EXIST;
    @Value("${MESSAGE_STUDENT_NOT_EXIST}")
    private String MESSAGE_STUDENT_NOT_EXIST;
    @Value("${MESSAGE_STUDENT_STUDENTID_NOT_REPEAT}")
    private String MESSAGE_STUDENT_STUDENTID_NOT_REPEAT;

    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public ResultInfo updateStudent(String id, Student student) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_ID_NOT_NULL,null);

        //学号不允许为空
        String studentStudentId=student.getStudentId();
        if(StringUtils.isBlank(studentStudentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_NULL,null);

        //学号预处理
        studentStudentId=studentStudentId.trim();

        //名字不允许为空
        String studentName=student.getName();
        if(StringUtils.isBlank(studentName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NAME_NOT_NULL,null);

        //名字预处理
        studentName=studentName.trim();

        //学生班级id不能为空
        String classId = student.getClassId();
        if(StringUtils.isBlank(classId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_ID_NOT_NULL,null);

        //学生班级必须存在
        Class aClass = classMapper.selectByPrimaryKey(classId);
        if(aClass==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_NOT_EXIST,null);

        //id对应专业必须存在
        Student studentDb = studentMapper.selectByPrimaryKey(id);

        if(studentDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NOT_EXIST,null);

        //若对学号进行了修改
        if(!StringUtils.equals(studentStudentId,studentDb.getStudentId())){
            //学号不能重复
            StudentExample studentExample=new StudentExample();
            StudentExample.Criteria studentCriteria = studentExample.createCriteria();
            studentCriteria.andStudentIdEqualTo(studentStudentId);
            List<Student> studentList = studentMapper.selectByExample(studentExample);
            if(!CollectionUtils.isEmpty(studentList)){
                return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_REPEAT,null);
            }
            //设置学号
            studentDb.setStudentId(studentStudentId);
        }

        //更新学生
        studentDb.setName(studentName);
        studentDb.setClassId(classId);
        studentDb.setUpdatedTime(new Date());
        studentMapper.updateByPrimaryKey(studentDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);

    }
}
