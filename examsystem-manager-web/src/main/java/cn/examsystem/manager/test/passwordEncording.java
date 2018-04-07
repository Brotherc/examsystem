package cn.examsystem.manager.test;

import cn.examsystem.common.utils.UUIDBuild;

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

/*        // 创建集合对象
        ArrayList array = new ArrayList();

        // 添加多个字符串元素(包含内容相同的)
        array.add("hello");
        array.add("world");
        array.add("java");
        array.add("world");
        array.add("java");
        array.add("world");
        array.add("world");
        array.add("world");
        array.add("world");
        array.add("java");
        array.add("world");

        // 由选择排序思想引入，我们就可以通过这种思想做这个题目
        // 拿0索引的依次和后面的比较，有就把后的干掉
        // 同理，拿1索引...
        for (int x = 0; x < array.size() - 1; x++) {
            for (int y = x + 1; y < array.size(); y++) {
                if (array.get(x).equals(array.get(y))) {
                    array.remove(y);
                    y--;
                }
            }
        }

        // 遍历集合
        Iterator it = array.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            System.out.println(s);
        }*/

/*        Date today=new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        System.out.println(today);*/

    }
}
