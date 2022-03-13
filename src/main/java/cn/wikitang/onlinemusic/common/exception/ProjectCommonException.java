package cn.wikitang.onlinemusic.common.exception;

import cn.wikitang.onlinemusic.common.enums.CommonErrorMsgEnum;

/**
 * @Package: cn.wikitang.onlinemusic.common.exception
 * @ClassName: ProjectCommonException
 * @Author: WikiTang
 * @Date: 2022/3/13 18:29
 * @Description: 项目通用异常处理
 */
public class ProjectCommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String msg;
    private final int code;

    public ProjectCommonException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 500;
    }

    public ProjectCommonException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = 500;
    }

    public ProjectCommonException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ProjectCommonException(CommonErrorMsgEnum error) {
        super(error.getMessage());
        this.msg = error.getMessage();
        this.code = error.getCode();
    }

    public ProjectCommonException(CommonErrorMsgEnum error, String msg) {
        super(msg);
        this.msg = msg;
        this.code = error.getCode();
    }

    public ProjectCommonException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }
}

