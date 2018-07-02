/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.test.v2;

import com.litespring.context.ApplicationContext;
import com.litespring.context.support.ClassPathXmlApplicationContext;
import com.litespring.dao.v2.AccountDao;
import com.litespring.dao.v2.ItemDao;
import com.litespring.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextV2 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());

        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);

        Assert.assertEquals("leo", petStoreService.getOwner());

        Assert.assertEquals(1, petStoreService.getVersion());
    }
}
