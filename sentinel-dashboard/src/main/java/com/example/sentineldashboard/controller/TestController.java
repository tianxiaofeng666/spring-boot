package com.example.sentineldashboard.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThreadPoolExecutor testThreadPool;

    @RequestMapping("/test")
    @SentinelResource(value = "abc", fallback = "doFallback")
    public String test(){
        return "进来了。。。";
    }

    public String doFallback(Throwable t){
        return "你的操作太频繁，请稍后重试！";
    }

    @RequestMapping("/testAbc")
    public String testAbc(){
        String resourceName = "abc";
        Entry entry = null;
        try {
            entry = SphU.entry(resourceName);

            return "进来了";
        } catch (BlockException e) {
            return "被限流了。。";
        }finally {
            if(entry != null){
                entry.close();
            }
        }
    }

    @RequestMapping("/testAll")
    public void testAll() throws ExecutionException, InterruptedException {
        //自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        String url = "http://localhost:8081/test";
        for(int i=0; i<10; i++){
            Future<String> res = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String result = restTemplate.postForObject(url,null,String.class);
                    return result;
                }
            });
            System.out.println(res.get());
        }
    }

    @RequestMapping("/testAll2")
    public void testAll2() throws ExecutionException, InterruptedException {
        String url = "http://localhost:8081/test";
        for(int i=0; i<10; i++){
            Future<String> res = testThreadPool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String result = restTemplate.postForObject(url,null,String.class);
                    return result;
                }
            });
            System.out.println(res.get());
        }
    }

}
