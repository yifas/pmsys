package com.bin.common;

/**
 * 枚举类型无法使用Lombok
 * 
 */
public enum ErrorCode {
    // 3. 定义枚举对象，并赋值
    SUCCESS(2000,"请求成功"),
    FAIL(0,"系统错误"),
    ;

    // 1.先定义枚举对象的参数值
    private final int code;
    private final String msg;

    // 2.生成构造方法和get方法
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
