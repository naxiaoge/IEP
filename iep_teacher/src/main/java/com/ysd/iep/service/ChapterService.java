package com.ysd.iep.service;

import java.util.List;

import com.ysd.iep.entity.Chapters;
import com.ysd.iep.entity.Course;
import com.ysd.iep.entity.dto.Result;

public interface ChapterService {
	/**
	 * 查询章节
	 */
	public List<Chapters> querychapterTree(Integer courid);
	/**
	 * 新增章节
	 * @param chapters
	 * @return
	 */
	Result insertChapters(Chapters chapters);

	/**
	 * 删除章节
	 * @param chaId
	 */
	Result deleteChapters(Integer chaId);
  
    /**
     * 修改章节
     * @param chapters
     * @return
     */
    public Result updateCourse(Chapters chapters);
	/**
	 * 根据章节id修改视频的路径和视频的时长
	 * @param chaViurl
	 * @param chaTime
	 * @param courId
	 * @return
	 */
	public Result updateChaViurlAtime(String chaViurl,String chaTime,String chaName,Integer courId);
}
