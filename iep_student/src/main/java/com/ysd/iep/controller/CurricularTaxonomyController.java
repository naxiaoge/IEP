package com.ysd.iep.controller;
import com.ysd.iep.feign.CourseFeign;
import com.ysd.iep.feign.CurricularFeign;
import com.ysd.iep.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(value="/CurricularTaxonomy", tags="课程分类页面")
@RestController
@RequestMapping("/curricularTaxonomy")
public class CurricularTaxonomyController {

    @Autowired
    private CurricularFeign curricularTaxonomyFeign;
    @Autowired
    private CourseFeign courseFeign;
    /**
     * 获取全部课程分类
     *http://127.0.0.1:8060/api/student/curricularTaxonomy/getCurricularTaxonomy
     * @return Result
     */

   @ApiOperation(value = "获取全部课程分类")
    @GetMapping("/getCurricularTaxonomy")
    public Result getCurricularTaxonomy(){
        return curricularTaxonomyFeign.getCurricularTaxonomy();
    }

    /**
     * 根据课程分类Id显示课程
     * http://127.0.0.1:8060/api/student/curricularTaxonomy/getCourse
     * @param depId
     * @param page
     * @param size
     * @return Result
     */
    @ApiOperation(value = "根据课程分类Id显示课程")
    @GetMapping("/getCourse")
    public Result getCourse(String depId,Integer page, Integer size){
        return courseFeign.getCourseAll(depId,page,size);
    }
    /**
     * http://127.0.0.1:8060/api/student/curricularTaxonomy/getCoursedetails
     * 
     * 课程详情页
     * @param courId
     * @return
     */
    @ApiOperation(value = "根据课程Id显示课程详情")
    @RequestMapping("/getCoursedetails")
    public Object getCoursedetails(@RequestParam("courId") String courId) {
    	return courseFeign.getCoursedetails(courId);
    };
}
