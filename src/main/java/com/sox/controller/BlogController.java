package com.sox.controller;

import com.sox.pojo.Blog;
import com.sox.pojo.Comment;
import com.sox.pojo.User;
import com.sox.service.BlogServiceImpl;
import com.sox.service.UserServiceImpl;
import com.sox.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/readBlog/{id}")
    public String readBlog(@PathVariable("id") String id, HttpSession session,Model model) {
        blogService.updateBlogView(id);
        Blog blog = blogService.selectBlogByBlogId(id);
        User author = userService.getAccountByUsername(blog.getAuthorID());

        author.setPassword(null); // make password null
        author.setProfilePicture(FileHandler.readFile(author.getProfilePicture()));
        blog.setContent(FileHandler.readFile(blog.getContent()));

        List<String> typeList = blogService.queryTypesByBlogID(blog.getBlogID());

        model.addAttribute("blog",blog);
        model.addAttribute("author",author);
        model.addAttribute("typeList",typeList);

        int result = 0;
        boolean isMe = false;
        if(session.getAttribute("user") != null){
            User user = (User)session.getAttribute("user");
            Map<String,String> map = new HashMap<>();
            map.put("subFromID",user.getUsername());
            map.put("subToID",author.getUsername());
            result = userService.selectSubToID(map);
            isMe = user.getUsername().equals(author.getUsername());
        }

        model.addAttribute("isSubbed",result == 1);
        model.addAttribute("isMe",isMe);
        List<Comment> commentList = blogService.selectCommentByBlogID(id);
        model.addAttribute("commentList",commentList);
        return "view";
    }

    @RequestMapping("/insertComment/{blogID}/{commentContent}")
    public String insertComment(@PathVariable("blogID") String blogID,
                                Model model, HttpSession session,
                                @PathVariable("commentContent") String commentContent)
                                throws UnsupportedEncodingException {
        commentContent = URLDecoder.decode(commentContent, "UTF-8");
        Comment comment = new Comment(blogID,((User)session.getAttribute("user")).
                getUsername(),commentContent,new Date());
        blogService.insertComment(comment);
        List<Comment> commentList = blogService.selectCommentByBlogID(blogID);

        model.addAttribute("commentList",commentList);
        return "view::comments";
    }
}
