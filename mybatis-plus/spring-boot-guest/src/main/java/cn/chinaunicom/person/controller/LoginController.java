package cn.chinaunicom.person.controller;

import cn.chinaunicom.person.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }
}
