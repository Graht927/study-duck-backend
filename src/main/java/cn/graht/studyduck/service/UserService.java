package cn.graht.studyduck.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.graht.studyduck.model.request.user.UserQueryRequest;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author graht
 */
public interface UserService extends IService<User> {

}
