package com.thankcode.web.controller;

import com.thankcode.blog.api.dto.BlogDTO;
import com.thankcode.common.entity.AnswerDTO;
import com.thankcode.common.entity.PageDTO;
import com.thankcode.web.annotation.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: suncl
 * @date: 2019-12-22 19:44:10
 * @version: V1.0
 */
@Controller
public class WebController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${zuul-url}")
    private String zuulUrl;
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

    /**
     * 填写博客
     * @param blogDTO 博客dto，由前台页面封装成对象
     * @param headers 带有token的header
     * @return
     */
    @RequestMapping(value = "/blog/post",method = RequestMethod.POST)
    @ResponseBody
    public AnswerDTO postBlog(@Valid @RequestBody BlogDTO blogDTO,
                              @Token HttpHeaders headers ){

        String url = zuulUrl+"/blog-server/blog/write";

        HttpEntity<BlogDTO> entity = new HttpEntity<BlogDTO>(blogDTO,headers);

        AnswerDTO answerDTO = restTemplate.postForObject(url, entity, AnswerDTO.class);
        return answerDTO;
    }

    /**
     * 发布博客页面
     * @return
     */
    @RequestMapping(value = "/blog/toPost")
    public String postBlogPage(){
        return "blog_post";
    }

    /**
     * blog 分页列表
     * @param page
     * @param size
     * @param headers
     * @return
     */
    @RequestMapping(value = "/blog/list")
    @ResponseBody
    public AnswerDTO<PageDTO<BlogDTO>> findAll(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="10") Integer size,
            @RequestParam(value = "title_or_content",required = false,defaultValue = "") String titleOrContent,
            @Token HttpHeaders headers){
        String url = zuulUrl+"/blog-server/blog/list?page="+page+"&size="+size+"&title_or_content="+titleOrContent;
        logger.info("webController page:{},size:{},title_or_content:{}",page,size,titleOrContent);
        HttpEntity entity = new HttpEntity(null,headers);
        ResponseEntity<AnswerDTO> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, AnswerDTO.class);
        AnswerDTO body = exchange.getBody();
        return body;
    }

    /**
     * 查询一个blog
     * @param id blog的id
     * @return
     */
    @RequestMapping(value = "/blog/show")
    public String show(Long id, Model model, @Token HttpHeaders headers){
        logger.info("web中发送的博客id:{}",id);
        String url = zuulUrl+"/blog-server/blog/show?id="+id;
        HttpEntity entity = new HttpEntity(null,headers);
        ResponseEntity<AnswerDTO<BlogDTO>> answerDTO = restTemplate.exchange(url, HttpMethod.GET,entity,
                new ParameterizedTypeReference<AnswerDTO<BlogDTO>>(){});
        AnswerDTO<BlogDTO> body = answerDTO.getBody();
        BlogDTO blogDTO =  body.getContent();
        model.addAttribute("blog", blogDTO);
        return "blog_show";
    }

    /**
     * 删除一个博客
     * @param id
     * @param headers
     * @return
     */
    @GetMapping(value = "/blog/del")
    @ResponseBody
    public AnswerDTO deleteBlog(Long id,@Token HttpHeaders headers){
        logger.info("删除博客id:{}",id);
        String url = zuulUrl+"/blog-server/blog/del?id="+id;
        HttpEntity entity = new HttpEntity(null,headers);
        ResponseEntity<AnswerDTO> answerDTO = restTemplate.exchange(url, HttpMethod.GET,entity,AnswerDTO.class);
        AnswerDTO body = answerDTO.getBody();
        return body;
    }
    @GetMapping(value = "/blog/edit")
    public String editBlog (Long id,Model model, @Token HttpHeaders headers){
        logger.info("编辑的博客的id:{}",id);
        String url = zuulUrl+"/blog-server/blog/show?id="+id;
        HttpEntity entity = new HttpEntity(null,headers);
        ResponseEntity<AnswerDTO<BlogDTO>> answerDTO = restTemplate.exchange(url, HttpMethod.GET,entity,
                new ParameterizedTypeReference<AnswerDTO<BlogDTO>>(){});
        AnswerDTO<BlogDTO> body = answerDTO.getBody();
        BlogDTO blogDTO =  body.getContent();
        model.addAttribute("blog", blogDTO);
        return "blog_edit";
    }
}
