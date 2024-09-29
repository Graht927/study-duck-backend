package cn.graht.springbootinit.configuration;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  redisson配置
 * @author grhat
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfiguration {

    private String host;
    private String port;
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", host, port));
        return (Redisson) Redisson.create(config);
    }
}
