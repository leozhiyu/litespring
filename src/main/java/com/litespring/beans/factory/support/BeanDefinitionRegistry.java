/**
 * @author:Leo
 * @create 2018/6/20
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanID);
    void registerBeanDefinition(String beanID, BeanDefinition bd);
}
