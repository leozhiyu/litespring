/**
 * @author:Leo
 * @create 2018/6/22
 * @desc
 */
package com.litespring.util;

public abstract class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
