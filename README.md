
###### 使用redis 测试spring cache

<br>
[参考简书](https://www.jianshu.com/p/fc076b6c2a13?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
<br>
[参考脚本之家][https://www.jb51.net/article/112849.htm]
<br>

```

缓存
首先在get方法上加上@Cacheable注解，运行代码测试。
我们使用postman做测试，测试地址为http://localhost:8080/1，浏览器响应Get value by id=1,the value isthis no 1，服务器控制台打印两行日志
Get value by id=1,the value isthis no 1
Get value by id=1, The time is 1516004770216
但我们再次刷新浏览器地址时，浏览器正常返回，但控制台已经不再打印了，原因是第二次调用时，Spring 已经不再执行方法，而是直接获取缓存的值。Spring Cache将函数的返回值以函数参数为key,缓存在了名为test的缓存中。
这里我们使用了@Cacheable注解，注解中的cacheNames指定了这里读取的是哪个Cache。这里会在cacheName="test"的cache中去查找key是id的缓存对象。
删除缓存中的数据
在上面的程序中，如果我们通过delete请求删除指定值，发送delete请求到http://localhost:8080/1,这个时候，值已经从map中删除了，但我们get 请求到http://localhost:8080/1的时候，仍然可以拿到值，这是因为我们在删除数据的时候，没有删除缓存中的数据，而在前面的get方法中，方法的运行结果仍然被保存着，Spring不会去重新读取，而是直接读取缓存。这个时候，我们在方法前面加上注解

@Override
@CacheEvict(cacheNames = "test")
public String delete(String id) {
    return enties.remove(id);
}

先后测试，首先调用get请求，会正确显示返回值为Get value by id=1,the value is 1
然后调用delete请求。将数据从cache和map中删除，再次调用get请求，这时返回Get value by id=1,the value is null，代表该值确实从缓存中删除了。
这里我们使用了@CacheEvict注解，cacheNames指定了删除哪个cache中的数据，默认会使用方法的参数作为删除的key

更新缓存
当程序到这里时，如果我们运行post请求，请求体为 id=1&value=new1，这时控制台打印save value new value1 with key 1，代码会将值保存到map中，但我们运行get请求时，会发现返回值仍然是之前的状态。这是我们可以使用

@Override
@CachePut(cacheNames = "test", key = "#id")
public String save(String id, String value) {
    logger.info("save value " + value + " with key " + id);
    return enties.put(id, value);
}

重新执行代码，我们先发送delete请求，从map和和cache中删除数据。然后再发送post请求，写入数据到map中。最后再发送get请求，会发现现在可以正确的取到值了，控制台也没有打印从map中获取数据的日志。
这里用到了@CachePut注解，这个注解的作用是将方法的返回值按照给定的key,写入到cacheNames指定的cache中去。
同样，我们需要再put方法上加上@CachePut注解，让修改也能刷新缓存中的数据。
到这里，一个简单的包含增删改查的缓存应用就完成了。
三、划重点
几个注解

@EnableCaching 启用缓存配置
@Cacheable 指定某个方法的返回值是可以缓存的。在注解属性中指定缓存规则。
@Cacheput 将方法的返回值缓存到指定的key中
@CacheEvict 删除指定的缓存数据
注意
@Cacheable和@Cacheput都会将方法的执行结果按指定的key放到缓存中，@Cacheable在执行时，会先检测缓存中是否有数据存在，如果有，直接从缓存中读取。如果没有，执行方法，将返回值放入缓存，而@Cacheput会先执行方法，然后再将执行结果写入缓存。使用@Cacheput的方法一定会执行

```


```

@CacheConfig 注解

所有的@Cacheable（）里面都有一个value＝“xxx”的属性，这显然如果方法多了，
写起来也是挺累的，如果可以一次性声明完 那就省事了，
所以，有了@CacheConfig这个配置，
@CacheConfig is a class-level annotation that allows to share the cache names，
如果你在你的方法写别的名字，那么依然以方法的名字为准。

@CacheConfig("books")
public class BookRepositoryImpl implements BookRepository {

  @Cacheable
  public Book findBook(ISBN isbn) {...}

}


```