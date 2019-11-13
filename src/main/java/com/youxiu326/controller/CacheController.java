package com.youxiu326.controller;

import com.youxiu326.service.CacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CacheController {

    @Autowired
    private CacheTestService cacheTestService;

    /**
     * 根据ID获取信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public String test(@PathVariable("id") String id) {
        return cacheTestService.get(id);
    }

    /**
     * 删除某个ID的信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") String id) {
        return cacheTestService.delete(id);
    }

    /**
     * 保存某个ID的信息
     *
     * @param id
     * @return
     */
    @PostMapping
    public String save(@RequestParam("id") String id, @RequestParam("value") String value) {
        return cacheTestService.save(id, value);
    }

    /**
     * 更新某个ID的信息
     *
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public String update(@PathVariable("id") String id, @RequestParam("value") String value) {
        return cacheTestService.update(id, value);
    }
}