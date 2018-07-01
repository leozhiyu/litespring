/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.test.v2;

import com.litespring.beans.factory.config.RuntimeBeanReference;
import com.litespring.beans.factory.config.TypedStringValue;
import com.litespring.beans.factory.support.BeanDefinitionValueResolver;
import com.litespring.beans.factory.support.DefaultBeanFactory;
import com.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import com.litespring.core.io.ClassPathResource;
import com.litespring.dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanDefinitionValueResolverTest {
    BeanDefinitionValueResolver resolver;

    @Before
    public void init() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
    }
    @Test
    public void testResolveRuntimeBeanReference() {
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        TypedStringValue typedStringValue = new TypedStringValue("owner");
        Assert.assertNotNull(typedStringValue);
        Assert.assertEquals("owner", typedStringValue.getValue());

    }
}
