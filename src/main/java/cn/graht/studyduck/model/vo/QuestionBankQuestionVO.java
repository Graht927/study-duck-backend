package cn.graht.studyduck.model.vo;

import cn.graht.studyduck.model.entity.QuestionBankQuestion;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题库题目关联表视图
 *
 * @author graht
 */
@Data
public class QuestionBankQuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 作者
     */
    private UserVO user;
    /**
     * 创建用户id
     */
    private Long userId;

    /**
     * 封装类转对象
     *
     * @param questionbankquestionVO
     * @return
     */
    public static QuestionBankQuestion voToObj(QuestionBankQuestionVO questionbankquestionVO) {
        if (questionbankquestionVO == null) {
            return null;
        }
        QuestionBankQuestion questionbankquestion = new QuestionBankQuestion();
        BeanUtils.copyProperties(questionbankquestionVO, questionbankquestion);
        return questionbankquestion;
    }

    /**
     * 对象转封装类
     *
     * @param questionbankquestion
     * @return
     */
    public static QuestionBankQuestionVO objToVo(QuestionBankQuestion questionbankquestion) {
        if (questionbankquestion == null) {
            return null;
        }
        QuestionBankQuestionVO questionbankquestionVO = new QuestionBankQuestionVO();
        BeanUtils.copyProperties(questionbankquestion, questionbankquestionVO);
        return questionbankquestionVO;
    }
}
