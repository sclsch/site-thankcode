package com.thankcode.blog.dao;

import com.thankcode.blog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author: suncl
 * @date: 2019-12-19 22:57:24
 * @version: V1.0
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor {
    @Query(value = "select * from blog  where ( title like CONCAT('%',?1,'%') or content like CONCAT('%',?1,'%')) ORDER BY ?#{#pageable}",
            countQuery = "select count(*) from blog where (title like CONCAT('%',?1,'%')   or content like CONCAT('%',?1,'%'))",
            nativeQuery = true)
    Page<Blog> findAllByTitle(String titleOrContent,
                                  Pageable pageable);
}
