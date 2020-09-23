package com.sox.controller;

import com.sox.pojo.Blog;
import com.sox.pojo.Type;
import com.sox.pojo.User;
import com.sox.service.BlogServiceImpl;
import com.sox.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
public class EditorController {

    @Autowired
    private BlogServiceImpl blogService;

    @RequestMapping("/edit")
    public String edit(Model model){
        List<Type> types = blogService.queryAllTypes();
        model.addAttribute("types",types);
        return "edit";
    }

    @RequestMapping("/submitBlog")
    @ResponseBody
    public String submitBlog(HttpSession session,String title,String introduction,
                             String content,@RequestParam("types[]")int[] types) throws IOException {
        if(session.getAttribute("user") == null){
            return "EXPIRE"; // session expired
        }
        String blogID = UUID.randomUUID().toString();//create a blog id
        String realPath = Constant.postPath_win +blogID+title.hashCode();
        File file = new File(realPath);
        if(!file.exists()) file.createNewFile();

        //write content to file
        PrintWriter pw = new PrintWriter(file);
        pw.println(content);
        pw.close();

        //insert blog
        Blog blog = new Blog(blogID,((User)session.getAttribute("user")).getUsername(),
                new Date(),realPath,0,0,title,introduction);

        if(types.length != 0){
            for (int type : types) { // insert types
                blogService.insertBlogTypeAssoc(blogID,type);
            }
        }else{
            blogService.insertBlogTypeAssoc(blogID,11); // default insert other
        }

        int result = blogService.insertBlog(blog);
        if(result == 1) return blogID;
        else return "FAIL";
    }
}
