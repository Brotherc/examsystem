package cn.examsystem.rest.pojo.dto;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 * 菜单（资源）
 */
public class Menu {

    //菜单id、模块id
    private String Id;

    //菜单名称/模块名称
    private String name;

    //图标
    private String icon;

    //菜单链接
    private String url;

    //二级菜单
    private List<Menu> menus;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
