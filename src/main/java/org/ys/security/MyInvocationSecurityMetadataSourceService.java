package org.ys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.ys.common.constant.CoreMenuType;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;
import org.ys.core.service.CoreMenuService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private CoreMenuService coreMenuService;

    private HashMap<String, Collection<ConfigAttribute>> map =null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg = null;
        CoreMenuExample example = new CoreMenuExample();
        example.createCriteria().andMenuTypeEqualTo(CoreMenuType.MENU_TYPE_PERMISSION);
		List<CoreMenu> permissions = null;
		try {
			permissions = coreMenuService.queryCoreMenusByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != permissions && permissions.size() > 0) {
	        for(CoreMenu coreMenu : permissions) {
	            array = new ArrayList<>();
	            cfg = new SecurityConfig(coreMenu.getPermission());
	            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
	            array.add(cfg);
	            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
	            map.put(coreMenu.getMenuUrl(), array);
	        }			
		}
    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map ==null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}