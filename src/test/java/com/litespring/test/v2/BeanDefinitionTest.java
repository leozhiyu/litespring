/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.test.v2;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.PropertyValue;
import com.litespring.beans.factory.config.RuntimeBeanReference;
import com.litespring.beans.factory.support.DefaultBeanFactory;
import com.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.litespring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTest {
    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        List<PropertyValue> propertyValues =  bd.getPropertyValues();
        // 测试是否拿到 property 元素
        Assert.assertTrue(propertyValues.size() == 2);
        // 测试 ref 属性
        {
            PropertyValue pv = this.getPropertyValue("accountDao", propertyValues);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue pv = this.getPropertyValue("itemDao", propertyValues);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> propertyValues) {
        for(PropertyValue pv : propertyValues) {
            if (name.equals(pv.getName())) {
                return pv;
            }
        }
        return null;
    }
}
