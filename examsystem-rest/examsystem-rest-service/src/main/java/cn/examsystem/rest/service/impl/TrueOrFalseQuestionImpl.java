package cn.examsystem.rest.service.impl;

import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.utils.FileUtil;
import cn.examsystem.common.utils.POIUtils;
import cn.examsystem.common.utils.UUIDBuild;
import cn.examsystem.rest.mapper.*;
import cn.examsystem.rest.pojo.dto.TrueOrFalseQuestionDto;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.rest.pojo.vo.TrueOrFalseQuestionVo;
import cn.examsystem.rest.service.TrueOrFalseQuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 判断题业务层实现
 */
@Service
public class TrueOrFalseQuestionImpl implements TrueOrFalseQuestionService {

    @Autowired
    private TrueOrFalseQuestionMapperCustom trueOrFalseQuestionMapperCustom;
    @Autowired
    private TrueOrFalseQuestionMapper trueOrFalseQuestionMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private DictInfoMapper dictInfoMapper;
    @Autowired
    private CourseTeacherRelationMapper courseTeacherRelationMapper;
    @Autowired
    private QuestionKnowledgepointRelationMapper questionKnowledgepointRelationMapper;
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

    @Value("${MESSAGE_QUESTION_IS_CHECKED}")
    private String MESSAGE_QUESTION_IS_CHECKED;
    @Value("${MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST}")
    private String MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST;
    @Value("${MESSAGE_QUESTION_ID_NOT_NULL}")
    private String MESSAGE_QUESTION_ID_NOT_NULL;
    @Value("${MESSAGE_QUESTION_NOT_EXIST}")
    private String MESSAGE_QUESTION_NOT_EXIST;
    @Value("${MESSAGE_QUESTION_CONTENT_NOT_NULL}")
    private String MESSAGE_QUESTION_CONTENT_NOT_NULL;
    @Value("${MESSAGE_QUESTION_ANSWER_NOT_NULL}")
    private String MESSAGE_QUESTION_ANSWER_NOT_NULL;
    @Value("${MESSAGE_DIFFICULTY_ID_NOT_NULL}")
    private String MESSAGE_DIFFICULTY_ID_NOT_NULL;
    @Value("${MESSAGE_DIFFICULTY_NOT_EXIST}")
    private String MESSAGE_DIFFICULTY_NOT_EXIST;
    @Value("${MESSAGE_CREATED_TEACHER_ID_NOT_NULL}")
    private String MESSAGE_CREATED_TEACHER_ID_NOT_NULL;
    @Value("${MESSAGE_CREATED_TEACHER_NOT_EXIST}")
    private String MESSAGE_CREATED_TEACHER_NOT_EXIST;
    @Value("${MESSAGE_COURSE_ID_NOT_NULL}")
    private String MESSAGE_COURSE_ID_NOT_NULL;
    @Value("${MESSAGE_COURSE_NOT_EXIST}")
    private String MESSAGE_COURSE_NOT_EXIST;
    @Value("${MESSAGE_PARAM_NOT_MATCH}")
    private String MESSAGE_PARAM_NOT_MATCH;

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;
    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Value("${DICTTYPE_DIFFICULTY_ID}")
    private String DICTTYPE_DIFFICULTY_ID;
    @Value("${DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE}")
    private String DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE;
    @Value("${FILE_PATH_PRE_TRUEORFALSEQUESTION_EXCEL}")
    private String FILE_PATH_PRE_TRUEORFALSEQUESTION_EXCEL;

    @Override
    public TrueOrFalseQuestionDto getTrueOrFalseQuestion(String id) throws Exception {

        TrueOrFalseQuestion trueOrFalseQuestion = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
        TrueOrFalseQuestionDto trueOrFalseQuestionDto=null;
        if(trueOrFalseQuestion!=null){
            trueOrFalseQuestionDto=new TrueOrFalseQuestionDto();

            //构造trueOrFalseQuestionDto
            BeanUtils.copyProperties(trueOrFalseQuestion,trueOrFalseQuestionDto);

            //查询课程信息
            Course course = courseMapper.selectByPrimaryKey(trueOrFalseQuestion.getCourseId());
            if(course!=null){
                trueOrFalseQuestionDto.setCourseName(course.getName());
            }

            //查询创建教师信息
            Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(trueOrFalseQuestion.getCreatedTeacherId());
            if(createdTeacher!=null){
                trueOrFalseQuestionDto.setCreatedTeacher(createdTeacher);
            }

            //查询难度信息
            DictInfoExample dictInfoExample=new DictInfoExample();
            DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
            dictInfoCriteria.andCodeEqualTo(String.valueOf(trueOrFalseQuestion.getDifficulty()));
            dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
            List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
            if(!CollectionUtils.isEmpty(dictInfoList)){
                trueOrFalseQuestionDto.setDifficultyName(dictInfoList.get(0).getName());
            }

            //查询知识点信息
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationCriteria.andQuestionIdEqualTo(id);
            List<QuestionKnowledgepointRelation> questionKnowledgepointRelationList = questionKnowledgepointRelationMapper.selectByExample(questionKnowledgepointRelationExample);
            if(!CollectionUtils.isEmpty(questionKnowledgepointRelationList)){
                List<String> knowledgePointIdList=new ArrayList<>();
                for(QuestionKnowledgepointRelation relation:questionKnowledgepointRelationList){
                    knowledgePointIdList.add(relation.getKnowledgePointId());
                }
                KnowledgePointExample knowledgePointExample=new KnowledgePointExample();
                KnowledgePointExample.Criteria knowledgePointCriteria = knowledgePointExample.createCriteria();
                knowledgePointCriteria.andIdIn(knowledgePointIdList);
                List<KnowledgePoint> knowledgePointList = knowledgePointMapper.selectByExample(knowledgePointExample);

                List<String> knowledgePointNameList=new ArrayList<>();
                for(KnowledgePoint knowledgePoint:knowledgePointList){
                    knowledgePointNameList.add(knowledgePoint.getName());
                }
                trueOrFalseQuestionDto.setKnowledgePoints(knowledgePointNameList);
            }
        }
        return trueOrFalseQuestionDto;
    }

    @Override
    public List<TrueOrFalseQuestionDto> listTrueOrFalseQuestion(TrueOrFalseQuestionVo trueOrFalseQuestionVo) throws Exception {
        return trueOrFalseQuestionMapperCustom.listTrueOrFalseQuestion(trueOrFalseQuestionVo);
    }

    @Override
    public ResultInfo saveTrueOrFalseQuestion(TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception {

        //内容不能为空
        String questionContent = trueOrFalseQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //答案不能为空
        Boolean questionAnswer = trueOrFalseQuestionDto.getAnswer();
        if(questionAnswer==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);


        //课程id不能为空
        String questionCourseId = trueOrFalseQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = trueOrFalseQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = trueOrFalseQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //添加的题目所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(questionCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //添加的题目的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(questionCreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //添加的题目所属的难度必须存在
        DictInfoExample dictInfoExample=new DictInfoExample();
        DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
        dictInfoCriteria.andCodeEqualTo(String.valueOf(questionDifficultyId));
        dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
        List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }

        //该题目对应课程必须在教师任课范围内
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationExampleCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationExampleCriteria.andCourseIdEqualTo(questionCourseId);
        courseTeacherRelationExampleCriteria.andTeacherIdEqualTo(questionCreatedTeacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(CollectionUtils.isEmpty(courseTeacherRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST,null);

        //补全uuid
        String questionId = UUIDBuild.getUUID();
        trueOrFalseQuestionDto.setId(questionId);
        trueOrFalseQuestionDto.setType(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));

        //添加审核状态
        trueOrFalseQuestionDto.setIsChecked(false);

        trueOrFalseQuestionDto.setCreatedTime(new Date());
        trueOrFalseQuestionDto.setUpdatedTime(new Date());

        //添加题目
        trueOrFalseQuestionMapper.insert(trueOrFalseQuestionDto);
        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,null);
    }

    @Override
    public ResultInfo updateTrueOrFalseQuestion(String id, TrueOrFalseQuestionDto trueOrFalseQuestionDto) throws Exception {

        //id不允许为空
        if(StringUtils.isBlank(id))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ID_NOT_NULL,null);

        //内容不能为空
        String questionContent = trueOrFalseQuestionDto.getContent();
        if(StringUtils.isBlank(questionContent))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_CONTENT_NOT_NULL,null);

        //内容预处理
        questionContent=questionContent.trim();

        //答案不能为空
        Boolean questionAnswer = trueOrFalseQuestionDto.getAnswer();
        if(questionAnswer==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_ANSWER_NOT_NULL,null);

        //课程id不能为空
        String questionCourseId = trueOrFalseQuestionDto.getCourseId();
        if(StringUtils.isBlank(questionCourseId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_ID_NOT_NULL,null);

        //难度id不能为空
        Integer questionDifficultyId = trueOrFalseQuestionDto.getDifficulty();
        if(questionDifficultyId==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_ID_NOT_NULL,null);

        //创建题目教师id不能为空
        String questionCreatedTeacherId = trueOrFalseQuestionDto.getCreatedTeacherId();
        if(StringUtils.isBlank(questionCreatedTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //id对应题目必须存在
        TrueOrFalseQuestion trueOrFalseQuestionDb = trueOrFalseQuestionMapper.selectByPrimaryKey(id);
        if(trueOrFalseQuestionDb==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_NOT_EXIST,null);

        //修改的题目所属的课程必须存在
        Course course = courseMapper.selectByPrimaryKey(questionCourseId);
        if(course==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_COURSE_NOT_EXIST,null);

        //修改的题目的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(questionCreatedTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //修改的题目所属的难度必须存在
        DictInfoExample dictInfoExample=new DictInfoExample();
        DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
        dictInfoCriteria.andCodeEqualTo(String.valueOf(questionDifficultyId));
        dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
        List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
        if(CollectionUtils.isEmpty(dictInfoList)){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_DIFFICULTY_NOT_EXIST,null);
        }


        //只有审核待通过的题目才允许修改
        Boolean isChecked = trueOrFalseQuestionDto.getIsChecked();
        if(isChecked)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_QUESTION_IS_CHECKED,null);

        //该题目对应课程必须在教师任课范围内
        CourseTeacherRelationExample courseTeacherRelationExample=new CourseTeacherRelationExample();
        CourseTeacherRelationExample.Criteria courseTeacherRelationExampleCriteria = courseTeacherRelationExample.createCriteria();
        courseTeacherRelationExampleCriteria.andCourseIdEqualTo(questionCourseId);
        courseTeacherRelationExampleCriteria.andTeacherIdEqualTo(questionCreatedTeacherId);
        List<CourseTeacherRelation> courseTeacherRelationList = courseTeacherRelationMapper.selectByExample(courseTeacherRelationExample);
        if(CollectionUtils.isEmpty(courseTeacherRelationList))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_TEACHER_QUESTION_RELATION_NOT_EXIST,null);

        //如果修改了课程id,则该题目对应知识点要删除
        if(!StringUtils.equals(questionCourseId,trueOrFalseQuestionDb.getCourseId())){
            //删除该题目对应知识点
            QuestionKnowledgepointRelationExample questionKnowledgepointRelationExample=new QuestionKnowledgepointRelationExample();
            QuestionKnowledgepointRelationExample.Criteria questionKnowledgepointRelationExampleCriteria = questionKnowledgepointRelationExample.createCriteria();
            questionKnowledgepointRelationExampleCriteria.andQuestionIdEqualTo(id);
            questionKnowledgepointRelationMapper.deleteByExample(questionKnowledgepointRelationExample);
        }

        trueOrFalseQuestionDb.setContent(questionContent);
        trueOrFalseQuestionDb.setAnswer(questionAnswer);
        trueOrFalseQuestionDb.setCourseId(questionCourseId);
        trueOrFalseQuestionDb.setDifficulty(questionDifficultyId);
        trueOrFalseQuestionDb.setUpdatedTime(new Date());

        //更新题目
        trueOrFalseQuestionMapper.updateByPrimaryKeyWithBLOBs(trueOrFalseQuestionDb);

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_PUT_SUCCESS,null);
    }

    @Override
    public ResultInfo addTrueOrFalseQuestionByExcel(String createdTeacherId, String fileName, byte[] uploadData) throws Exception {
        //创建题目教师id不能为空
        if(StringUtils.isBlank(createdTeacherId))
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_ID_NOT_NULL,null);

        //添加的题目的教师必须存在
        Sysuser createdTeacher = sysuserMapper.selectByPrimaryKey(createdTeacherId);
        if(createdTeacher==null)
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_CREATED_TEACHER_NOT_EXIST,null);

        //将上传的文件写到磁盘

        //获得上传文件所在磁盘路径
        String absolutePath = FileUtil.byte2File(uploadData,FILE_PATH_PRE_TRUEORFALSEQUESTION_EXCEL,UUIDBuild.getUUID()+fileName.substring(fileName.lastIndexOf(".")));

        //读取文件
        Workbook book = POIUtils.getExcelWorkbook(absolutePath);
        Sheet sheet = POIUtils.getSheetByNum(book,1);

        List<TrueOrFalseQuestionDto> questionListFromExcel=null;

        try {
            questionListFromExcel = getTrueOrFalseQuestionListFromExcel(book, sheet);
        }catch (Exception e){
            return new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY,MESSAGE_PARAM_NOT_MATCH,null);
        }

        //处理成功的数量
        Integer countSuccess = 0;
        //处理失败的数量
        Integer countError = 0;
        //错误信息
        List<ResultInfo> errorDetails=new ArrayList<>();

        for(int i=0;i<questionListFromExcel.size();i++){

            TrueOrFalseQuestionDto trueOrFalseQuestionDto=questionListFromExcel.get(i);

            String errorMessage="";
            //题目内容不能为空
            String content=trueOrFalseQuestionDto.getContent();
            if(StringUtils.isBlank(content)){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=(i+1)+":内容不能为空";
                else
                    errorMessage+=",内容不能为空";
            }else
                //名字预处理
                content=content.trim();

            //答案不能为空
            Boolean answer=trueOrFalseQuestionDto.getAnswer();
            if(answer==null){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=(i+1)+":答案不能为空";
                else
                    errorMessage+=",答案不能为空";
            }

            //难度不能为空
            Integer difficulty = trueOrFalseQuestionDto.getDifficulty();
            if(difficulty==null){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=(i+1)+":难度不能为空";
                else
                    errorMessage+=",难度不能为空";
            }

            //课程名字不能为空
            String courseName=trueOrFalseQuestionDto.getCourseName();
            if(StringUtils.isBlank(courseName)){
                if(StringUtils.isBlank(errorMessage))
                    errorMessage+=(i+1)+":课程名字不能为空";
                else
                    errorMessage+=",课程名字不能为空";
            }else
                //课程名字预处理
                courseName=courseName.trim();

            //对应难度存在
            if(difficulty!=null){
                DictInfoExample dictInfoExample=new DictInfoExample();
                DictInfoExample.Criteria dictInfoCriteria = dictInfoExample.createCriteria();
                dictInfoCriteria.andCodeEqualTo(String.valueOf(difficulty));
                dictInfoCriteria.andDictTypeIdEqualTo(DICTTYPE_DIFFICULTY_ID);
                List<DictInfo> dictInfoList = dictInfoMapper.selectByExample(dictInfoExample);
                if(CollectionUtils.isEmpty(dictInfoList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=(i+1)+":不存在该难度";
                    else
                        errorMessage+=",不存在该难度";
                }
            }

            //对应课程存在
            Course course=null;
            if(!StringUtils.isBlank(courseName)){
                CourseExample courseExample=new CourseExample();
                CourseExample.Criteria courseCriteria = courseExample.createCriteria();
                courseCriteria.andNameEqualTo(courseName);
                List<Course> courseList = courseMapper.selectByExample(courseExample);
                if(CollectionUtils.isEmpty(courseList)){
                    if(StringUtils.isBlank(errorMessage))
                        errorMessage+=(i+1)+":不存在该课程";
                    else
                        errorMessage+=",不存在该课程";
                }else
                    course=courseList.get(0);
            }

            if(!StringUtils.isBlank(errorMessage)){
                countError++;
                ResultInfo resultInfo = new ResultInfo(ResultInfo.STATUS_RESULT_UNPROCESABLE_ENTITY, errorMessage, null);
                errorDetails.add(resultInfo);
                continue;
            }

            //将该题目添加到系统中
            TrueOrFalseQuestion trueOrFalseQuestion=new TrueOrFalseQuestion();
            String questionId = UUIDBuild.getUUID();
            trueOrFalseQuestion.setId(questionId);
            trueOrFalseQuestion.setContent(content);
            System.out.println(answer);
            trueOrFalseQuestion.setAnswer(answer);
            trueOrFalseQuestion.setType(new Integer(DICTINFO_TRUEORFALSEQUESTION_TYPE_CODE));
            trueOrFalseQuestion.setDifficulty(difficulty);
            trueOrFalseQuestion.setCourseId(course.getId());
            trueOrFalseQuestion.setCreatedTeacherId(createdTeacherId);
            trueOrFalseQuestion.setIsChecked(true);
            trueOrFalseQuestion.setCreatedTime(new Date());
            trueOrFalseQuestion.setUpdatedTime(new Date());
            trueOrFalseQuestionMapper.insert(trueOrFalseQuestion);
            countSuccess++;
        }

        return new ResultInfo(ResultInfo.STATUS_RESULT_CREATED,MESSAGE_POST_SUCCESS,countSuccess,errorDetails);
    }

    private List getTrueOrFalseQuestionListFromExcel(Workbook book,Sheet sheet) throws Exception{
        System.out.println("sheet名称是："+sheet.getSheetName());

        int lastRowNum = sheet.getLastRowNum();

        List<TrueOrFalseQuestionDto> trueOrFalseQuestionDtoList=new ArrayList<>();

        for(int i=1;i<=lastRowNum;i++){
            Row row = sheet.getRow(i);
            if(row != null){

                TrueOrFalseQuestionDto trueOrFalseQuestionDto=new TrueOrFalseQuestionDto();

                Cell cell1 = row.getCell(0);
                String content=null;
                if(cell1 != null){
                    content=cell1.getStringCellValue();
                    trueOrFalseQuestionDto.setContent(content);
                }

                Cell cell2 = row.getCell(1);
                Boolean answer=null;
                if(cell2 != null){
                    int ans=(int)cell2.getNumericCellValue();
                    if(ans==0)
                        answer=false;
                    else
                        answer=true;
                    trueOrFalseQuestionDto.setAnswer(answer);
                }

                Cell cell3 = row.getCell(2);
                Integer difficulty=null;
                if(cell3 != null){
                    difficulty=(int)cell3.getNumericCellValue();
                    trueOrFalseQuestionDto.setDifficulty(difficulty);
                }

                Cell cell4 = row.getCell(3);
                String courseName=null;
                if(cell4 != null){
                    courseName=cell4.getStringCellValue();
                    trueOrFalseQuestionDto.setCourseName(courseName);
                }

                if(StringUtils.isBlank(content)&&answer==null&&difficulty==null&&StringUtils.isBlank(courseName))
                    continue;
                trueOrFalseQuestionDtoList.add(trueOrFalseQuestionDto);
            }
        }
        return trueOrFalseQuestionDtoList;
    }
}
