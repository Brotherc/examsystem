package cn.examsystem.common.utils;

import java.util.*;

public class MathUtils {
    public static Map.Entry<Integer, Integer> getMapMinEntry(Map map){

        List<Map.Entry<Integer,Integer>> list = new ArrayList(map.entrySet());

        Collections.sort(list,new Comparator<Map.Entry<Integer, Integer>>(){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o1.getValue() - o2.getValue());
            }
        } );

        return list.get(0);
    }
}
