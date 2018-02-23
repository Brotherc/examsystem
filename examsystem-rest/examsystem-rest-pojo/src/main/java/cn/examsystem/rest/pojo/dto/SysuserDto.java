package cn.examsystem.rest.pojo.dto;

import cn.examsystem.rest.pojo.po.Role;
import cn.examsystem.rest.pojo.po.Sysuser;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 * 用户类（包括角色信息,系信息，状态信息）
 */
public class SysuserDto extends Sysuser{

    private String departmentName;

    private String statusName;

    private List<Role> roles;

    private String rolesId;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRolesId() {
        return rolesId;
    }

    public void setRolesId(String rolesId) {
        this.rolesId = rolesId;
    }
}
