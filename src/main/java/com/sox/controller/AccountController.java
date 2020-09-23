package com.sox.controller;

import com.sox.pojo.User;
import com.sox.service.UserServiceImpl;
import com.sox.util.Constant;
import com.sox.util.FileHandler;
import com.sox.util.Hasher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private UserServiceImpl userService;

    //Back to root
    @RequestMapping("/toHome")
    public String toHome(){
        return "redirect:/";
    }

    @RequestMapping("/toLogin")
    //redirect to login page
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    //check account and password
    public String login(String username, String password, Model model, HttpSession session){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,Hasher.encrypt(password));
        try {
            subject.login(token);//Login
            subject.getSession().setTimeout(-1000);
        }catch(UnknownAccountException | IncorrectCredentialsException accountException){
            model.addAttribute("msg","username or password invalid");
            model.addAttribute("username",username);
            return "login";
        }

        User user = userService.getAccountByUsername(username);
        user.setProfilePicture(FileHandler.readFile(user.getProfilePicture()));
        user.setPassword(null);
        session.setAttribute("user",user);
        return toHome();
    }



    @GetMapping("/toSignup")
    //redirect to sign up page
    public String toSignUp(){
        return "signup";
    }

    @PostMapping("/signup")
    //insert new account
    public String signUp(String username, String password, Model model){
         User newUser = new User(username,Hasher.encrypt(password),username,null,Constant.profilePicPath_win);
         int result = userService.insertAccount(newUser);
         if(result == 1){
             model.addAttribute("msg","You have successfully signed up, ");
             model.addAttribute("show",true);
         }else{
             model.addAttribute("msg","An unexpected error occurred!");
             model.addAttribute("show",false);
         }
         return "success";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    @PostMapping("/exist")
    @ResponseBody
    public int checkExist(String username){
        User user = userService.getAccountByUsername(username);
        if(user != null) return Constant.FAIL;
        else return Constant.SUCCESS;
    }

    @RequestMapping(value = "/saveUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public int saveUserInfo(HttpSession session,String name,String email){
         User user = (User)session.getAttribute("user");
         user.setName(name);
         user.setEmail(email);
         int result = userService.updateAccount(user);
         if(result == 1) return Constant.SUCCESS;
         else return Constant.FAIL;
    }

    @RequestMapping(value = "/checkPassword",method = RequestMethod.POST)
    @ResponseBody
    public int checkPassword(String password,HttpSession session){
        User user = userService.
                getAccountByUsername(((User)session.getAttribute("user")).getUsername());
        if(user.getPassword().equals(Hasher.encrypt(password))){
            return Constant.SUCCESS;
        }else{
            return Constant.FAIL;
        }
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public int changePassword(String password,HttpSession session){
        Map<String,String> map = new HashMap<>();
        map.put("username",((User)session.getAttribute("user")).getUsername());
        map.put("password",Hasher.encrypt(password));
        int result = userService.updatePassword(map);
        if(result == 1) return Constant.SUCCESS;
        else return Constant.FAIL;
    }

    @RequestMapping("/subscribe/{subbed}")
    @ResponseBody
    public int subscribeControl(String subToID,@PathVariable("subbed") boolean subbed,HttpSession session){
        if(session.getAttribute("user") == null){
            return Constant.NO_LOGIN;
        }
        Map<String,String> map = new HashMap<>();
        map.put("subFromID",((User)session.getAttribute("user")).getUsername());
        map.put("subToID",subToID);
        if(subbed){
            return userService.deleteSubscriptionPair(map);
        }else{
            return userService.insertSubscriptionPair(map);
        }
    }

    @RequestMapping("/toAbout")
    public String toAbout(){
        return "about";
    }
}
