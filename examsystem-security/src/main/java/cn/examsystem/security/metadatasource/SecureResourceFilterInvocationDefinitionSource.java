package cn.examsystem.security.metadatasource;

import cn.examsystem.rest.mapper.OperateMapperCustom;
import cn.examsystem.rest.pojo.dto.OperateDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * Created by Administrator on 2018/1/27.
 * 操作资源权限类
 */
public class SecureResourceFilterInvocationDefinitionSource implements FilterInvocationSecurityMetadataSource,InitializingBean {

    /**
     * 所有操作可能请求的方法
     */
    public static final String URL_METHOD_GET="get";
    public static final String URL_METHOD_POST="post";
    public static final String URL_METHOD_PUT="put";
    public static final String URL_METHOD_DELETE="delete";

    @Autowired
    private OperateMapperCustom operateMapperCustom;

    private PathMatcher matcher;

    /**
     * 外层map的key为get/post/put/delete
     * 内层map的key为对应请求方法的url，值为该url所需要的权限
     */
    private static Map<String, Map<String,Collection<ConfigAttribute>>> operateMap = new HashMap<String, Map<String,Collection<ConfigAttribute>>>();

    //存放get操作的map(key是操作名字（url），value是该操作所需要的角色集合(权限))
    private Map<String, Collection<ConfigAttribute>> methodIsGetMap = new HashMap<String, Collection<ConfigAttribute>>();
    //存放post操作的map(key是操作名字（url），value是该操作所需要的角色集合(权限))
    private Map<String, Collection<ConfigAttribute>> methodIsPostMap = new HashMap<String, Collection<ConfigAttribute>>();
    //存放put操作的map(key是操作名字（url），value是该操作所需要的角色集合(权限))
    private Map<String, Collection<ConfigAttribute>> methodIsPutMap = new HashMap<String, Collection<ConfigAttribute>>();
    //存放delete操作的map(key是操作名字（url），value是该操作所需要的角色集合(权限))
    private Map<String, Collection<ConfigAttribute>> methodIsDeleteMap = new HashMap<String, Collection<ConfigAttribute>>();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.matcher = new AntPathMatcher();//用来匹配访问资源路径

        //初始化map
        operateMap.put(URL_METHOD_GET,methodIsGetMap);
        operateMap.put(URL_METHOD_POST,methodIsPostMap);
        operateMap.put(URL_METHOD_PUT,methodIsPutMap);
        operateMap.put(URL_METHOD_DELETE,methodIsDeleteMap);

        //获取所有操作（包括该操作所需要的角色）
        List<OperateDto> operateDtoList = operateMapperCustom.listOperateIncludeRole();

        System.out.println("---------------------"+operateDtoList);

        for(OperateDto operateDto:operateDtoList){

            //获取该操作的url请求方法
            String operateMethod = operateDto.getMethod().trim();
            //获取该操作的url
            String operateUrl=operateDto.getUrl();
            //获取该操作所需要的角色代码(权限)
            List<String> roleCodeList = operateDto.getRoleCodes();

            //将请求方法-url-权限一一对应到map中
            if(URL_METHOD_GET.equals(operateMethod)){
                initializeMethodMap(methodIsGetMap,operateUrl,roleCodeList);
            }else if(URL_METHOD_POST.equals(operateMethod)){
                initializeMethodMap(methodIsPostMap,operateUrl,roleCodeList);
            }else if(URL_METHOD_PUT.equals(operateMethod)){
                initializeMethodMap(methodIsPutMap,operateUrl,roleCodeList);
            }else{
                initializeMethodMap(methodIsDeleteMap,operateUrl,roleCodeList);
            }
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;

        //获取请求url
        String requestURI = filterInvocation.getRequestUrl();
        //获取请求方法
        String method = filterInvocation.getHttpRequest().getMethod();

        System.out.println("------------------请求方法"+method);
        System.out.println("------------------请求url"+requestURI);

        //返回该请求方法的url所需要的权限
        if(StringUtils.equalsIgnoreCase(URL_METHOD_GET,method)){
            return listOperateNeedRole(methodIsGetMap,requestURI);
        }else if(StringUtils.equalsIgnoreCase(URL_METHOD_POST,method)){
            return listOperateNeedRole(methodIsPostMap,requestURI);
        }else if(StringUtils.equalsIgnoreCase(URL_METHOD_PUT,method)){
            return listOperateNeedRole(methodIsPutMap,requestURI);
        }else if(StringUtils.equalsIgnoreCase(URL_METHOD_DELETE,method)){
            return listOperateNeedRole(methodIsDeleteMap,requestURI);
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


    private void initializeMethodMap(Map<String,Collection<ConfigAttribute>> urlMap,String operateUrl,List<String> roleCodeList){
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        for(String roleCode:roleCodeList){
            ConfigAttribute ca = new SecurityConfig(roleCode);
            atts.add(ca);
        }
        urlMap.put(operateUrl,atts);
    }

    private Collection<ConfigAttribute> listOperateNeedRole(Map<String,Collection<ConfigAttribute>> urlMap,String requestURI){
        //循环资源路径，当访问的Url和资源路径url匹配时，返回该Url所需要的权限
        for(Iterator<Map.Entry<String, Collection<ConfigAttribute>>> iter = urlMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, Collection<ConfigAttribute>> entry = iter.next();
            String url = entry.getKey();

            if(matcher.match(url+"/**", requestURI)) {
                System.out.println(url+"-------"+requestURI);
                System.out.println(urlMap.get(url));
                return urlMap.get(url);
            }
        }
        return null;
    }
}
