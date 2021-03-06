package com.ysd.iep.serviceImpl;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ysd.iep.dao.CoursePostRepository;
import com.ysd.iep.dao.CourseReplyRepository;
import com.ysd.iep.dao.CourseTypeRepository;
import com.ysd.iep.entity.CoursePost;
import com.ysd.iep.entity.CoursePostQuery;
import com.ysd.iep.entity.CourseReply;
import com.ysd.iep.entity.Post;
import com.ysd.iep.entity.Reply;
import com.ysd.iep.service.CoursePostService;

@Service
public class CoursePostServiceImpl implements CoursePostService {
	@Autowired
	private CoursePostRepository postRepository;
	@Autowired
	private CourseReplyRepository replyRepository;
	@Autowired
	private CourseTypeRepository typeRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<CoursePost> queryAllCoursePost(CoursePostQuery postQuery, Pageable pageable) {
		StringBuilder dataSql = new StringBuilder(
				"SELECT * FROM courseposttb p JOIN coursereplytb r ON p.post_id=r.post_id");
		StringBuilder countSql = new StringBuilder(
				"SELECT COUNT(1) FROM courseposttb p JOIN coursereplytb r ON p.post_id=r.post_id");
		// 拼接where条件
		StringBuilder whereSql = new StringBuilder(" WHERE r.reply_parentid = 0 ");

		if (StringUtils.isNotEmpty(postQuery.getTypeName())) {
			whereSql.append("AND p.post_id IN (SELECT post_id FROM courseposttypetb pt WHERE type_id="
					+ "(SELECT type_id FROM coursetypetb WHERE type_name='" + postQuery.getTypeName() + "'))");
		}
		if (StringUtils.isNotEmpty(postQuery.getUserId())) {
			whereSql.append("AND r.user_id='" + postQuery.getUserId() + "'");
		}
		if (postQuery.getCourseId() != null) {
			whereSql.append("AND r.course_id=" + postQuery.getCourseId() + " ");
		}

		// 拼接orderBy条件
		StringBuilder orderBySql = new StringBuilder("ORDER BY p.post_isstick=1 desc");
		if ("replyTime".equals(postQuery.getOrderBy())) {
			orderBySql.append(" , r.reply_time desc");
		}
		if ("replyLikenum".equals(postQuery.getOrderBy())) {
			orderBySql.append(" , r.reply_likenum desc");
		}
		if ("replyReportnum".equals(postQuery.getOrderBy())) {
			orderBySql.append(" , r.reply_reportnum desc");
		}
		if("replyNum".equals(postQuery.getOrderBy())) {
			orderBySql.append(" , p.reply_num desc");
		}

		// 组装sql语句
		dataSql.append(whereSql).append(orderBySql);
		countSql.append(whereSql);

		Query dataQuery = entityManager.createNativeQuery(dataSql.toString(), CoursePost.class);
		Query countQuery = entityManager.createNativeQuery(countSql.toString());

		// 设置分页
		dataQuery.setFirstResult((int) pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());
		BigInteger count = (BigInteger) countQuery.getSingleResult();
		Long total = count.longValue();
		List<CoursePost> content2 = total > pageable.getOffset() ? dataQuery.getResultList()
				: Collections.<CoursePost>emptyList();
		return new PageImpl<>(content2, pageable, total);
	}

	/**
	 * 获取帖子详情
	 */
	@Override
	public CourseReply getPostDetails(Integer postId, Integer parentId) {
		return replyRepository.getPostDetails(postId, parentId);
	}

	/**
	 * 获取点赞数
	 */
	@Override
	public Integer getLikeNum(Integer replyId) {
		return replyRepository.getLikeNum(replyId);
	}
	
	/**
	 * 根据回复id更新点赞数
	 */
	@Override
	public Integer updateLikeNum(Integer replyId, Integer likeNum) {
		return replyRepository.updateLikeNum(replyId, likeNum);
	}

	/**
	 * 发表帖子
	 */
	@Override
	public Integer publicPost(Integer courseId,String title, String content, Integer parentId, String userId, Integer typeId) {
		CoursePost post = new CoursePost();
		post.setPostTitle(title);
		CoursePost post2 = postRepository.save(post);
		int n = typeRepository.insertPosttype(post2.getPostId(), typeId);
		int m = replyRepository.insertPortDetails(courseId, userId, post2.getPostId(), content, parentId);
		if (m > 0 && n > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<CoursePost> getHotPost(List<Integer> courseIds) {
		return postRepository.getHotPost(courseIds);
	}

	@Override
	public CoursePost getPostByPostId(Integer postId) {
		return postRepository.findByPostId(postId);
	}

	@Override
	public Integer getReplyNum(Integer postId) {
		return replyRepository.getReplyNum(postId);
	}

	@Override
	public Integer updateReplyNum(Integer replynum, Integer postId) {
		return postRepository.updateReplyNum(replynum,postId);
		
	}

}
