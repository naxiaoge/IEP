package com.ysd.iep.dao;

import com.ysd.iep.entity.Examparper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gaozhongye
 * @date 2018/12/7
 * 考试试卷dao
 */

public interface ExamparperDao extends JpaRepository<Examparper, String> {
}