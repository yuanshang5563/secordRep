package org.ys.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.common.constant.CoreMenuTypeContant;
import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.common.utils.BuildTreeUtil;
import org.ys.core.dao.CoreMenuMapper;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;
import org.ys.core.model.CoreMenuExample.Criteria;
import org.ys.core.service.CoreMenuService;

import com.github.pagehelper.PageHelper;

@Service("coreMenuService")
public class CoreMenuServiceImpl implements CoreMenuService{
	@Autowired
	private CoreMenuMapper coreMenuMapper;

	@Override
	public CoreMenu queryCoreMenuById(Long coreMenuId) throws Exception {
		if(null == coreMenuId){
			return null;
		}
		return coreMenuMapper.selectByPrimaryKey(coreMenuId);
	}

	@Override
	public void save(CoreMenu coreMenu) throws Exception {
		if(null != coreMenu) {
			coreMenuMapper.insert(coreMenu);
		}
	}

	@Override
	public void updateById(CoreMenu coreMenu) throws Exception {
		if(null != coreMenu) {
			coreMenuMapper.updateByPrimaryKey(coreMenu);
		}
	}

	@Override
	public void updateByExaple(CoreMenu corePermission, CoreMenuExample example) throws Exception {
		if(null != corePermission && null != example) {
			coreMenuMapper.updateByExample(corePermission, example);
		}
		
	}

	@Override
	public void delCoreMenuById(Long coreMenuId) throws Exception {
		if(null != coreMenuId) {
			coreMenuMapper.deleteByPrimaryKey(coreMenuId);
		}
	}

	@Override
	public List<CoreMenu> queryCoreMenusByExample(CoreMenuExample example) throws Exception {
		if(null == example) {
			return null;
		}
		return coreMenuMapper.selectByExample(example);
	}

	@Override
	public List<CoreMenu> queryCoreMenusByParentId(Long parentId) throws Exception {
		if(null == parentId) {
			return null;
		}
		CoreMenuExample example = new CoreMenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentCoreMenuIdEqualTo(parentId);
		return coreMenuMapper.selectByExample(example);
	}
	
	private Set<CoreMenu> queryAllSubCoreMenusByMenuId(Long coreMenuId,Set<CoreMenu> allSubMenus,List<CoreMenu> allMenus) throws Exception  {
        for(CoreMenu menu : allMenus){
        	if(menu.getCoreMenuId() == coreMenuId) {
        		allSubMenus.add(menu);
        	}
            //遍历出父id等于参数的id，add进子节点集合
            if(menu.getParentCoreMenuId() == coreMenuId){
                //递归遍历下一级
            	queryAllSubCoreMenusByMenuId(menu.getCoreMenuId(),allSubMenus,allMenus);
            	allSubMenus.add(menu);
            }
        }
        return allSubMenus;	
	}

	@Override
	public Set<CoreMenu> queryAllSubCoreMenusByMenuId(Long coreMenuId) throws Exception {
		if(null == coreMenuId) {
			return null;
		}
		Set<CoreMenu> allSubMenus = new HashSet<>();
		//一次找出所有节点然后处理
		CoreMenuExample example = new CoreMenuExample();
		List<CoreMenu> allMenus = coreMenuMapper.selectByExample(example);
		return queryAllSubCoreMenusByMenuId(coreMenuId,allSubMenus,allMenus);
	}

	@Override
	public PageBean<CoreMenu> pageCoreMenusByExample(CoreMenuExample example, int pageNum, int pageSize) throws Exception {
		if(null == example) {
			return null;
		}
		PageHelper.startPage(pageNum, pageSize, true);
		List<CoreMenu> menuList = coreMenuMapper.selectByExample(example);
		return new PageBean<CoreMenu>(menuList);
	}

	@Override
	public List<Tree<CoreMenu>> listMenuTreeByUserId(Long coreUserId) {
		if(null == coreUserId) {
			return null;
		}
		List<Tree<CoreMenu>> trees = new ArrayList<Tree<CoreMenu>>();
		List<CoreMenu> menus = coreMenuMapper.listCoreMenusByUserId(coreUserId);
		for (CoreMenu menu : menus) {
			if(menu.getMenuType() == CoreMenuTypeContant.MENU_TYPE_PERMISSION || menu.getCoreMenuId() == 0l) {
				continue;
			}
			Tree<CoreMenu> tree = new Tree<CoreMenu>();
			tree.setId(menu.getCoreMenuId().toString());
			if(null != menu.getParentCoreMenuId()) {
				tree.setParentId(menu.getParentCoreMenuId().toString());
			}else {
				tree.setParentId("0");
			}
			tree.setText(menu.getMenuName());
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("url", menu.getMenuUrl());
			attributes.put("icon", menu.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<CoreMenu>> list = BuildTreeUtil.buildList(trees, "0");
		return list;
	}

	@Override
	public Tree<CoreMenu> getCoreMenuTree() throws Exception {
		List<Tree<CoreMenu>> trees = new ArrayList<Tree<CoreMenu>>();
		List<CoreMenu> allCoreMenus = coreMenuMapper.selectByExample(new CoreMenuExample());
		for (CoreMenu coreMenu : allCoreMenus) {
			Tree<CoreMenu> tree = new Tree<CoreMenu>();
			tree.setId(coreMenu.getCoreMenuId().toString());
			if(null != coreMenu.getParentCoreMenuId()) {
				tree.setParentId(coreMenu.getParentCoreMenuId().toString());
			}
			tree.setText(coreMenu.getMenuName());
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<CoreMenu> tree = BuildTreeUtil.build(trees);
		return tree;
	}

	@Override
	public Tree<CoreMenu> getCoreMenuTreeByRoleId(Long coreRoleId) {
		if(null == coreRoleId) {
			return null;
		}
		// 根据roleId查询权限
		List<CoreMenu> menus =  coreMenuMapper.selectByExample(new CoreMenuExample());
		List<CoreMenu> roleMenus = coreMenuMapper.listCoreMenusByRoleId(coreRoleId);
		Set<Long> roleMenuIds = new HashSet<Long>();
		for (CoreMenu roleMenu : roleMenus) {
			roleMenuIds.add(roleMenu.getCoreMenuId());
		}
		//只留下叶子节点
		for (CoreMenu roleMenu : roleMenus) {
			if(roleMenuIds.contains(roleMenu.getParentCoreMenuId())) {
				roleMenuIds.remove(roleMenu.getParentCoreMenuId());
			}
		}
		List<Tree<CoreMenu>> trees = new ArrayList<Tree<CoreMenu>>();
		for (CoreMenu coreMenu : menus) {
			Tree<CoreMenu> tree = new Tree<CoreMenu>();
			tree.setId(coreMenu.getCoreMenuId().toString());
			if(null != coreMenu.getParentCoreMenuId()) {
				tree.setParentId(coreMenu.getParentCoreMenuId().toString());
			}
			tree.setText(coreMenu.getMenuName());
			Map<String, Object> state = new HashMap<>();
			if (roleMenuIds.contains(coreMenu.getCoreMenuId())) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<CoreMenu> tree = BuildTreeUtil.build(trees);
		return tree;
	}

	@Override
	public List<CoreMenu> listCoreMenusByUserId(Long coreUserId) {
		if(null == coreUserId) {
			return null;
		}
		return coreMenuMapper.listCoreMenusByUserId(coreUserId);
	}

}
