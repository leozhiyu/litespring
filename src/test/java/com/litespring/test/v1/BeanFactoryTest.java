/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.test.v1;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.BeanDefinitionStoreException;
import com.litespring.beans.factory.support.DefaultBeanFactory;
import com.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.litespring.service.v1.PetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanFactoryTest {

    DefaultBeanFactory factory = null;

    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions("pet-v1.xml");
        BeanDefinition beanDefinition = factory.getBeanDefinition("pet");

        Assert.assertEquals("com.litespring.service.v1.PetService", beanDefinition.getBeanClassName());

        PetService petService = (PetService)factory.getBean("pet");

        Assert.assertNotNull(petService);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinitions("pet-v1.xml");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }

        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            DefaultBeanFactory factory = new DefaultBeanFactory();
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
            reader.loadBeanDefinitions("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
