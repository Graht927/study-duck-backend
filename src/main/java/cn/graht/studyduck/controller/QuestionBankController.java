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
import cn.graht.studyduck.model.request.questionbank.QuestionBankAddRequest;
import cn.graht.studyduck.model.request.questionbank.QuestionBankEditRequest;
import cn.graht.studyduck.model.request.questionbank.QuestionBankQueryRequest;
import cn.graht.studyduck.model.request.questionbank.QuestionBankUpdateRequest;
import cn.graht.studyduck.model.entity.QuestionBank;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.model.vo.QuestionBankVO;
import cn.graht.studyduck.service.QuestionBankService;
import cn.graht.studyduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题库接口
 *
 * @author graht
 */
@RestController
@RequestMapping("/questionbank")
@Slf4j
public class QuestionBankController {

    @Resource
    private QuestionBankService questionbankService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建题库
     *
     * @param questionbankAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResultApi<Long> addQuestionBank(@RequestBody QuestionBankAddRequest questionbankAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionbankAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 request 进行转换
        QuestionBank questionbank = new QuestionBank();
        BeanUtils.copyProperties(questionbankAddRequest, questionbank);
        // 数据校验
        questionbankService.validQuestionBank(questionbank, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        questionbank.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = questionbankService.save(questionbank);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newQuestionBankId = questionbank.getId();
        return ResultUtil.ok(newQuestionBankId);
    }

    /**
     * 删除题库
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public ResultApi<Boolean> deleteQuestionBank(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionBank oldQuestionBank = questionbankService.getById(id);
        ThrowUtils.throwIf(oldQuestionBank == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestionBank.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 操作数据库
        boolean result = questionbankService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtil.ok(true);
    }

    /**
     * 更新题库（仅管理员可用）
     *
     * @param questionbankUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Boolean> updateQuestionBank(@RequestBody QuestionBankUpdateRequest questionbankUpdateRequest) {
        if (questionbankUpdateRequest == null || questionbankUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 request 进行转换
        QuestionBank questionbank = new QuestionBank();
        BeanUtils.copyProperties(questionbankUpdateRequest, questionbank);
        // 数据校验
        questionbankService.validQuestionBank(questionbank, false);
        // 判断是否存在
        long id = questionbankUpdateRequest.getId();
        QuestionBank oldQuestionBank = questionbankService.getById(id);
        ThrowUtils.throwIf(oldQuestionBank == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = questionbankService.updateById(questionbank);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtil.ok(true);
    }

    /**
     * 根据 id 获取题库（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public ResultApi<QuestionBankVO> getQuestionBankVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        QuestionBank questionbank = questionbankService.getById(id);
        ThrowUtils.throwIf(questionbank == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtil.ok(questionbankService.getQuestionBankVO(questionbank, request));
    }

    /**
     * 分页获取题库列表（仅管理员可用）
     *
     * @param questionbankQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Page<QuestionBank>> listQuestionBankByPage(@RequestBody QuestionBankQueryRequest questionbankQueryRequest) {
        long current = questionbankQueryRequest.getCurrent();
        long size = questionbankQueryRequest.getPageSize();
        // 查询数据库
        Page<QuestionBank> questionbankPage = questionbankService.page(new Page<>(current, size),
                questionbankService.getQueryWrapper(questionbankQueryRequest));
        return ResultUtil.ok(questionbankPage);
    }

    /**
     * 分页获取题库列表（封装类）
     *
     * @param questionbankQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public ResultApi<Page<QuestionBankVO>> listQuestionBankVOByPage(@RequestBody QuestionBankQueryRequest questionbankQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionbankQueryRequest.getCurrent();
        long size = questionbankQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBank> questionbankPage = questionbankService.page(new Page<>(current, size),
                questionbankService.getQueryWrapper(questionbankQueryRequest));
        // 获取封装类
        return ResultUtil.ok(questionbankService.getQuestionBankVOPage(questionbankPage, request));
    }

    /**
     * 分页获取当前登录用户创建的题库列表
     *
     * @param questionbankQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public ResultApi<Page<QuestionBankVO>> listMyQuestionBankVOByPage(@RequestBody QuestionBankQueryRequest questionbankQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(questionbankQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        questionbankQueryRequest.setUserId(loginUser.getId());
        long current = questionbankQueryRequest.getCurrent();
        long size = questionbankQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBank> questionbankPage = questionbankService.page(new Page<>(current, size),
                questionbankService.getQueryWrapper(questionbankQueryRequest));
        // 获取封装类
        return ResultUtil.ok(questionbankService.getQuestionBankVOPage(questionbankPage, request));
    }

    /**
     * 编辑题库（给用户使用）
     *
     * @param questionbankEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public ResultApi<Boolean> editQuestionBank(@RequestBody QuestionBankEditRequest questionbankEditRequest, HttpServletRequest request) {
        if (questionbankEditRequest == null || questionbankEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 request 进行转换
        QuestionBank questionbank = new QuestionBank();
        BeanUtils.copyProperties(questionbankEditRequest, questionbank);
        // 数据校验
        questionbankService.validQuestionBank(questionbank, false);
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        long id = questionbankEditRequest.getId();
        QuestionBank oldQuestionBank = questionbankService.getById(id);
        ThrowUtils.throwIf(oldQuestionBank == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestionBank.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 操作数据库
        boolean result = questionbankService.updateById(questionbank);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtil.ok(true);
    }
    // endregion
}
