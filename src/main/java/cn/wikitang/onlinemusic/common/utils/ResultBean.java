package cn.wikitang.onlinemusic.common.utils;

import cn.wikitang.onlinemusic.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * @Package: cn.wikitang.onlinemusic.common.utils
 * @ClassName: ResultBean
 * @Author: WikiTang
 * @Date: 2022/3/13 17:39
 * @Description: 公用的实体类信息返回
 */
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Deprecated
//    private Boolean success;

    private Integer code = 1;

    private String msg = "success";

    /**
     * 响应消息体
     */
//    @Deprecated
//    private T result;

    public ResultBean() {
    }

    public ResultBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

//    public ResultBean(Integer code, String msg, T result) {
//        this.code = code;
//        this.msg = msg;
//        this.result = result;
//        code为1代表成功，有例外时另外设置
//        this.success = (code == 1);
//    }

//    private ResultBean(T data) {
//        this.code = ResultEnum.SUCCESS.getCode();
//        this.msg = ResultEnum.SUCCESS.getMsg();
//        this.result = data;
//    }

    private ResultBean(String msg) {
        this.code = ResultEnum.ERROR.getCode();
        this.msg = msg;
    }

    private ResultBean(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

//    public Boolean getSuccess() {
//        return success;
//    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

//    public T getResult() {
//        return result;
//    }

//    public void setSuccess(Boolean success) {
//        this.success = success;
//    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public void setResult(T result) {
//        this.result = result;
//    }


    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
