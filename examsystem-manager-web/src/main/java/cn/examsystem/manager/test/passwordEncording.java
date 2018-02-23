package cn.examsystem.manager.test;

import cn.examsystem.common.utils.DateUtil;
import cn.examsystem.common.utils.UUIDBuild;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/25.
 */
public class passwordEncording {
    public static void main(String[] args){
/*        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String s=encoder.encode("sm");
        System.out.println(s);*/



        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());
        System.out.println(UUIDBuild.getUUID());

        int year=DateUtil.getYear(new Date());
        System.out.println(year+"-"+(year+1));
    }
}
