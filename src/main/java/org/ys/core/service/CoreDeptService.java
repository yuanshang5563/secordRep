package org.ys.core.service;

import java.util.List;
import java.util.Set;

import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.core.model.CoreDept;
import org.ys.core.model.CoreDeptExample;

public interface CoreDeptService {
	/**
	 * 根据id查询
	 * @param coreDeptId
	 * @return
	 * @throws Exception
	 */
	public CoreDept queryCoreDeptById(Long coreDeptId) throws Exception;

	/**
	 * 保存
	 * @param coreDept
	 * @throws Exception
	 */
	public int save(CoreDept coreDept) throws Exception;

	/**
	 * 根据id更新
	 * @param coreDept
	 * @throws Exception
	 */
	public int updateById(CoreDept coreDept) throws Exception;

	/**
	 * 根据条件更新
	 * @param coreDept
	 * @param example
	 * @throws Exception
	 */
	public int updateByExaple(CoreDept coreDept,CoreDeptExample example) throws Exception;

	/**
	 * 根据Id删除
	 * @param coreDeptId
	 * @throws Exception
	 */
	public int delCoreDeptById(Long coreDeptId) throws Exception;

	/**
	 * 根据指定条件查询
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<CoreDept>queryCoreDeptsByExample(CoreDeptExample example) throws Exception;

	/**
	 * 根据指定条件分页
	 * @param example
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageBean<CoreDept> pageCoreDeptsByExample(CoreDeptExample example,int pageNum,int pageSize) throws Exception;

	/**
	 * 根据指定条件获取树
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public Tree<CoreDept> getCoreDeptTree(CoreDeptExample example) throws Exception;

	/**
	 * 根据父id查询子节点
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<CoreDept> queryCoreDeptsByParentId(Long parentId) throws Exception;

	/**
	 * 查询某个id下的所有子节点
	 * @param coreDeptId
	 * @return
	 * @throws Exception
	 */
	public Set<CoreDept> queryAllSubCoreDeptsByDeptId(Long coreDeptId) throws Exception;
}