package com.youxiu326.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
//公共配置  可以在类上注释 注释本类的 缓存相关公共配置
//@CacheConfig(cacheNames = TestCacheService.CACHE_KEY)
public class TestCacheService {

    public static final String CACHE_KEY = "test-cache";

    /**
     * 获取信息  第二次访问会取缓存
     *
     * @author ming
     * @date 2018-07-11 17:41:47
     */
    @Cacheable(cacheNames = CACHE_KEY)
    public String testCache(String id) {
        return getString(id);
    }


    /**
     * 更新信息   更新缓存
     *
     * @author ming
     * @date 2018-07-12 09:50:53
     */
    @CachePut(cacheNames = CACHE_KEY)
    public String testCachePut(String id) {
        return getString(id + "update");
    }

    /**
     * 清除缓存
     *
     * @author ming
     * @date 2018-07-12 09:51:22
     */
    @CacheEvict(cacheNames = CACHE_KEY)
    public void removeCache(String id) {
        System.out.println("删除缓存 ");
    }


    /**
     * 获取string 模拟调用方法
     *
     * @author ming
     * @date 2018-07-11 17:41:58
     */
    private String getString(String id) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id + "这是获得的缓存";
    }

}