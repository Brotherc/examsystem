package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.TestPaper;

/**
 * Created by Administrator on 2018/2/4.
 * 课程视图类
 */
public class TestPaperVo extends TestPaper{

    @Override
    public void setName(String name) {
        try {
            super.setName(new String(name.getBytes("iso8859-1"), "utf-8"));
        }catch (Exception e){
        }
    }
}
