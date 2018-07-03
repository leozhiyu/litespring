/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.PropertyValue;
import com.litespring.beans.SimpleTypeConverter;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.config.ConfigurableBeanFactory;
import com.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
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
            throw new BeanCreationException("未定义 bean");
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
        // 初始化bean
        Object bean = this.instantiateBean(bd);
        // 设置属性
        this.populateBean(bd, bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition bd) {
        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader classLoader = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class clazz = classLoader.loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for '" + beanClassName + "' failed" + e);
            }
        }

    }

    private void populateBean(BeanDefinition bd, Object bean) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();

        if (propertyValues == null || propertyValues.isEmpty()) {
            return;
        }

        // setter 注入

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : propertyValues) {
                String propertyName = pv.getName();
                Object propertyValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(propertyValue);
                // 获得 bean 信息
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                // 获得属性描述符，通过反射 set 值
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : descriptors) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", e);
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
