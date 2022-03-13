package cn.wikitang.onlinemusic.common.exception;

/**
 * @Package: cn.wikitang.onlinemusic.common.exception
 * @ClassName: ApiException
 * @Author: WikiTang
 * @Date: 2022/3/13 17:24
 * @Description: 自定义异常处理
 */

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String msg;
    private final int code;

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 500;
    }

    public ApiException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = 500;
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ApiException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}

