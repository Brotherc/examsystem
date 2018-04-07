package cn.examsystem.rest.service.impl;

import cn.examsystem.rest.mapper.ProgramQuestionMapper;
import cn.examsystem.rest.pojo.dto.ProgramQuestionDto;
import cn.examsystem.rest.pojo.po.ProgramQuestion;
import cn.examsystem.rest.pojo.po.ProgramQuestionExample;
import cn.examsystem.rest.pojo.po.ProgramQuestionWithBLOBs;
import cn.examsystem.rest.service.ProgramQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 程序题业务层实现
 */
@Service
public class ProgramQuestionImpl implements ProgramQuestionService{

    @Value("${MESSAGE_POST_SUCCESS}")
    private String MESSAGE_POST_SUCCESS;

    @Value("${MESSAGE_PUT_SUCCESS}")
    private String MESSAGE_PUT_SUCCESS;


    @Autowired
    private ProgramQuestionMapper programQuestionMapper;

    @Override
    public ProgramQuestionDto getProgramQuestion(String id) throws Exception {
        return null;
    }

    @Override
    public List<ProgramQuestionWithBLOBs> listProgramQuestion(ProgramQuestion programQuestion) throws Exception {
        ProgramQuestionExample programQuestionExample=new ProgramQuestionExample();
        ProgramQuestionExample.Criteria programQuestionCriteria = programQuestionExample.createCriteria();

        //查询条件-xx-不为空
/*        if(programQuestion!=null){
            String xx=programQuestion.getXX();
            if(!StringUtils.isBlank(xx)){
                programQuestionCriteria.andXXEqualTo(xx);
            }
        }*/
        return programQuestionMapper.selectByExampleWithBLOBs(programQuestionExample);
    }
}
