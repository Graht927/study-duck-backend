package cn.graht.studyduck.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.graht.studyduck.model.request.questionbank.QuestionBankQueryRequest;
import cn.graht.studyduck.model.entity.QuestionBank;
import cn.graht.studyduck.model.vo.QuestionBankVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题库服务
 *
 * @author graht
 */
public interface QuestionBankService extends IService<QuestionBank> {

    /**
     * 校验数据
     *
     * @param questionbank
     * @param add 对创建的数据进行校验
     */
    void validQuestionBank(QuestionBank questionbank, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionbankQueryRequest
     * @return
     */
    QueryWrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest questionbankQueryRequest);
    
    /**
     * 获取题库封装
     *
     * @param questionbank
     * @param request
     * @return
     */
    QuestionBankVO getQuestionBankVO(QuestionBank questionbank, HttpServletRequest request);

    /**
     * 分页获取题库封装
     *
     * @param questionbankPage
     * @param request
     * @return
     */
    Page<QuestionBankVO> getQuestionBankVOPage(Page<QuestionBank> questionbankPage, HttpServletRequest request);
}
