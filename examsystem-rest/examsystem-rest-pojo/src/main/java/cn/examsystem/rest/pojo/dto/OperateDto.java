package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Operate;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 * 操作（包括该操作所需要角色代码）
 */
public class OperateDto extends Operate{
    private List<String> roleCodes;

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
