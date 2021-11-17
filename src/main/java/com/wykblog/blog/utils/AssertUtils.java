package com.wykblog.blog.utils;

import com.wykblog.blog.utils.exception.ErrorMessage;
import com.wykblog.blog.utils.exception.Exceptions;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xiazhengyue
 * @since 2020-06-23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtils {

    public static void notNullWithFail(Object object, String errorCode, String errorMsg) {
        if (object == null) {
            throw Exceptions.fail(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }

    public static void notNullWithFault(Object object, String errorCode, String errorMsg) {
        if (object == null) {
            throw Exceptions.fault(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }

    public static void notNull(Object object, String errorMsg) {
        if (object == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void notBlankWithFail(String str, String errorCode, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            throw Exceptions.fail(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }

    public static void notBlankWithFault(String str, String errorCode, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            throw Exceptions.fault(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }

    public static void notBlank(String str, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isTrueWithFail(Boolean expression, String errorCode, String errorMsg) {
        if (!expression) {
            throw Exceptions.fail(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }

    public static void isTrueWithFault(Boolean expression, String errorCode, String errorMsg) {
        if (!expression) {
            throw Exceptions.fault(ErrorMessage.errorMessage(errorCode, errorMsg));
        }
    }
}
