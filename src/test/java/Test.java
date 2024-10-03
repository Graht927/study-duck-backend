import cn.graht.studyduck.MainApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author GRAHT
 */
@SpringBootTest(classes = MainApplication.class)
public class Test {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @org.junit.jupiter.api.Test
    public void test() {
        redisTemplate.opsForValue().set("hello", "world");
    }
}
