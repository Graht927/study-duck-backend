package cn.graht.studyduck.configuration;

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
    private String password;
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", host, port)).setPassword(password);
        return (Redisson) Redisson.create(config);
    }
}
