package com.youxiu326.service;

public interface CacheTestService {

    public String save(String id,String value);

    public String delete(String id);

    public String update(String id,String value);

    public String get(String id);

} 