package cn.graht.studyduck.model.request.questionbankquestion;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建题库题目关联表请求
 *
 * @author graht
 */
@Data
public class QuestionBankQuestionAddRequest implements Serializable {

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}