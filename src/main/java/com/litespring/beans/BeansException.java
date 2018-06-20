/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans;

public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
