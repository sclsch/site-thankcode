package com.thankcode.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.thankcode.blog.api.dto.BlogDTO;
import com.thankcode.common.entity.AnswerDTO;
import com.thankcode.common.entity.PageDTO;
import com.thankcode.web.annotation.Token;
import com.thankcode.web.utils.NameComparator;
import com.thankcode.web.utils.SizeComparator;
import com.thankcode.web.utils.TypeComparator;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @RequestMapping(value = "/blog/add")
    public String postBlogPage(){
        return "blog_add";
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
        ResponseEntity<AnswerDTO<PageDTO<BlogDTO>>> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<AnswerDTO<PageDTO<BlogDTO>>>(){});
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
        logger.info("webController 显示博客id:{}",id);
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
    /**
     * 上传目录
     */
    @Value("${uploadPrefix}")
    private String uploadPrefix;

    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }

    @RequestMapping("/uploadJson")
    public void uploadJson(HttpServletRequest request, HttpServletResponse response, String dir) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        //文件保存目录路径
        String savePath = uploadPrefix + "/attached/";

        //文件保存目录URL
        String saveUrl = uploadPrefix + "/attached";

        System.out.println(savePath);
        System.out.println(saveUrl);

        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");

        //最大文件大小
        long maxSize = 1000000;

        response.setContentType("text/html; charset=UTF-8");

        if (!ServletFileUpload.isMultipartContent(request)) {
            out.println(getError("请选择文件。"));
            return;
        }
        //检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            out.println(getError("上传目录不存在。"));
            return;
        }
        //检查目录写权限
        if (!uploadDir.canWrite()) {
            out.println(getError("上传目录没有写权限。"));
            return;
        }

        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if (!extMap.containsKey(dirName)) {
            out.println(getError("目录名不正确。"));
            return;
        }
        //创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        //此处是直接采用Spring的上传
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            String fileFullname = mf.getOriginalFilename();
            fileFullname = fileFullname.replace('&', 'a');
            fileFullname = fileFullname.replace(',', 'b');
            fileFullname = fileFullname.replace('，', 'c');
            //检查扩展名
            String fileExt = fileFullname.substring(fileFullname.lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
                return;
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

            File uploadFile = null;
            if (extMap.get("file").contains(fileExt)) {
                uploadFile = new File(savePath + fileFullname);
            } else {
                uploadFile = new File(savePath + newFileName);
            }
            try {
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
                JSONObject obj = new JSONObject();
                obj.put("error", 0);
                obj.put("url", request.getContextPath() + "/attached/" + dirName + "/" + ymd + "/" + newFileName);
                out.println(obj.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
                out.println(getError("上传文件失败。"));
                return;
            }
        }
    }
    @RequestMapping("/fileManagerJson")
    public void fileManagerJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        //根目录路径，可以指定绝对路径，比如 /var/www/attached/
        String rootPath = uploadPrefix + "/attached/";
        //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = request.getContextPath() + "/attached/";
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if (!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)) {
                out.println("Invalid Directory name.");
                return;
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        //排序形式，name or size or type
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        //目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            out.println("Directory does not exist.");
            return;
        }

        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        out.println(result.toJSONString());
    }

}
