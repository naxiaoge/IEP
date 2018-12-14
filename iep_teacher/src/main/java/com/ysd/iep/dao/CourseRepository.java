package com.ysd.iep.dao;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ysd.iep.entity.Course;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

	/**
	 * 分页查询  课程(前台)
	 * @param DepId
	 * @param pageable
	 * @return Page<Course>
	 */
	public Page<Course> findByCourDepid(String DepId,Pageable pageable);

	/**
	 * 提供  根据课程id查询课程信息
	 * @return
	 */
	@Query(value="select * from coursetb where cour_id in (1) ",nativeQuery=true)
	public List<Course> findByCourseId(Integer courId);
}
