package cn.graht.studyduck.commons;

/**
 * 错误码
 * @author graht
 */
public enum ErrorCode {
    SUCCESS(20000,"响应成功","success"),
    SYSTEM_ERROR(50000,"系统内部错误",""),
    PARAMS_ERROR(40000,"参数错误","请求参数错误"),
    PARAMS_NULL_ERROR(40001,"参数为空","请求参数为空"),
    NULL_ERROR(40002,"结果为空","请求结果为空"),
    NO_AUTH(40101,"无权限","没权限"),
    NOT_LOGIN_ERROR(40100,"未登录","用户没登录"),
    ;
    /**
     * 状态码信息
     */
    private final int code;
    private final String msg;
    private final String description;

    ErrorCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }
}
