package org.ys.core.service;

import java.util.List;
import java.util.Set;

import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;

/**
 * 菜单操作接口
 */
public interface CoreMenuService {
	/**
	 * 根据id查找
	 * @param coreMenuId
	 * @return
	 * @throws Exception
	 */
	public CoreMenu queryCoreMenuById(Long coreMenuId) throws Exception;

	/**
	 * 保存
	 * @param coreMenu
	 * @throws Exception
	 */
	public void save(CoreMenu coreMenu) throws Exception;

	/**
	 * 根据id更新
	 * @param coreMenu
	 * @throws Exception
	 */
	public void updateById(CoreMenu coreMenu) throws Exception;

	/**
	 * 根据指定条件更新
	 * @param corePermission
	 * @param example
	 * @throws Exception
	 */
	public void updateByExaple(CoreMenu corePermission,CoreMenuExample example) throws Exception;

	/**
	 * 根据id删除
	 * @param coreMenuId
	 * @throws Exception
	 */
	public void delCoreMenuById(Long coreMenuId) throws Exception;

	/**
	 * 根据指定条件查找
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<CoreMenu>queryCoreMenusByExample(CoreMenuExample example) throws Exception;

	/**
	 * 查找子节点
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<CoreMenu> queryCoreMenusByParentId(Long parentId) throws Exception;

	/**
	 * 查找某个节点下所有节点
	 * @param coreMenuId
	 * @return
	 * @throws Exception
	 */
	public Set<CoreMenu> queryAllSubCoreMenusByMenuId(Long coreMenuId) throws Exception;

	/**
	 * 根据指定条件分页
	 * @param example
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageBean<CoreMenu> pageCoreMenusByExample(CoreMenuExample example,int pageNum,int pageSize) throws Exception;

	/**
	 * 查找某个用户的节点树
	 * @param coreUserId
	 * @return
	 */
	public List<Tree<CoreMenu>> listMenuTreeByUserId(Long coreUserId);

	/**
	 * 查找某个角色的树
	 * @param coreRoleId
	 * @return
	 */
	public Tree<CoreMenu> getCoreMenuTreeByRoleId(Long coreRoleId);

	/**
	 * 获取菜单树
	 * @return
	 * @throws Exception
	 */
	public Tree<CoreMenu> getCoreMenuTree() throws Exception;

	/**
	 * 查找某个用户的菜单或权限集合
	 * @param coreUserId
	 * @return
	 */
	public List<CoreMenu> listCoreMenusByUserId(Long coreUserId);
}