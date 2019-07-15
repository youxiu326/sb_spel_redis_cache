package com.youxiu326.service.impl;

import com.youxiu326.service.CacheTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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


    /**
     * <br>这里我们使用了<span color='red'>@Cacheable</span> 注解，注解中的cacheNames指定了这里读取的是哪个Cache。
     * <br>这里会在cacheName="test"的cache中去查找key是id的缓存对象
     */
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

    /**
     * <br>这里我们使用了<span color='red'>@CacheEvict</span> 注解，
     * <br>cacheNames指定了删除哪个cache中的数据，默认会使用方法的参数作为删除的key
     */
    @Override
    @CacheEvict(cacheNames = "test")
    public String delete(String id) {
        return enties.remove(id);
    }

    /**
     * <br>这里用到了<span color='red'>@CachePut</span> 注解，
     * <br>这个注解的作用是将方法的返回值按照给定的key,写入到cacheNames指定的cache中去
     */
    @Override
    @CachePut(cacheNames = "test", key = "#id")
    public String save(String id, String value) {
        logger.info("save value " + value + " with key " + id);
        enties.put(id, value);
        return value;
    }


    /**
     *  注意:
     * <br> @EnableCaching 启用缓存配置
     * <br> @Cacheable 指定某个方法的返回值是可以缓存的。在注解属性中指定缓存规则。
     * <br> @Cacheput 将方法的返回值缓存到指定的key中
     * <br> @CacheEvict 删除指定的缓存数据
     * <br> 注意
     * <br> @Cacheable和@Cacheput都会将方法的执行结果按指定的key放到缓存中，
     * <br> @Cacheable在执行时，会先检测缓存中是否有数据存在，如果有，直接从缓存中读取。如果没有，执行方法，将返回值放入缓存，
     * <br> 而@Cacheput会先执行方法，然后再将执行结果写入缓存。使用@Cacheput的方法一定会执行
     */
    @Override
    @CachePut(cacheNames = "test", key = "#id")
    public String update(String id, String value) {
        return enties.put(id, value);
    }
}