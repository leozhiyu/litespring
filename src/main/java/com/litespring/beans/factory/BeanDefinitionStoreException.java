/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans.factory;

import com.litespring.beans.BeansException;

public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
