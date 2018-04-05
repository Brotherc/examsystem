package cn.examsystem.security.pojo.dto;

import cn.examsystem.rest.pojo.dto.Menu;
import cn.examsystem.rest.pojo.po.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 * 系统用户实体业务类，用户认证
 */
public class StudentDto extends Student implements UserDetails{

    private Boolean isShowPracticeModule;

    private String username;

    /**
     * 用户可操作的资源（菜单）
     */
    private List<Menu> menus;

    /**
     * 用户所拥有的权限
     */
    private Collection<? extends GrantedAuthority> authorities=new ArrayList<>();

    /**
     * 是否可用
     */
    private boolean isEnabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username){
        this.username=username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getShowPracticeModule() {
        return isShowPracticeModule;
    }

    public void setShowPracticeModule(Boolean showPracticeModule) {
        isShowPracticeModule = showPracticeModule;
    }
}
