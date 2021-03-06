package com.ysd.iep.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.iep.dao.CourseRepository;
import com.ysd.iep.entity.Course;
import com.ysd.iep.entity.dto.CourseDTO;
import com.ysd.iep.entity.dto.PagingResult;
import com.ysd.iep.entity.dto.Result;
import com.ysd.iep.entity.query.CourseQuery;
import com.ysd.iep.feign.AdminFeign;
import com.ysd.iep.service.CourseService;
import com.ysd.iep.service.TeachersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "/course", tags = "课程")
@RestController
@RequestMapping("/course")
public class CourseController{
    @Autowired
    private CourseService courseService;
    @Autowired
    private AdminFeign adminFeign;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeachersService teachersService;

    /**
     * course/getCourUIPage
     *  课程的分页查询(前台 )
     * @param courseQuery
     * @return
     */
  /*  @ApiOperation(value = "前台课程分页")
    @GetMapping("/getCourUIPage")
    public PagingResult getCourUIPage(CourseQuery courseQuery){
        System.out.println(courseQuery);
        return courseService.queryCourseBydepid(courseQuery);
    }*/


    @GetMapping("/getByDepartId")
    public List<Integer> get(@RequestParam("departmentId") String departmentId){
        List<String> teaIdByDepartmentId = teachersService.getTeaIdByDepartmentId(departmentId);
        return courseService.queryCourByteaId(teaIdByDepartmentId);
    }
    /**
     * @param courseQuery
     * @return
     */
     @ApiOperation(value = "课程分页")
    @GetMapping("/getPaginate")
    public Page<Course> getPaginate(CourseQuery courseQuery) {
        System.out.println(courseQuery);
        return courseService.getPaginate(courseQuery);
    }

    /**
     * @param courseQuery
     * @return
     */
    @GetMapping("/queryDTO")
    public PagingResult<Course> queryDTO(CourseQuery courseQuery) {
        System.out.println(courseQuery);
        Page<Course> pages = courseService.getPaginate(courseQuery);
        return new PagingResult<Course>().setTotal(pages.getTotalElements()).setRows(pages.getContent());
    }

    @ApiOperation(value = "获取老师菜单")
    @GetMapping("/getMenu")
    public Result getMenu() {
        return adminFeign.getMenu();
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("/deleteCourseById")
    public Result deleteC(Integer courId,Integer chaCourId) {


        courseService.deleteById(courId);
           return new Result(true);
    }

    @ApiOperation(value = "增加课程")
    @PostMapping("addCourseAll")
    public Result addCourse(Course course) {
        // UUID.randomUUID().toString();
        Result add = courseService.insertCourse(course);
        //teachersService.insertTeacher(teachers);
        return new Result(true);
    }

    @ApiOperation(value = "修改课程")
    @PostMapping("updateCourseAll")
    public Result updateCourseAll(Course course) {
    	System.out.println("课程>>>>"+course);
        Result add = courseService.updateCourse(course);
        return new Result(true);
    }

    /**
     * course/getCourUIPage
     * 课程的分页查询(前台 )
     *
     * @param
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "前台课程分页")
    @GetMapping("/getCourUIPage")
    public Result getCourUIPage(CourseQuery courseQuery) {
        Page<Course> courses = courseService.queryCourseDepidAllPage(courseQuery);
        if(courses==null){
            Map map=new HashMap<>();
            map.put("content",new Object[0]);
            map.put("totalElements",0);
            return new Result<Map>(true, map);
        }
        return new Result<Page<Course>>(true, courses);
    }

    @ApiOperation(value = "根据课程id查询课程信息")
    @GetMapping("/findCourseById")
    public List<CourseDTO> findCourseById(@ApiParam(name = "courId", value = "课程id", required = true) @RequestParam("courId") String courId) {
        return courseService.findByCourseId(courId);

    }

    /**
     * 根据教师Id查询课程
     *
     * @param teaId
     * @return
     */
    @ApiOperation(value = "根据教师Id查询课程")
    @GetMapping("queryCourByteaId")
    public Result<List<Course>> queryCourByteaId(@ApiParam(name = "teaId", value = "老师Id", required = true) @RequestParam("teaId") String teaId) {
        System.out.println("我的教师Id" + teaId);
        List<Course> list = courseService.queryCourByteaId(teaId);
        System.out.println("我的list" + list);
        return new Result<List<Course>>(true, list);
    }

    @ApiOperation(value = "提供 根据课程id修改报名人数")
    @PutMapping("updateCourStudypeople")
    public void updateCourStudypeople(@RequestParam("courId") Integer courId) {
        courseRepository.updateCourStudypeople(courId);
    }
    
    @ApiOperation(value = "提供  根据院系id查询课程(按报名人数降序取6条)")
    @GetMapping("queryCourByDepId")
    public Result<List<Course>> queryCourByDepId(@RequestParam("depid") String depid){
        List<String> teaIdByDepartmentId = teachersService.getTeaIdByDepartmentId(depid);
        if(teaIdByDepartmentId.size()==0){
            return new Result<>(true,new ArrayList<Course>());
        }
        List<Course> list = courseRepository.queryCourByDepId(teaIdByDepartmentId);
        return new Result<List<Course>>(true,list);
    }
    
    @ApiOperation(value = "提供  提供根据课程id查询单个课程信息")
    @GetMapping("queryCourByid")
    public Result<Course> queryCourByid(@RequestParam("courid") Integer courid){
        Course cour = courseService.queryCourByid(courid);
        return new Result<Course>(true,cour);
    }
   

}

