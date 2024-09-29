package cn.graht.springbootinit.constant;

import java.util.Random;

public interface UserRedisKeyConstants {
    String RECOMMEND_USERS = "find_friends:user:recommend_users:";
    String LOCK_UPDATE_TEAM = "find_friends:team:update:";
    String LOCK_SEND_JOIN_TEAM = "find_friends:team:send_join_team:";
    String LOCK_USER_MATCH = "find_friends:team:user_match:";
    Integer DEFAULT_TIMEOUT = 60*60*10+new Random().nextInt(1000);
}
