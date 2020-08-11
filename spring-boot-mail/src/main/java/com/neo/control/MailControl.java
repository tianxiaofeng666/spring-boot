package com.neo.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class MailControl {
    @Autowired
    private MailService mailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public String sendMail(){
        mailService.sendSimpleMail("tianxiaofeng@iyunbao.com","4月3号","云服产品上线");
        return "发送成功";
    }

    @RequestMapping(value = "/sendMailToMore",method = RequestMethod.POST)
    public JSONObject sendMailToMore(@RequestBody JSONObject json){
        JSONObject res = new JSONObject();
        String subject = json.getString("subject");
        String content = json.getString("content");
        JSONArray adds = json.getJSONArray("adds");
        List<String> addList = JSONObject.parseArray(JSONObject.toJSONString(adds), String.class);
        String result = mailService.sendSimpleMailToMore(addList,subject,content);
        res.put("result","success");
        res.put("content",result);
        return res;
    }

    /**
     * 发送带邮件模板的html邮件
     * @param json
     * @return
     */
    @RequestMapping(value = "/sendHtmlMail",method = RequestMethod.POST)
    public JSONObject sendHtmlMail(@RequestBody JSONObject json){
        JSONObject res = new JSONObject();
        String to = json.getString("to");
        String subject = json.getString("subject");
        Context context = new Context();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id","006");
        map.put("userName","晓峰");
        context.setVariables(map);
        //context.setVariable("id","006");
        String content = templateEngine.process("emailTemplate",context);
        String result = mailService.sendHtmlMail(to,subject,content);
        res.put("result","success");
        res.put("content",result);
        return res;
    }

    @RequestMapping("/testTask")
    public String testTask(@RequestBody JSONObject json) throws ExecutionException, InterruptedException {
        ExecutorService cacheExecutor = Executors.newCachedThreadPool();
        //List<Future> list = new ArrayList<Future>();
        cacheExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mailService.sendSimpleMail("tianxiaofeng@iyunbao.com","4月23号","云服产品上线");
            }
        });
        Future<String> result = cacheExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String to = "1248083709@qq.com";
                String subject = "带返回值的线程";
                Context context = new Context();
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id","006");
                map.put("userName","晓峰");
                context.setVariables(map);
                String content = templateEngine.process("emailTemplate",context);
                String result = mailService.sendHtmlMail(to,subject,content);
                return result;
            }
        });
        if(result.get().equals("0")){
            return "邮件发送成功！！！";
        }else{
            return "发送失败了。。。";
        }

    }

    /**
     * 自定义线程池
     * @param json
     * @return
     */
    @RequestMapping("/testTask11")
    public String testTask11(@RequestBody JSONObject json){
        ThreadPoolExecutor tpc = new ThreadPoolExecutor(8,8,60L,TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        return "hello world!";
    }

}
