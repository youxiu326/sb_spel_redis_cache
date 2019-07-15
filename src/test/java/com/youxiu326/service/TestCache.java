package com.youxiu326.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCache {

    @Autowired
    private TestCacheService testCacheService;

    @Test
    public void test() {
        String id = "ming";
        System.out.println("第一次访问没有缓存--------");
        long oneNow = System.currentTimeMillis();
        System.out.println(testCacheService.testCache(id));
        System.out.println("耗时:" + (System.currentTimeMillis() - oneNow) + "ms");

        System.out.println("第二次访问有缓存--------");
        long twoNow = System.currentTimeMillis();
        System.out.println(testCacheService.testCache(id));
        System.out.println("耗时:" + (System.currentTimeMillis() - twoNow) + "ms");


        System.out.println("更新缓存信息--------");
        long threeNow = System.currentTimeMillis();
        System.out.println(testCacheService.testCachePut(id));
        System.out.println("耗时:" + (System.currentTimeMillis() - threeNow) + "ms");


        System.out.println("获取更新后的缓存信息-------");
        long fourNow = System.currentTimeMillis();
        System.out.println(testCacheService.testCache(id));
        System.out.println("耗时:" + (System.currentTimeMillis() - fourNow) + "ms");


        System.out.println("移除缓存------并且调用testCache方法");
        testCacheService.removeCache(id);
        long fiveNow = System.currentTimeMillis();
        System.out.println(testCacheService.testCache(id));
        System.out.println("耗时:" + (System.currentTimeMillis() - fiveNow) + "ms");
    }
}