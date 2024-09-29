package cn.graht.studyduck.commons;

/**
 * 返回工具类
 * @author graht
 */
public class ResultUtil {
    /**
     *  正常返回
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResultApi<T> ok(T data){
        return new ResultApi<T>(ErrorCode.SUCCESS,data);
    }
    public static <T> ResultApi<T> ok(){
        return new ResultApi<T>(ErrorCode.SUCCESS);
    }

    /**
     *  错误返回
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> ResultApi<T> error(ErrorCode errorCode){
        return new ResultApi<T>(errorCode);
    }
    public static <T> ResultApi<T> error(ErrorCode errorCode,String description){
        return new ResultApi<T>(errorCode,description);
    }
    public static <T> ResultApi<T> error(int code,String msg,String description){
        return new ResultApi<T>(code,msg,description);
    }
}
