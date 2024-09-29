package cn.graht.springbootinit.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @author  graht
 * @param <T>
 */
@Data
public class ResultApi<T> implements Serializable {
    private final static long serialVersionUID = 1L;
    private int code;
    private String msg;
    private T data;
    private String description;

    public ResultApi(int code, String msg, T data,String description) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.description = description;
    }
    public ResultApi(int code, T data) {
        this(code, "", data,"");
    }

    public ResultApi(int code, String msg) {
        this(code, msg, null,"");
    }
    public ResultApi(int code, String msg,String description) {
        this(code, msg, null,description);
    }
    public ResultApi(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg(), null,errorCode.getDescription());
    }
    public ResultApi(ErrorCode errorCode,String description) {
        this(errorCode.getCode(), errorCode.getMsg(), null,description);
    }
    public ResultApi(ErrorCode errorCode,T data) {
        this(errorCode.getCode(), errorCode.getMsg(), data,errorCode.getDescription());
    }

}
