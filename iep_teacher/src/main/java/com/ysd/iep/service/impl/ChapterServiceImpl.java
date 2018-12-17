package com.ysd.iep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysd.iep.dao.ChapterRepository;
import com.ysd.iep.entity.Chapters;
import com.ysd.iep.service.ChapterService;
@Service
public class ChapterServiceImpl implements ChapterService{

	@Autowired
	private ChapterRepository chaperRepo;
	
	/**
	 * 查询章节
	 * @return
	 */
	@Override
	public List<Chapters> querychapterTree() {
		List<Chapters> rootList =null;
		rootList = chaperRepo.findAll();
		//查询出所有根菜单
		rootList = chaperRepo.queryTreeChildrenById(0);
		// 递归设置子菜单
		this.setTreeChildrens(rootList);
		System.out.println("rootList==>" + rootList);
		return rootList;
	}

	/**
	 * 给菜单模块 设置孩子
	 * @param parentList
	 */
	private void setTreeChildrens(List<Chapters> parentList) {
		for (Chapters c : parentList) {
			//查出子菜单
			List<Chapters> childrenList=chaperRepo.queryTreeChildrenById(c.getChaId());
			// 如果没有子菜单则递归结束
			if (childrenList == null || childrenList.isEmpty()) {// 有子菜单
			} else {
				// 设置子菜单
				System.out.println("设置的子菜单是=>" + childrenList);
				c.setChildren(childrenList);
				// 如果有子菜单则继续递归设置子菜单
				this.setTreeChildrens(childrenList);
			}
		}
		
	}

}