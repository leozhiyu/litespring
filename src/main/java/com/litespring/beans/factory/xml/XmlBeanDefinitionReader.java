/**
 * @author:Leo
 * @create 2018/6/20
 * @desc
 */
package com.litespring.beans.factory.xml;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanDefinitionStoreException;
import com.litespring.beans.factory.support.BeanDefinitionRegistry;
import com.litespring.beans.factory.support.GenericBeanDefinition;
import com.litespring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    /**
     * 定义 xml 中 <bean> 标签的属性
     */
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 解析传进来的 xml 文件
     *
     * 将当前配置文件中所有的 bean 存放在 beanDefinitionMap 中
     * 当需要 getBean 获取实例时，通过 BeanDefinition 获得 className
     * 通过反射创建实例
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        InputStream inputStream = null;
        try {
            // 获得类加载器
            ClassLoader classLoader = this.getClass().getClassLoader();
            // 通过类加载器获得文件输入流
            inputStream = resource.getInputStream();
            // 创建 dom4j 对象
            SAXReader saxReader = new SAXReader();

            // 获得文件对象
            Document document = saxReader.read(inputStream);
            Element root = document.getRootElement();
            // 将当前 bean xml 文件中的所有 bean
            Iterator<Element> it = root.elementIterator();
            while (it.hasNext()) {
                Element el = it.next();
                String beanID = el.attributeValue(ID_ATTRIBUTE);
                String beanClassName = el.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(beanID,beanClassName);
                if (el.attribute(SCOPE_ATTRIBUTE) != null) {
                    beanDefinition.setScope(el.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(beanID, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document failed", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
