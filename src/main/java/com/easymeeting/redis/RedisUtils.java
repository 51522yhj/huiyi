package com.easymeeting.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component("redisUtils")
public class RedisUtils<T> {
    @Resource
    private RedisTemplate<String, T> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 删除缓存
     *
     * @parem key 可以传一个值或多个
     */
    public void delete(String... key)
    {
        if (key != null && key.length > 0)
        {
            if (key.length == 1)
            {
                redisTemplate.delete(key[0]);
            }
            else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }

    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return 成功失败
     */
    public boolean set(String key, T value)
    {
        try{
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("设置redisKey:{},value:{}失败", key, value);
            return false;
        }

    }
    public T get(String key)
    {
        return key == null? null: redisTemplate.opsForValue().get(key);
    }
    /**
     * 普通缓存放入并设置时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setex(String key, T value, long time)
    {
        try{
            if (time > 0)
            {
                redisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        }catch (Exception e) {
            logger.error("设置redisKey:{},value:{}失败", key, value);
            return false;
        }
    }
    public  void hset(String key, String field, T value)
    {
        redisTemplate.opsForHash().put(key, field, value);
    }
    public T hget(String key, String field){
        return (T) redisTemplate.opsForHash().get(key, field);
    }
    public void hdel(String key, String... field){
        redisTemplate.opsForHash().delete(key, field);
    }
    public List<T> hvals(String key){ return (List<T>) redisTemplate.opsForHash().values(key); }
}
