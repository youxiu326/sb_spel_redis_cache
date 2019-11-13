package com.youxiu326.service.impl;

import com.youxiu326.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    //@Async("asyncServiceExecutor")
    @Async
    @Override
    public void executeAsync() {

        Object o = redisTemplate.opsForList().rightPop("async_list");
        logger.info(o+"");

    }
}