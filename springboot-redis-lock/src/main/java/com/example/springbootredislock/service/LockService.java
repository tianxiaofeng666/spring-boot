package com.example.springbootredislock.service;

import com.example.springbootredislock.pojo.CommodityStore;
import com.example.springbootredislock.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LockService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /*public void count(){
        String requestId = UUID.randomUUID().toString();
        String lockKey = "commodity";
        if(RedisUtil.tryGetDistributedLock(redisTemplate,lockKey,requestId,10L)){
            //获取到锁，执行减库存操作
            try{
                Query query =new Query(Criteria.where("commodityId").is(1));
                CommodityStore commodity = mongoTemplate.findOne(query, CommodityStore.class);
                int num = commodity.getNum();
                if(num > 0){
                    Update update = new Update();
                    update.set("num",num - 1);
                    mongoTemplate.updateFirst(query,update,CommodityStore.class);
                    System.out.println("线程：" + Thread.currentThread().getName() + "减库存成功了。。。");
                }
            }catch (Exception e){
                throw e;
            }finally {
                RedisUtil.releaseDistributedLock(redisTemplate,lockKey,requestId);
            }

        }


    }*/

    public void count(){
        Query query =new Query(Criteria.where("commodityId").is(1));
        CommodityStore commodity = mongoTemplate.findOne(query, CommodityStore.class);
        int num = commodity.getNum();
        if(num > 0){
            Update update = new Update();
            update.set("num",num - 1);
            mongoTemplate.updateFirst(query,update,CommodityStore.class);
            System.out.println("线程：" + Thread.currentThread().getName() + "减库存成功了。。。");
        }
    }
}
