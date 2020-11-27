package com.example.sentinelflow.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Slf4j
public class FlowController extends BaseController{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("testThreadPool")
    private ThreadPoolExecutor testThreadPool;

    @RequestMapping(value = "/flow", method = RequestMethod.POST)
    @SentinelResource(value = "testFlow", blockHandler = "exceptionHandler")
    public String flow(@RequestBody JSONObject json){
        String ip = request.getRemoteAddr();
        log.info("入参:{},请求ip:{}",json.getString("id"),ip);
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "进来啦！！！";
    }

    /**
     * 方法的参数列表应与原始方法匹配，最后一个附加参数类型为BlockException。返回类型应与原始方法相同,
     * 返回类型应与原始方法相同
     * @param e
     * @return
     */
    public String exceptionHandler(JSONObject json,BlockException e){
        String msg = "";
        log.info("异常入参:{},请求ip:{}",json.getString("id"),request.getRemoteAddr());
        if(e instanceof FlowException){
            msg = "你的操作太频繁，请稍后重试！";
        }else if(e instanceof AuthorityException){
            msg = "无权访问该接口，请联系管理员！";
        }else if(e instanceof DegradeException){
            msg = "接口响应过慢，请稍后再试！";
        }
        return msg;
    }

    @RequestMapping("/testAll")
    public String testAll(@RequestBody JSONObject json) throws ExecutionException, InterruptedException {
        String ip = request.getRemoteAddr();
        String url = "http://localhost:8888/flow";
        for(int i=0; i<10; i++){
            Future<String> res = testThreadPool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String result = restTemplate.postForObject(url,json,String.class);
                    return result;
                }
            });
            System.out.println(res.get());
        }
        return ip;
    }

}
