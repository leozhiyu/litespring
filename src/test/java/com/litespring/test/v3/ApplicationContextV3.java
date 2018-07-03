/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.test.v3;

import com.litespring.context.ApplicationContext;
import com.litespring.context.support.ClassPathXmlApplicationContext;
import com.litespring.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextV3 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());

        Assert.assertEquals(1, petStoreService.getVersion());
    }
}
