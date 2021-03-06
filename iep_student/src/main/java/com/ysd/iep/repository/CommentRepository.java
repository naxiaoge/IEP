package com.ysd.iep.repository;

import com.ysd.iep.entity.CommentDTO;
import com.ysd.iep.entity.StudentComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 评价接口
 *
 * @author ASUS
 */
public interface CommentRepository extends JpaRepository<StudentComment, Integer>, JpaSpecificationExecutor<StudentComment> {
    @Query(value = "select new com.ysd.iep.entity.CommentDTO(c.cid,count(c.mid)) from StudentComment c group by cid")
    Page<CommentDTO> queryCommentPagingOrder(Pageable pageable);

    Page<StudentComment> findByCid(Integer cid, Pageable pageable);

    Page<StudentComment> findBySid(String sid, Pageable pageable);

    @Query(value = "update commenttb set praise=:praise where mid=:mid",nativeQuery = true)
    @Modifying
    @Transactional
    public int updatePraise(@Param("mid") Integer mid, @Param("praise")Integer praise);

    /**
     * 删除操作
     * 提供的接口根据课程id查询评价表是否有评价记录  返回true 或 false
     */
    public List<StudentComment> findByCid(Integer cid);


}
