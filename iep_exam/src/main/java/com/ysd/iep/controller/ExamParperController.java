package com.ysd.iep.controller;

import com.ysd.iep.entity.Examparper;
import com.ysd.iep.entity.parameter.Result;
import com.ysd.iep.entitySerch.ExamParperSerch;
import com.ysd.iep.service.ExamparperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.PastOrPresent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/examParper")
public class ExamParperController {
    @Autowired
    private ExamparperService examparperService;

    /***
     * 试卷管理的多条件分页显示
     * @param examParperSerch
     * @return
     */
    @GetMapping ("getqueryqueryByDynamicSQLPageExpaerper")
    private Object getqueryqueryByDynamicSQLPageExpaerper(ExamParperSerch examParperSerch){
        Page<Examparper> page=examparperService.queryqueryByDynamicSQLPageExpaerper(examParperSerch);
        Long total = page.getTotalElements();
        List<Examparper> list=page.getContent();
        for (Examparper examparper : list) {
            examparper.setExamrubricslist(null);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("total", total);
        map.put("rows", list);

        return map;
    }

    /***
     * 点击开始考试按钮
     * @param examparper
     * @return
     */
    @PutMapping("updateStartExamtime")
    public Result updateStartExamtime(Examparper examparper){
      return   examparperService.updateStartExamtime(examparper);
    }

    /***
     * 随机生成试卷
     * @param examParperSerch
     * @return
     */
    @PostMapping("addRandomExamparper")
    public Result addRandomExamparper(ExamParperSerch examParperSerch){
        return examparperService.addRandomExamparper(examParperSerch);
    }

    /***
     * 根据试卷id删除试卷
     * @param id
     * @return
     */
    @DeleteMapping("deleteExamparper/{id}")
    public Result deleteExamparper(@PathVariable("id") String id){
   return examparperService.deleteExamparper(id);
    }

}
