package cn.graht.studyduck.service.impl;

import cn.graht.studyduck.commons.ErrorCode;
import cn.graht.studyduck.constant.CommonConstant;
import cn.graht.studyduck.exception.ThrowUtils;
import cn.graht.studyduck.mapper.QuestionBankQuestionMapper;
import cn.graht.studyduck.model.entity.Question;
import cn.graht.studyduck.model.entity.QuestionBank;
import cn.graht.studyduck.model.entity.QuestionBankQuestion;
import cn.graht.studyduck.model.entity.User;
import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionQueryRequest;
import cn.graht.studyduck.model.vo.QuestionBankQuestionVO;
import cn.graht.studyduck.model.vo.UserVO;
import cn.graht.studyduck.service.QuestionBankQuestionService;
import cn.graht.studyduck.service.QuestionBankService;
import cn.graht.studyduck.service.QuestionService;
import cn.graht.studyduck.service.UserService;
import cn.graht.studyduck.utils.SqlUtils;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 题库题目关联表服务实现
 *
 * @author graht
 */
@Service
@Slf4j
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion> implements QuestionBankQuestionService {

    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionBankService questionBankService;

    /**
     * 校验数据
     *
     * @param questionbankquestion
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validQuestionBankQuestion(QuestionBankQuestion questionbankquestion, boolean add) {
        ThrowUtils.throwIf(questionbankquestion == null, ErrorCode.PARAMS_ERROR);
        /*// todo 从对象中取值
        String title = questionbankquestion.getTitle();
        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        if (StringUtils.isNotBlank(title)) {
            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
        }*/
        //题目和题库必须存在
        Long questionId = questionbankquestion.getQuestionId();
        if (questionId != null) {
            Question question = questionService.getById(questionId);
            ThrowUtils.throwIf(question == null, ErrorCode.PARAMS_ERROR,"题目不存在");
        }
        Long questionBankId = questionbankquestion.getQuestionBankId();
        if (questionBankId != null) {
            QuestionBank questionBank = questionBankService.getById(questionBankId);
            ThrowUtils.throwIf(questionBank == null, ErrorCode.PARAMS_ERROR,"题库不存在");
        }
    }
    /**
     * 获取查询条件
     *
     * @param questionbankquestionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionbankquestionQueryRequest) {
        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (questionbankquestionQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = questionbankquestionQueryRequest.getId();
        String sortField = questionbankquestionQueryRequest.getSortField();
        String sortOrder = questionbankquestionQueryRequest.getSortOrder();
        Long userId = questionbankquestionQueryRequest.getUserId();
        Long questionId = questionbankquestionQueryRequest.getQuestionId();
        Long questionBankId = questionbankquestionQueryRequest.getQuestionBankId();
        // todo 补充需要的查询条件
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "questionBankId", questionBankId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取题库题目关联表封装
     *
     * @param questionbankquestion
     * @param request
     * @return
     */
    @Override
    public QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionbankquestion, HttpServletRequest request) {
        // 对象转封装类
        QuestionBankQuestionVO questionbankquestionVO = QuestionBankQuestionVO.objToVo(questionbankquestion);

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionbankquestion.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionbankquestionVO.setUser(userVO);
        return questionbankquestionVO;
    }

    /**
     * 分页获取题库题目关联表封装
     *
     * @param questionbankquestionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionbankquestionPage, HttpServletRequest request) {
        List<QuestionBankQuestion> questionbankquestionList = questionbankquestionPage.getRecords();
        Page<QuestionBankQuestionVO> questionbankquestionVOPage = new Page<>(questionbankquestionPage.getCurrent(), questionbankquestionPage.getSize(), questionbankquestionPage.getTotal());
        if (CollUtil.isEmpty(questionbankquestionList)) {
            return questionbankquestionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionBankQuestionVO> questionbankquestionVOList = questionbankquestionList.stream().map(questionbankquestion -> {
            return QuestionBankQuestionVO.objToVo(questionbankquestion);
        }).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionbankquestionList.stream().map(QuestionBankQuestion::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        questionbankquestionVOList.forEach(questionbankquestionVO -> {
            Long userId = questionbankquestionVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionbankquestionVO.setUser(userService.getUserVO(user));
        });
        // endregion

        questionbankquestionVOPage.setRecords(questionbankquestionVOList);
        return questionbankquestionVOPage;
    }

}
