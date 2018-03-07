package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.ClassMapper;
import cn.examsystem.rest.mapper.StudentMapper;
import cn.examsystem.rest.pojo.po.Class;
import cn.examsystem.rest.pojo.po.Student;
import cn.examsystem.rest.pojo.po.StudentExample;
import cn.examsystem.rest.pojo.vo.StudentVo;
import cn.examsystem.rest.service.StudentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;


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

        //id对应学生必须存在
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

        //如果密码不为空，则修改密码
        String studentPassword = student.getPassword();
        if(!StringUtils.isBlank(studentPassword)){
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            studentPassword=encoder.encode(studentPassword.trim());
            studentDb.setPassword(studentPassword);
        }


        //更新学生
        studentDb.setName(studentName);
        studentDb.setClassId(classId);
        studentDb.setUpdatedTime(new Date());
        studentMapper.updateByPrimaryKey(studentDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);

    }

    @Override
    public List<Student> listStudent(StudentVo studentVo) throws Exception {
        StudentExample studentExample=new StudentExample();

        if(studentVo!=null){

            StudentExample.Criteria studentCriteria = studentExample.createCriteria();

            if(!StringUtils.isBlank(studentVo.getStudentId())){
                studentCriteria.andStudentIdEqualTo(studentVo.getStudentId().trim());
            }
            if(!StringUtils.isBlank(studentVo.getName())){
                studentCriteria.andNameEqualTo(studentVo.getName().trim());
            }
            if(!StringUtils.isBlank(studentVo.getClassId())){
                studentCriteria.andClassIdEqualTo(studentVo.getClassId());
            }
            if(studentVo.getStatus()!=null){
                studentCriteria.andStatusEqualTo(studentVo.getStatus());
            }
        }
        return studentMapper.selectByExample(studentExample);
    }

    @Override
    public ResultInfo saveStudent(Student student) throws Exception {
        //学号不能为空
        String studentStudentId = student.getStudentId();
        if(StringUtils.isBlank(studentStudentId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_NULL,null);

        //学号预处理
        studentStudentId=studentStudentId.trim();

        //名字不能为空
        String studentDtoName = student.getName();
        if(StringUtils.isBlank(studentDtoName))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_NAME_NOT_NULL,null);

        //名字预处理
        studentDtoName=studentDtoName.trim();

        //班级id不能为空
        String studentDtoClassId = student.getClassId();
        if(StringUtils.isBlank(studentDtoClassId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_ID_NOT_NULL,null);

        //班级信息必须存在
        Class aClass = classMapper.selectByPrimaryKey(studentDtoClassId);
        if(aClass==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CLASS_NOT_EXIST,null);


        //学号不能重复
        StudentExample studentExample=new StudentExample();
        StudentExample.Criteria studentCriteria = studentExample.createCriteria();
        studentCriteria.andStudentIdEqualTo(studentStudentId);
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        if(!CollectionUtils.isEmpty(studentList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_STUDENT_STUDENTID_NOT_REPEAT,null);
        }

        //补全id
        String studentId = UUIDBuild.getUUID();
        student.setId(studentId);
        student.setStudentId(studentStudentId);
        student.setName(studentDtoName);
        //密码加密(学生学号后4位)
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String studentDtoPassword=encoder.encode(studentStudentId.substring(studentStudentId.length()-4));
        student.setPassword(studentDtoPassword);
        //设置状态
        student.setStatus(1);
        //设置班级信息
        student.setClassId(studentDtoClassId);

        //补全创建时间，更新时间
        student.setCreatedTime(new Date());
        student.setUpdatedTime(new Date());

        //添加用户
        studentMapper.insert(student);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }
}
