package cn.examsystem.security.service;

import cn.examsystem.rest.mapper.ModuleMapperCustom;
import cn.examsystem.rest.mapper.RoleMapper;
import cn.examsystem.rest.mapper.SysuserMapper;
import cn.examsystem.rest.mapper.UserRoleRelationMapper;
import cn.examsystem.rest.pojo.dto.Menu;
import cn.examsystem.rest.pojo.po.*;
import cn.examsystem.security.pojo.dto.SysuserDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 * 系统用户认证业务类
 */
public class SysuserDetailService implements UserDetailsService{

    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ModuleMapperCustom moduleMapperCustom;

    /**
     * 根据用户名查询是否有该用户（在该系统中，学号或工号即对应的用户名）
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名（sysuserId）查询用户
        SysuserExample sysuserExample=new SysuserExample();
        SysuserExample.Criteria sysuserCriteria=sysuserExample.createCriteria();
        sysuserCriteria.andSysuserIdEqualTo(username);
        List<Sysuser> sysuserList=sysuserMapper.selectByExample(sysuserExample);
        System.out.println("--------------------走这了");

        //如果不存在返回null
        if(sysuserList==null||sysuserList.size()<1){
            System.out.println("--------------找不到");
            return null;
        }
        else{//存在该用户
            Sysuser sysuser=sysuserList.get(0);

            //构造userDetails
            SysuserDto sysuserDto=new SysuserDto();
            BeanUtils.copyProperties(sysuser,sysuserDto);

            //根据用户id查询对应用户角色关系
            UserRoleRelationExample userRoleRelationExample=new UserRoleRelationExample();
            UserRoleRelationExample.Criteria userRoleCriteria = userRoleRelationExample.createCriteria();
            userRoleCriteria.andUserIdEqualTo(sysuser.getId());
            List<UserRoleRelation> userRoleRelationList = userRoleRelationMapper.selectByExample(userRoleRelationExample);

            System.out.println(userRoleRelationList);

            //根据该用户角色关系构造对应所拥有的角色id
            List<String> roleIdList=new ArrayList<>();
            for(UserRoleRelation userRoleRelation:userRoleRelationList){
                System.out.println(userRoleRelation.getRoleId());
                roleIdList.add(userRoleRelation.getRoleId());
            }

            //查询用户对应角色id所能操作的资源
            List<Menu> menuList = moduleMapperCustom.listMenuByRoleIds(roleIdList);
            sysuserDto.setMenus(menuList);

            Collection<SimpleGrantedAuthority> authorities= (Collection<SimpleGrantedAuthority>) sysuserDto.getAuthorities();

            //第一次登录，需要记载用户所对应的权限，即角色代码
            if(CollectionUtils.isEmpty(authorities)){

                //添加用户代码，即成功登录该系统的用户
                SimpleGrantedAuthority sga=new SimpleGrantedAuthority("ROLE_USER");
                authorities.add(sga);

                //根据角色id查询相应角色信息
                RoleExample roleExample=new RoleExample();
                RoleExample.Criteria roleCriteria = roleExample.createCriteria();
                roleCriteria.andIdIn(roleIdList);
                List<Role> roleList = roleMapper.selectByExample(roleExample);

                //根据角色信息构造角色代码，并添加到用户权限中
                for(Role role:roleList){
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleCode());
                    authorities.add(authority);
                }

                //测试
                Collection<SimpleGrantedAuthority> authoritiesTest= (Collection<SimpleGrantedAuthority>) sysuserDto.getAuthorities();
                for(SimpleGrantedAuthority simpleGrantedAuthority:authoritiesTest){
                    System.out.println(simpleGrantedAuthority.getAuthority());
                }

            }

            //判断该用户是否可用
            Integer status = sysuser.getStatus();
            if(status==0){
                System.out.println("不可用");
                sysuserDto.setEnabled(false);
            }

            System.out.println("--------------找到");
            return sysuserDto;
        }

    }
}
