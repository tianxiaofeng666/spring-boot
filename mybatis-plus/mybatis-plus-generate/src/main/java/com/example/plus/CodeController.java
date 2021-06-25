package com.example.plus;

import com.alibaba.fastjson.JSONObject;
import com.example.plus.utils.CodeGenerateUtil;
import com.example.plus.utils.DataBaseUtil;
import com.example.plus.utils.ZipGenerateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CodeController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/codegen/download")
    @ResponseBody
    public void download(@RequestBody JSONObject json){
        String module = json.getString("module");
        String url = json.getString("url");
        String username = json.getString("username");
        String password = json.getString("password");
        String tablename = json.getString("tablename");
        CodeGenerateUtil.codeGenerate(module,url,username,password,tablename);
    }

    @RequestMapping("/test")
    public void test(@RequestParam(value = "module") String module, HttpServletRequest request, HttpServletResponse response){
        String sourcePath = System.getProperty("user.dir") + "/src/main/java";
        String downloadZipFileName = module + "_code_" + System.currentTimeMillis() + ".zip";
        //打包
        ZipGenerateUtils.createZip(request,response,sourcePath,downloadZipFileName);
        //打完包之后，删除创建的module
        String folderPath1 = System.getProperty("user.dir") + "/src/main/java/com/example/plus/" + module;
        String folderPath2 = System.getProperty("user.dir") + "/src/main/resources/mapper";
        ZipGenerateUtils.delFolder(folderPath1);
        ZipGenerateUtils.delFolder(folderPath2);
    }

    @RequestMapping("/tableList")
    public String userList(@RequestBody JSONObject json,Model model) {
        String url = json.getString("url");
        String username = json.getString("username");
        String password = json.getString("password");
        List<JSONObject> tableList = DataBaseUtil.getTableList(url, username, password);
        model.addAttribute("tableList", tableList);
        return "index";
    }

    @RequestMapping("/test123")
    @ResponseBody
    public String test123(@RequestBody JSONObject json){
        String sourcePath = System.getProperty("user.dir") + "/src/main/java";
        return sourcePath;
    }

}
