package cn.graht.springbootinit.job;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 缓存预热
 * @author graht
 */
@Component
public class PreCacheJob {
    @Scheduled(cron = "0 0 0 * * *")
    public void preCache() {
        System.out.println("缓存预热");

    }

}
