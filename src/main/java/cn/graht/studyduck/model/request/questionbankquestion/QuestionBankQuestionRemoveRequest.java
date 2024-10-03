package cn.graht.studyduck.model.request.questionbankquestion;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除题库题目关联表请求
 *
 * @author graht
 */
@Data
public class QuestionBankQuestionRemoveRequest implements Serializable {

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}