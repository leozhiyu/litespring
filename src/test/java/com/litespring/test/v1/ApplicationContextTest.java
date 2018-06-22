/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.test.v1;

import com.litespring.context.ApplicationContext;
import com.litespring.context.support.FileSystemXmlApplicationContext;
import com.litespring.context.support.ClassPathXmlApplicationContext;
import com.litespring.service.v1.PetService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("pet-v1.xml");
        PetService petService = (PetService) ctx.getBean("pet");
        Assert.assertNotNull(petService);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src\\test\\resources\\pet-v1.xml");
        PetService petService = (PetService) ctx.getBean("pet");
        Assert.assertNotNull(petService);
    }
}
