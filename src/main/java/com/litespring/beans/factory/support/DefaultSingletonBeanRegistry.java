/**
 * @author:Leo
 * @create 2018/6/22
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.factory.config.SingletonBeanRegistry;
import com.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(64);

    /**
     * 首先判断 bean 是否已经存在，如果存在的话抛出异常，不能重复注册
     * 如果存在，则注册新的bean
     * @param beanName
     * @param singletonObject
     */
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");
        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        singletonObjects.put(beanName, singletonObject);
    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
