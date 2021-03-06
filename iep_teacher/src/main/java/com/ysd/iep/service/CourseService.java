package com.ysd.iep.service;

import com.ysd.iep.annotation.PermissionMethod;
import com.ysd.iep.annotation.PermissionType;
import com.ysd.iep.entity.Course;
import com.ysd.iep.entity.dto.CourseDTO;
import com.ysd.iep.entity.dto.PagingResult;
import com.ysd.iep.entity.dto.Result;
import com.ysd.iep.entity.query.CourseQuery;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@PermissionType("课程")
public interface CourseService {
    /**
     * 课程的分页查询
     * @param
     * @param
     * @param
     * @return
     */
    Page<Course> getPaginate(CourseQuery courseQuery);

    /**
     * 删除课程
     * @param
     */
    Result deleteById(Integer courId);

     /**
      * 课程的分页查询(前台)
      * @param
      * @param
      * @param
      * @return  Page<Course>
      */
     public Page<Course> queryCourseDepidAllPage(CourseQuery courseQuery);


    /**
     * 新增课程
     * @param course
     * @return
     */
    Result insertCourse(Course course);
    
    /**
     * 修改课程
     * @param course
     * @return
     */
    @PreAuthorize("hasAuthority('course:updateCourse')")
    @PermissionMethod("修改课程")
    
    Result updateCourse(Course course);
    /**
     *根据课程id查询课程
     * @param courId
     * @return
     */

    List<CourseDTO> findByCourseId(String courId);
    /**
     * 根据教师Id查询课程
     * @param teaId
     * @return
     */
    public List<Course> queryCourByteaId(String teaId);


    List<Integer> queryCourByteaId(List<String> teaIdByDepartmentId);
    
    public Course queryCourByid(Integer courid);
    
    PagingResult<Course> queryCourseBydepid(CourseQuery courseQuery);
    


}
