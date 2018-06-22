/**
 * @author:Leo
 * @create 2018/6/22
 * @desc
 */
package com.litespring.beans.factory.config;

import com.litespring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory{
    void setBeanClassLoader(ClassLoader beanClassLoader);
    ClassLoader getBeanClassLoader();
}
