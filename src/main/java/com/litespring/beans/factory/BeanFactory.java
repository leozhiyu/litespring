package com.litespring.beans.factory;

import com.litespring.beans.BeanDefinition;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String id);

    Object getBean(String id);
}
