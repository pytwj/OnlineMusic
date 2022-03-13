package cn.wikitang.onlinemusic.common.enums;

/**
 * @Package: cn.wikitang.onlinemusic.common.enums
 * @ClassName: CommonErrorMsgEnum
 * @Author: WikiTang
 * @Date: 2022/3/13 18:30
 * @Description:
 */
public enum CommonErrorMsgEnum {
    ERROR(0, "失败"),
    SUCCESS(1, "成功"),
    RUNTIME_EXCEPTION(2, "异常");

    private Integer code;
    private String message;

    private CommonErrorMsgEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonErrorMsgEnum getEnumByInstance(Integer instance) {
        CommonErrorMsgEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CommonErrorMsgEnum erpErrorMsgEnum = var1[var3];
            if (erpErrorMsgEnum.getCode().equals(instance) || erpErrorMsgEnum.getMessage().equals(instance)) {
                return erpErrorMsgEnum;
            }
        }

        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
