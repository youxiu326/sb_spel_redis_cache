package com.youxiu326.service.impl;

import com.youxiu326.service.CacheTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SimpleCacheTestServiceImpl implements CacheTestService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCacheTestServiceImpl.class);

    private final Map<String, String> enties = new HashMap<>();

    public SimpleCacheTestServiceImpl() {
        enties.put("1", "this no 1");
    }

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = "test")
    public String get(String id) {
        // 记录数据产生的时间，用于测试对比
        long time = new Date().getTime();
        // 打印使用到的cacheManager
        logger.info("The cacheManager is" + cacheManager);
        // 当数据不是从cache里面获取时，打印日志
        logger.info("Get value by id=" + id + ", The time is " + time);
        return "Get value by id=" + id + ",the value is" + enties.get(id);
    }

    @Override
    public String delete(String id) {
        return enties.remove(id);
    }

    @Override
    public String save(String id, String value) {
        logger.info("save value " + value + " with key " + id);
        enties.put(id, value);
        return value;
    }

    @Override
    public String update(String id, String value) {
        return enties.put(id, value);
    }
}