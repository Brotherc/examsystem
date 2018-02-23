package cn.examsystem.rest.pojo.vo;

import cn.examsystem.rest.pojo.po.Sysuser;

/**
 * Created by Administrator on 2018/2/5.
 */
public class SysuserVo extends Sysuser{
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public void setName(String name) {
        try {
            System.out.println("初始------"+name);
            super.setName(new String(name.getBytes("iso8859-1"), "utf-8"));
        }catch (Exception e){
        }
    }
}
