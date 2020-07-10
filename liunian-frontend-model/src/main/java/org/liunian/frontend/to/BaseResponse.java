package org.liunian.frontend.to;

import org.liunian.frontend.enums.ReturnCode;
import org.liunian.frontend.enums.ReturnEnum;
import org.springframework.beans.BeanUtils;

import java.text.MessageFormat;

public class BaseResponse {

    public static BaseResult success() {
        return new BaseResult(ReturnEnum.SUCCESS.getCode(), ReturnEnum.SUCCESS.getMessage());
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(ReturnEnum.SUCCESS.getCode(), ReturnEnum.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResult<T> success(T data, T output) {
        if (data != null) {
            BeanUtils.copyProperties(data, output);
        } else {
            output = null;
        }
        return new BaseResult<>(ReturnEnum.SUCCESS.getCode(), ReturnEnum.SUCCESS.getMessage(), output);
    }

    public static <T> BaseResult<T> success(ReturnCode returnEnum) {
        return new BaseResult<>(returnEnum.getCode(), returnEnum.getMessage());
    }

    public static <T> BaseResult<T> success(Integer code, String message) {
        return new BaseResult<>(code, message);
    }

    public static <T> BaseResult<T> success(ReturnCode returnEnum, Integer s1) {
        return new BaseResult<>(returnEnum.getCode(), MessageFormat.format(returnEnum.getMessage(), s1));
    }

    public static <T> BaseResult<T> success(Integer code, String message, Integer s1) {
        return new BaseResult<>(code, MessageFormat.format(message, s1));
    }

    public static <T> BaseResult<T> success(ReturnCode returnEnum, T data) {
        return new BaseResult<>(returnEnum.getCode(), returnEnum.getMessage(), data);
    }

    public static <T> BaseResult<T> success(Integer code, String message, T data) {
        return new BaseResult<>(code, message, data);
    }

    public static <T> BaseResult<T> error(ReturnCode returnEnum) {
        return new BaseResult<>(returnEnum.getCode(), returnEnum.getMessage());
    }

    public static <T> BaseResult<T> error(Integer code, String message) {
        return new BaseResult<>(code, message);
    }

    public static <T> BaseResult<T> error(String msg) {
        return new BaseResult<>(400, msg);
    }

    public static <T> BaseResult<T> error(ReturnCode returnEnum, T s1) {
        return new BaseResult<>(returnEnum.getCode(), MessageFormat.format(returnEnum.getMessage(), s1));
    }

    public static <T> BaseResult<T> error(Integer code, String message, T s1) {
        return new BaseResult<>(code, MessageFormat.format(message, s1));
    }

}
