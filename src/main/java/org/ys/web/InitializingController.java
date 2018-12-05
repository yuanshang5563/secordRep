package org.ys.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.ys.common.constant.CoreMenuTypeContant;
import org.ys.common.constant.RedisKeyContant;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;
import org.ys.core.model.CoreParameter;
import org.ys.core.model.CoreParameterExample;
import org.ys.core.service.CoreMenuService;
import org.ys.core.service.CoreParameterService;

/**
 * 系统初始化控制类
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Controller
public class InitializingController implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(InitializingController.class);
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private CoreMenuService coreMenuService;
	@Autowired
	private CoreParameterService coreParameterService;

	/**
	 * 系统初始化
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("---------系统初始化开始---------------");
		CoreMenuExample example = new CoreMenuExample();
		List<CoreMenu> allMenuList = coreMenuService.queryCoreMenusByExample(example);
		redisTemplate.opsForList().leftPush(RedisKeyContant.CORE_MENU_ALL_MENUS, allMenuList);
		example.clear();
		example.createCriteria().andMenuTypeEqualTo(CoreMenuTypeContant.MENU_TYPE_PERMISSION);
		List<CoreMenu> allPermissionList = coreMenuService.queryCoreMenusByExample(example);
		redisTemplate.opsForList().leftPush(RedisKeyContant.CORE_MENU_ALL_PERMISSION, allPermissionList);
		CoreParameterExample paramExample = new CoreParameterExample();
		List<CoreParameter> paramList = coreParameterService.queryCoreParametersByExample(paramExample);
		if(null != paramList && paramList.size() > 0) {
			for (CoreParameter coreParameter : paramList) {
				if(StringUtils.isNotEmpty(coreParameter.getParamCode())) {
					redisTemplate.opsForValue().set(RedisKeyContant.CORE_PARAMETER+coreParameter.getParamCode()+":", coreParameter);
				}
			}
		}
		logger.info("---------系统初始化结束---------------");
	}

}
