package cn.graht.studyduck.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.graht.studyduck.annotation.AuthCheck;
import cn.graht.studyduck.commons.ResultApi;
import cn.graht.studyduck.commons.DeleteRequest;
import cn.graht.studyduck.commons.ErrorCode;
import cn.graht.studyduck.commons.ResultUtil;
import cn.graht.studyduck.constant.UserConstant;
import cn.graht.studyduck.exception.BusinessException;
import cn.graht.studyduck.exception.ThrowUtils;
import cn.graht.studyduck.model.request.user.UserAddRequest;
import cn.graht.studyduck.model.request.user.UserEditRequest;
import cn.graht.studyduck.model.request.user.UserQueryRequest;
import cn.graht.studyduck.model.request.user.UserUpdateRequest;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.model.vo.UserVO;
import cn.graht.studyduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author graht
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

}
