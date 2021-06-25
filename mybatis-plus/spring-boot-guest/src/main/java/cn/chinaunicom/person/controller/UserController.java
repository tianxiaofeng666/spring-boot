package cn.chinaunicom.person.controller;

import cn.chinaunicom.person.entity.User;
import cn.chinaunicom.person.service.UserService;
import cn.chinaunicom.person.utils.Common;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/index123")
    public String index123() {
        return "index123";
    }

    @RequestMapping("/addUser")
    public String addUser() {
        return "addUser";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JSONObject save(@RequestBody User user){
        JSONObject obj = new JSONObject();
        userService.save(user);
        obj.put("result","success");
        obj.put("content",0);
        return obj;
    }

    @RequestMapping("/saveUser")
    public String saveUser(MultipartFile file,User user, Model model){
        String fileName = file.getOriginalFilename();
        Common.logger.info("文件名：" + fileName);
        userService.save(user);
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return "list";
    }

    @RequestMapping("/userList")
    public String userList(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("userList", userList);
        return "list";
    }

    public static void main(String[] args) {
        String url = "https://jyhcu.com:18088/video";
        String aa = "%s/fastgate/person";
        String dd = "/fastgate/person";
        String bb = String.format(aa,url);
        System.out.println("url：" + bb);
        String cc = String.format("%s%s","1",String.valueOf(1234));
        System.out.println(cc);
        System.out.println(url.concat(dd));
    }

}
