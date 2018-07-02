package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.Major;

/**
 * Created by Administrator on 2018/2/2.
 * 专业视图类
 */
public class MajorVo extends Major {
    @Override
    public void setName(String name) {
        try {
            //System.out.println("初始------"+name);
            super.setName(new String(name.getBytes("iso8859-1"), "utf-8"));
        }catch (Exception e){
        }
    }
}
