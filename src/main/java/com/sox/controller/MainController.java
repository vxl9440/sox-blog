package com.sox.controller;

import com.sox.pojo.Blog;
import com.sox.pojo.BlogUserAssociation;
import com.sox.pojo.Type;
import com.sox.pojo.User;
import com.sox.service.BlogServiceImpl;
import com.sox.service.UserServiceImpl;
import com.sox.util.Constant;
import com.sox.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    BlogServiceImpl blogService;

    @Autowired
    UserServiceImpl userService;


    @RequestMapping("/")
    public String initHomePage(Model model){
        refreshHomePage(model,0,false,-1);
        model.addAttribute("currentPage",1);
        return "index";
    }

    @RequestMapping("/changePage/{pageNumber}")
    public String changePage(@PathVariable("pageNumber") int pageNumber,Model model){
        refreshHomePage(model,(pageNumber - 1) * Constant.MAXIMUM_POST_PER_PAGE,false,-1);
        model.addAttribute("currentPage",pageNumber);
        return "index::blogs";
    }

    @RequestMapping("/searchForType/{typeID}")
    public String searchForType(@PathVariable("typeID") int typeID,Model model){
        refreshHomePage(model,0,true,typeID);
        model.addAttribute("currentPage",1);
        return "index::blogs";
    }

    @RequestMapping("/searchForBlogs/{keywords}")
    public String searchForBlogs(@PathVariable("keywords") String keywords,Model model)
                                 throws UnsupportedEncodingException {
        keywords = URLDecoder.decode(keywords, "UTF-8");
        List<String> tokens = Arrays.asList(keywords.split(" "));
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",tokens);
        List<BlogUserAssociation> targetBlogs = blogService.selectBlogsByKeyword(map);

        for (BlogUserAssociation targetBlog : targetBlogs) {
            targetBlog.getUser().setProfilePicture(FileHandler.readFile(targetBlog.getUser().getProfilePicture()));
        }

        model.addAttribute("buList",targetBlogs);
        return "index::blogs";
    }

    @RequestMapping("/mySpace/{which}")
    public String goMySpace(@PathVariable("which") String which, HttpSession session,Model model){
        User currentUser = (User)session.getAttribute("user");
        Map<String,String> map = new HashMap<>();
        map.put("subFromID",currentUser.getUsername());
        List<User> subscriptions = userService.selectSubByID(map);
        map.remove("subFromID");
        map.put("subToID",currentUser.getUsername());
        List<User> followers = userService.selectSubByID(map);

        List<Blog> blogList = blogService.selectBlogByAuthorID(currentUser.getUsername());

        for (User user : subscriptions) {
            user.setProfilePicture(FileHandler.readFile(user.getProfilePicture()));
        }

        for (User user : followers) {
            user.setProfilePicture(FileHandler.readFile(user.getProfilePicture()));
        }

        model.addAttribute("subscriptions",subscriptions);
        model.addAttribute("followers",followers);
        model.addAttribute("blogList",blogList);
        model.addAttribute("active",which);
        return "space";
    }

    /*@RequestMapping("/space/{username}")
    public String goSpace(@PathVariable("username") String username,HttpSession session,Model model){
        List<Blog> blogList = blogService.selectBlogByAuthorID(username);

        *//*int result = 0;
        boolean isMe = false;
        if(session.getAttribute("user") != null){
            User user = (User)session.getAttribute("user");
            Map<String,String> map = new HashMap<>();
            map.put("subFromID",user.getUsername());
            map.put("subToID",author.getUsername());
            result = userService.selectSubToID(map);
            isMe = user.getUsername().equals(author.getUsername());
        }*//*

        model.addAttribute("blogList",blogList);
        return "space2";
    }*/


    private void refreshHomePage(Model model,int start,boolean withType,int typeID){
        Map<String,Integer> map = new HashMap<>();
        if(withType) map.put("typeID",typeID);
        List<Type> typeList = blogService.queryAllTypes();
        int count = blogService.selectAllBlogsCount(map);
        map.put("start",start);
        map.put("end", Constant.MAXIMUM_POST_PER_PAGE);
        List<BlogUserAssociation> buList = blogService.queryAllBlogs(map);
        for (BlogUserAssociation blogUserAssociation : buList) {
            Blog tmp = blogUserAssociation.getBlog();
            User tmp2 = blogUserAssociation.getUser();
            tmp.setContent(FileHandler.readFile(tmp.getContent()));
            tmp2.setProfilePicture(FileHandler.readFile(tmp2.getProfilePicture()));
        }
        List<Integer> countList = new ArrayList<>();
        int pages = (int) Math.ceil((double)count/Constant.MAXIMUM_POST_PER_PAGE);
        for(int i = 1;i <= pages;i++){
            countList.add(i);
        }
        model.addAttribute("typeList",typeList);
        model.addAttribute("buList",buList);
        model.addAttribute("countList",countList);
        model.addAttribute("withType",withType);
    }
}
