package com.easymeeting.redis;

import com.easymeeting.constants.Constants;
import io.netty.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = Constants.MESSAGEING_HANDLE_CHANNEL_KEY, havingValue = Constants.MESSAGEING_HANDLE_CHANNEL_REDIS)
public class RedissonConfig {
    @Value("${spring.redis.host:}")
    private String redisHost;
    @Value("${spring.redis.port:}")
    private String redisPort;
    @Bean(name="redissionClient",destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        try{
            Config config= new Config();
            config.useSingleServer().setAddress("redis://"+redisHost+":"+redisPort);
            RedissonClient redissonClient = Redisson.create(config);
            return redissonClient;
        }catch (Exception e){
            log.error("redis配置错误，请检查配置");
        }
        return null;
    }
}
