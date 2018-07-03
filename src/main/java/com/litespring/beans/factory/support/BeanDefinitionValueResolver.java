/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.factory.BeanFactory;
import com.litespring.beans.factory.config.RuntimeBeanReference;
import com.litespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory factory) {
        beanFactory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference val = (RuntimeBeanReference) value;
            Object bean = beanFactory.getBean(val.getBeanName());
            return bean;
        }
        if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        }
        throw new RuntimeException("the value " + value +" has not implemented");
    }
}
