/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 * ApplicationContext 更贴近用户，BeanFactory 偏底层
 * ApplicationContext 只暴露给用户 getBean 方法，方便用户使用
 */
package com.litespring.context;

import com.litespring.beans.factory.config.ConfigurableBeanFactory;


public interface ApplicationContext extends ConfigurableBeanFactory{

}
