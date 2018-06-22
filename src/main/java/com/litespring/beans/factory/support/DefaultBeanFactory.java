/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.config.ConfigurableBeanFactory;
import com.litespring.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements BeanDefinitionRegistry,ConfigurableBeanFactory {

    /**
     * 存放 bean 的定义，不仅存在 Map 中，BeanDefinition 的子类 GenericBeanDefinition 中也要存储，方便扩展
     */
    private final Map<String,BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    private ClassLoader beanClassLoader;

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
     * 获得 bean 实例，通过反射实现
     * @param id
     * @return
     */
    public Object getBean(String id) {
        BeanDefinition beanDefinition = this.getBeanDefinition(id);
        if (beanDefinition == null) {
            return null;
        }
        // 先判断这个 bean 是否是单例
        if (beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(id);
            if (bean == null) {
                bean = createBean(beanDefinition);
                this.registerSingleton(id, bean);
            }
            return bean;
        }
        return createBean(beanDefinition);
    }

    private Object createBean(BeanDefinition bd) {
        String beanClassName = bd.getBeanClassName();
        ClassLoader classLoader = this.getBeanClassLoader();
        try {
            Class clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for '" + beanClassName + "' failed" + e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtil.getDefaultClassLoader());
    }
}
