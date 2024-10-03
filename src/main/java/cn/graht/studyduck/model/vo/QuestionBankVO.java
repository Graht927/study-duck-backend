package cn.graht.studyduck.model.vo;

import cn.graht.studyduck.model.entity.Question;
import cn.graht.studyduck.model.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题库视图
 *
 * @author graht
 */
@Data
public class QuestionBankVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String picture;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 创建用户信息
     */
    private UserVO user;
    /**
     * 题目列表
     */
    private Page<Question> questionPage;

    /**
     * 封装类转对象
     *
     * @param questionbankVO
     * @return
     */
    public static QuestionBank voToObj(QuestionBankVO questionbankVO) {
        if (questionbankVO == null) {
            return null;
        }
        QuestionBank questionbank = new QuestionBank();
        BeanUtils.copyProperties(questionbankVO, questionbank);
        return questionbank;
    }

    /**
     * 对象转封装类
     *
     * @param questionbank
     * @return
     */
    public static QuestionBankVO objToVo(QuestionBank questionbank) {
        if (questionbank == null) {
            return null;
        }
        QuestionBankVO questionbankVO = new QuestionBankVO();
        BeanUtils.copyProperties(questionbank, questionbankVO);
        return questionbankVO;
    }
}
