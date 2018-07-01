/**
 * @author:Leo
 * @create 2018/7/1
 * @desc <property> 标签中对应的 ref 属性
 */
package com.litespring.beans.factory.config;

public class RuntimeBeanReference {
    private final String beanName;
    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }
    public String getBeanName() {
        return this.beanName;
    }
}
