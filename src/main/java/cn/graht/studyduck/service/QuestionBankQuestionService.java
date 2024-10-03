package cn.graht.studyduck.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.graht.studyduck.model.request.questionbankquestion.QuestionBankQuestionQueryRequest;
import cn.graht.studyduck.model.entity.QuestionBankQuestion;
import cn.graht.studyduck.model.vo.QuestionBankQuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题库题目关联表服务
 *
 * @author graht
 */
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    /**
     * 校验数据
     *
     * @param questionbankquestion
     * @param add 对创建的数据进行校验
     */
    void validQuestionBankQuestion(QuestionBankQuestion questionbankquestion, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionbankquestionQueryRequest
     * @return
     */
    QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionbankquestionQueryRequest);
    
    /**
     * 获取题库题目关联表封装
     *
     * @param questionbankquestion
     * @param request
     * @return
     */
    QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionbankquestion, HttpServletRequest request);

    /**
     * 分页获取题库题目关联表封装
     *
     * @param questionbankquestionPage
     * @param request
     * @return
     */
    Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionbankquestionPage, HttpServletRequest request);
}
