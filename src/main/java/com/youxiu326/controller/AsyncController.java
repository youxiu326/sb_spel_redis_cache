package com.youxiu326.controller;


import com.youxiu326.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@RestController
public class AsyncController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/asyn")
    public String submit(){
        Long srartTime = System.currentTimeMillis();
        logger.info("start submit");

        redisTemplate.delete("async_list");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(i);
        }
        redisTemplate.opsForList().leftPushAll("async_list",list);

        for (int i = 0; i < 5000; i++) {
            //调用service层的任务
            asyncService.executeAsync();
        }

        while (true){
            Long size = redisTemplate.opsForList().size("async_list");
            if (size <= 0L){
                logger.info("end submit {}",(System.currentTimeMillis()-srartTime));
                break;
            }
        }

        return "success";
    }

} 