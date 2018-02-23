package cn.examsystem.manager.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/22.
 */
public class DateConverter implements Converter<String,Date>{
    @Override
    public Date convert(String s) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(s);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
