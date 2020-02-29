package com.thankcode.blog.controller;

import com.thankcode.blog.api.dto.BlogDTO;
import com.thankcode.blog.dao.BlogRepository;
import com.thankcode.blog.domain.Blog;
import com.thankcode.common.entity.AnswerDTO;
import com.thankcode.common.entity.PageDTO;
import com.thankcode.common.enums.CommonStateEnum;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author: suncl
 * @date: 2019-12-15 06:43:32
 * @version: V1.0
 */
@RestController
public class BlogController {
    @Resource
    private BlogRepository blogRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/blog/list11")
    public String home(){
        List<Blog> all = blogRepository.findAll();
        String s  = "";
        for (Blog b:all){
            s+= b.getTitle();
        }
        return "blogs:"+s;
    }

    /**
     * 发布博客,或者修改
     * @param blogDTO
     * @return
     */
    @RequestMapping(value = "/blog/write")
    public AnswerDTO write(@RequestBody BlogDTO blogDTO){
         logger.info("填写博客，title:{},content:{}",blogDTO.getTitle(),blogDTO.getContent());
         Blog b = new Blog();
         BeanUtils.copyProperties(blogDTO,b);
         b.setUpdateTime(new Date());
         blogRepository.save(b);
         return new AnswerDTO("write blog ok",null, CommonStateEnum.OK);
    }

    /**
     * 查询所有博客
     * @param page 当前页
     * @param size 每页几条
     * @return
     */
    @GetMapping(value = "/blog/list")
    public AnswerDTO<PageDTO<BlogDTO>> findAll(
            @RequestParam(value="page", defaultValue="0") Integer page
            , @RequestParam(value="size", defaultValue="10") Integer size,
            @RequestParam(value = "title_or_content",required = false,defaultValue = "") String titleOrContent
    ){
        logger.info("BlogController page:{}，size:{},title_or_content:{} ",page,size,titleOrContent);
        Sort sort = new Sort(Sort.Direction.DESC,"update_time");
        PageRequest pageable = new PageRequest(page,size,sort);

        Page<Blog> all = blogRepository.findAllByTitle(titleOrContent, pageable);
        List<Blog> content = all.getContent();
        List<BlogDTO> blogDTOList = new ArrayList<>();
        for(Blog b : content){
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(b,blogDTO);
            blogDTOList.add(blogDTO);
        }
        PageDTO<BlogDTO> pagedBlog = new PageDTO<BlogDTO>(blogDTOList,page,size,all.getTotalPages(),all.getTotalElements());
        return new AnswerDTO<PageDTO<BlogDTO>>("blog's list",pagedBlog, CommonStateEnum.OK);
    }

    /**
     * 查询一个blog
     * @param id blog的id
     * @return
     */
    @RequestMapping(value = "/blog/show",method = RequestMethod.GET)
    public AnswerDTO<BlogDTO> show(Long id){
        logger.info("显示一个博客的id:{}",id);
        Blog one = blogRepository.findOne(id);
        BlogDTO blogDTO = new BlogDTO();
        BeanUtils.copyProperties(one,blogDTO);
        AnswerDTO<BlogDTO> ans = new AnswerDTO<>("select one blog", blogDTO, CommonStateEnum.OK);
        return ans;
    }
    /**
     * 删除blog
     * @param id blog的id
     * @return
     */
    @RequestMapping(value = "/blog/del",method = RequestMethod.GET)
    public AnswerDTO<BlogDTO> deleteBlog(Long id){
        logger.info("删除一个博客的id:{}",id);
        blogRepository.delete(id);
        return new AnswerDTO("delete a blog",null,CommonStateEnum.OK);
    }
}
