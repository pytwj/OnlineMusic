package cn.wikitang.onlinemusic.common.enums;

/**
 * @Package: cn.wikitang.onlinemusic.common.enums
 * @ClassName: ResultEnum
 * @Author: WikiTang
 * @Date: 2022/3/13 17:22
 * @Description:
 */
public enum ResultEnum {
    ERROR(0, "失败"),
    SUCCESS(1, "成功"),
    RUNTIME_EXCEPTION(2, "异常");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}