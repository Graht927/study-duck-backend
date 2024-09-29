package cn.graht.studyduck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.graht.studyduck.mapper.UserMapper;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 用户服务实现
 *
 * @author graht
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
