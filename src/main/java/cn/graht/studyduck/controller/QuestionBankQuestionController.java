package cn.graht.studyduck.controller;

import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionRemoveRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.graht.studyduck.annotation.AuthCheck;
import cn.graht.studyduck.commons.ResultApi;
import cn.graht.studyduck.commons.DeleteRequest;
import cn.graht.studyduck.commons.ErrorCode;
import cn.graht.studyduck.commons.ResultUtil;
import cn.graht.studyduck.constant.UserConstant;
import cn.graht.studyduck.exception.BusinessException;
import cn.graht.studyduck.exception.ThrowUtils;
import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionAddRequest;
import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionQueryRequest;
import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionUpdateRequest;
import cn.graht.studyduck.model.entity.QuestionBankQuestion;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.model.vo.QuestionBankQuestionVO;
import cn.graht.studyduck.service.QuestionBankQuestionService;
import cn.graht.studyduck.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 题库题目关联表接口
 *
 * @author graht
 */
@RestController
@RequestMapping("/questionbankquestion")
@Slf4j
public class QuestionBankQuestionController {

    @Resource
    private QuestionBankQuestionService questionbankquestionService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建题库题目关联表
     *
     * @param questionbankquestionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResultApi<Long> addQuestionBankQuestion(@RequestBody QuestionBankQuestionAddRequest questionbankquestionAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionbankquestionAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 request 进行转换
        QuestionBankQuestion questionbankquestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionbankquestionAddRequest, questionbankquestion);
        // 数据校验
        questionbankquestionService.validQuestionBankQuestion(questionbankquestion, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        questionbankquestion.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = questionbankquestionService.save(questionbankquestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newQuestionBankQuestionId = questionbankquestion.getId();
        return ResultUtil.ok(newQuestionBankQuestionId);
    }

    /**
     * 删除题库题目关联表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Boolean> deleteQuestionBankQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionBankQuestion oldQuestionBankQuestion = questionbankquestionService.getById(id);
        ThrowUtils.throwIf(oldQuestionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestionBankQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 操作数据库
        boolean result = questionbankquestionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtil.ok(true);
    }

    /**
     * 更新题库题目关联表（仅管理员可用）
     *
     * @param questionbankquestionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Boolean> updateQuestionBankQuestion(@RequestBody QuestionBankQuestionUpdateRequest questionbankquestionUpdateRequest) {
        if (questionbankquestionUpdateRequest == null || questionbankquestionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 request 进行转换
        QuestionBankQuestion questionbankquestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionbankquestionUpdateRequest, questionbankquestion);
        // 数据校验
        questionbankquestionService.validQuestionBankQuestion(questionbankquestion, false);
        // 判断是否存在
        long id = questionbankquestionUpdateRequest.getId();
        QuestionBankQuestion oldQuestionBankQuestion = questionbankquestionService.getById(id);
        ThrowUtils.throwIf(oldQuestionBankQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = questionbankquestionService.updateById(questionbankquestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtil.ok(true);
    }

    /**
     * 根据 id 获取题库题目关联表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public ResultApi<QuestionBankQuestionVO> getQuestionBankQuestionVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        QuestionBankQuestion questionbankquestion = questionbankquestionService.getById(id);
        ThrowUtils.throwIf(questionbankquestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtil.ok(questionbankquestionService.getQuestionBankQuestionVO(questionbankquestion, request));
    }

    /**
     * 分页获取题库题目关联表列表（仅管理员可用）
     *
     * @param questionbankquestionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Page<QuestionBankQuestion>> listQuestionBankQuestionByPage(@RequestBody QuestionBankQuestionQueryRequest questionbankquestionQueryRequest) {
        long current = questionbankquestionQueryRequest.getCurrent();
        long size = questionbankquestionQueryRequest.getPageSize();
        // 查询数据库
        Page<QuestionBankQuestion> questionbankquestionPage = questionbankquestionService.page(new Page<>(current, size),
                questionbankquestionService.getQueryWrapper(questionbankquestionQueryRequest));
        return ResultUtil.ok(questionbankquestionPage);
    }

    /**
     * 分页获取题库题目关联表列表（封装类）
     *
     * @param questionbankquestionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public ResultApi<Page<QuestionBankQuestionVO>> listQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionbankquestionQueryRequest,
                                                               HttpServletRequest request) {
        long current = questionbankquestionQueryRequest.getCurrent();
        long size = questionbankquestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionbankquestionPage = questionbankquestionService.page(new Page<>(current, size),
                questionbankquestionService.getQueryWrapper(questionbankquestionQueryRequest));
        // 获取封装类
        return ResultUtil.ok(questionbankquestionService.getQuestionBankQuestionVOPage(questionbankquestionPage, request));
    }

    /**
     * 分页获取当前登录用户创建的题库题目关联表列表
     *
     * @param questionbankquestionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public ResultApi<Page<QuestionBankQuestionVO>> listMyQuestionBankQuestionVOByPage(@RequestBody QuestionBankQuestionQueryRequest questionbankquestionQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(questionbankquestionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        questionbankquestionQueryRequest.setUserId(loginUser.getId());
        long current = questionbankquestionQueryRequest.getCurrent();
        long size = questionbankquestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<QuestionBankQuestion> questionbankquestionPage = questionbankquestionService.page(new Page<>(current, size),
                questionbankquestionService.getQueryWrapper(questionbankquestionQueryRequest));
        // 获取封装类
        return ResultUtil.ok(questionbankquestionService.getQuestionBankQuestionVOPage(questionbankquestionPage, request));
    }
    /**
     * 删除题库题目关联表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/remove")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResultApi<Boolean> removeQuestionBankQuestion(@RequestBody QuestionBankQuestionRemoveRequest questionBankQuestionRemoveRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(Objects.isNull(questionBankQuestionRemoveRequest),ErrorCode.PARAMS_ERROR);
        Long questionBankId = questionBankQuestionRemoveRequest.getQuestionBankId();
        Long questionId = questionBankQuestionRemoveRequest.getQuestionId();
        LambdaQueryWrapper<QuestionBankQuestion> wrapper = Wrappers.lambdaQuery(QuestionBankQuestion.class).eq(QuestionBankQuestion::getQuestionId, questionId).eq(QuestionBankQuestion::getQuestionBankId, questionBankId);
        boolean result = questionbankquestionService.remove(wrapper);
        return ResultUtil.ok(result);
    }
}
