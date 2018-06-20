/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.BeanDefinitionStoreException;
import com.litespring.beans.factory.BeanFactory;
import com.litespring.beans.support.DefaultBeanFactory;
import com.litespring.service.v1.PetService;
import org.junit.Assert;
import org.junit.Test;

public class BeanFactoryTest {
    @Test
    public void testGetBean() {
        BeanFactory factory = new DefaultBeanFactory("pet-v1.xml");

        BeanDefinition beanDefinition = factory.getBeanDefinition("pet");

        Assert.assertEquals("com.litespring.service.v1.PetService", beanDefinition.getBeanClassName());

        PetService petService = (PetService)factory.getBean("pet");

        Assert.assertNotNull(petService);
    }

    @Test
    public void testInvalidBean() {
        BeanFactory factory = new DefaultBeanFactory("pet-v1.xml");
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
            new DefaultBeanFactory("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
