package cn.examsystem.rest.quartz;

import cn.examsystem.rest.mapper.ExamMapper;
import cn.examsystem.rest.pojo.po.Exam;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class ExamQuartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map=jobExecutionContext.getJobDetail().getJobDataMap();

        Exam examDb=(Exam)map.get("exam");
        ExamMapper examMapper=(ExamMapper)map.get("examMapper");
        String code=(String)map.get("code");
        //修改考试状态
        examDb.setStatus(new Integer(code));
        examDb.setUpdatedTime(new Date());
        examMapper.updateByPrimaryKey(examDb);
    }
}
