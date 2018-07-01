/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 * 做这个的原因是 <bean> 标签不仅仅只有两个属性，有多个属性并且还有子标签
 * 如果仅仅在 DefaultBeanFactory 中用 Map 存储的话不方便扩展

 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String beanID;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    private List<PropertyValue> propertyValues = new ArrayList();

    public GenericBeanDefinition(String beanID, String beanClassName) {
        this.beanID = beanID;
        this.beanClassName = beanClassName;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }
}
