/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.BeanFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanDefinitionRegistry,BeanFactory {

    /**
     * 存放 bean 的定义，不仅存在 Map 中，BeanDefinition 的子类 GenericBeanDefinition 中也要存储，方便扩展
     */
    private final Map<String,BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    public DefaultBeanFactory() {
    }

    /**
     * 根据 beanID 获取 bean 的定义
     * @param id
     * @return
     */
    public BeanDefinition getBeanDefinition(String id) {
        return beanDefinitionMap.get(id);
    }

    public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID, bd);
    }

    /**
     * 获得bean实例，通过反射实现
     * @param id
     * @return
     */
    public Object getBean(String id) {
        BeanDefinition beanDefinition = this.getBeanDefinition(id);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        String beanClassName = beanDefinition.getBeanClassName();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            Class clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for '" + beanClassName + "' failed" + e);
        }
    }
}
