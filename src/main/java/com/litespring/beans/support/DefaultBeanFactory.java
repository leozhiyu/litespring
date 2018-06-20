/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 */
package com.litespring.beans.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.BeanDefinitionStoreException;
import com.litespring.beans.factory.BeanFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {

    /**
     * 定义 xml 中 <bean> 标签的属性
     */
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    /**
     * 存放 bean 的定义，不仅存在 Map 中，BeanDefinition 的子类 GenericBeanDefinition 中也要存储，方便扩展
     */
    private final Map<String,BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    /**
     * 构造函数，在此初始化 bean 定义
     * @param configFile
     */
    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 解析传进来的 xml 文件
     *
     * 将当前配置文件中所有的 bean 存放在 beanDefinitionMap 中
     * 当需要 getBean 获取实例时，通过 BeanDefinition 获得className
     * 通过反射创建实例
     * @param configFile
     */
    public void loadBeanDefinition(String configFile) {
        InputStream inputStream = null;
        try {
            // 获得类加载器
            ClassLoader classLoader = this.getClass().getClassLoader();
            // 通过类加载器获得文件输入流
            inputStream = classLoader.getResourceAsStream(configFile);
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
                this.beanDefinitionMap.put(beanID, beanDefinition);
            }
        } catch (DocumentException e) {
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

    /**
     * 根据 beanID 获取 bean 的定义
     * @param id
     * @return
     */
    public BeanDefinition getBeanDefinition(String id) {
        return beanDefinitionMap.get(id);
    }

    /**
     * 获得bean实例，通过反射实现
     * @param id
     * @return
     */
    public Object getBean(String id) {
        BeanDefinition beanDefinition = this.getBeanDefinition(id);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        String beanClassName = beanDefinition.getBeanClassName();
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            Class clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for '" + beanClassName + "' failed" + e);
        }
    }
}
