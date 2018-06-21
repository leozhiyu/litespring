/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 * 做这个的原因是 <bean> 标签不仅仅只有两个属性，有多个属性并且还有子标签
 * 如果仅仅在 DefaultBeanFactory 中用 Map 存储的话不方便扩展

 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanID;
    private String beanClassName;

    public GenericBeanDefinition(String beanID, String beanClassName) {
        this.beanID = beanID;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
}
