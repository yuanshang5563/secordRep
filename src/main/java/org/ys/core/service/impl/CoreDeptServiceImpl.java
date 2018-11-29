package org.ys.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.common.utils.BuildTreeUtil;
import org.ys.core.dao.CoreDeptMapper;
import org.ys.core.model.CoreDept;
import org.ys.core.model.CoreDeptExample;
import org.ys.core.model.CoreDeptExample.Criteria;
import org.ys.core.service.CoreDeptService;

import com.github.pagehelper.PageHelper;

@Service("coreDeptService")
public class CoreDeptServiceImpl implements CoreDeptService {
	@Autowired
	private CoreDeptMapper coreDeptMapper;

	@Override
	public CoreDept queryCoreDeptById(Long coreDeptId) throws Exception {
		if(null == coreDeptId) {
			return null;
		}
		return coreDeptMapper.selectByPrimaryKey(coreDeptId);
	}

	@Override
	public void save(CoreDept coreDept) throws Exception {
		if(null != coreDept) {
			coreDeptMapper.insert(coreDept);
		}
	}

	@Override
	public void updateById(CoreDept coreDept) throws Exception {
		if(null != coreDept) {
			coreDeptMapper.updateByPrimaryKey(coreDept);
		}
	}

	@Override
	public void updateByExaple(CoreDept coreDept, CoreDeptExample example) throws Exception {
		if(null != coreDept && null != example) {
			coreDeptMapper.updateByExample(coreDept, example);
		}
	}

	@Override
	public void delCoreDeptById(Long coreDeptId) throws Exception {
		if(null != coreDeptId) {
			coreDeptMapper.deleteByPrimaryKey(coreDeptId);
		}
	}

	@Override
	public List<CoreDept> queryCoreDeptsByExample(CoreDeptExample example) throws Exception {
		if(null == example) {
			return null;
		}
		return coreDeptMapper.selectByExample(example);
	}

	@Override
	public PageBean<CoreDept> pageCoreDeptsByExample(CoreDeptExample example, int pageNum, int pageSize) throws Exception {
		if(null == example) {
			return null;
		}
		PageHelper.startPage(pageNum, pageSize, true);
		List<CoreDept> deptList = coreDeptMapper.selectByExample(example);
		return new PageBean<CoreDept>(deptList);
	}

	@Override
	public Tree<CoreDept> getCoreDeptTree(CoreDeptExample example) throws Exception {
		List<Tree<CoreDept>> trees = new ArrayList<Tree<CoreDept>>();
		List<CoreDept> sysDepts = coreDeptMapper.selectByExample(example);
		for (CoreDept sysDept : sysDepts) {
			Tree<CoreDept> tree = new Tree<CoreDept>();
			tree.setId(sysDept.getCoreDeptId().toString());
			if(null != sysDept.getParentCoreDeptId()) {
				tree.setParentId(sysDept.getParentCoreDeptId().toString());
			}
			tree.setText(sysDept.getDeptName());
			Map<String, Object> state = new HashMap<String, Object>();
			state.put("opened", true);
			tree.setState(state);
			//Map<String, Object> attributes = new HashMap<String, Object>();
			//attributes.put("deptCode", sysDept.getDeptCode());
			//tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<CoreDept> tree = BuildTreeUtil.build(trees);
		return tree;
	}
	
	@Override
	public List<CoreDept> queryCoreDeptsByParentId(Long parentId) throws Exception {
		if(null == parentId) {
			return null;
		}
		CoreDeptExample example = new CoreDeptExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentCoreDeptIdEqualTo(parentId);
		return coreDeptMapper.selectByExample(example);
	}
	
	private Set<CoreDept> queryAllSubCoreDeptsByDeptId(Long coreDeptId,Set<CoreDept> allSubDepts,List<CoreDept> allDepts) throws Exception  {
        for(CoreDept dept : allDepts){
        	if(dept.getCoreDeptId() == coreDeptId) {
        		allSubDepts.add(dept);
        	}
            //遍历出父id等于参数的id，add进子节点集合
            if(dept.getParentCoreDeptId()==coreDeptId){
                //递归遍历下一级
            	queryAllSubCoreDeptsByDeptId(dept.getCoreDeptId(),allSubDepts,allDepts);
                allSubDepts.add(dept);
            }
        }
        return allSubDepts;	
	}

	@Override
	public Set<CoreDept> queryAllSubCoreDeptsByDeptId(Long coreDeptId) throws Exception {
		if(null == coreDeptId) {
			return null;
		}
		Set<CoreDept> allSubDepts = new HashSet<>();
		//一次找出所有节点然后处理
		CoreDeptExample example = new CoreDeptExample();
		List<CoreDept> allDepts = coreDeptMapper.selectByExample(example);
		return queryAllSubCoreDeptsByDeptId(coreDeptId,allSubDepts,allDepts);
	}

}
