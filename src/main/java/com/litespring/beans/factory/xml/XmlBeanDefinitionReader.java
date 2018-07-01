/**
 * @author:Leo
 * @create 2018/6/20
 * @desc 解析 XML 文件，要注意 Element 之间的关系，别用错对象了，相当于层次嵌套
 */
package com.litespring.beans.factory.xml;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.PropertyValue;
import com.litespring.beans.factory.BeanDefinitionStoreException;
import com.litespring.beans.factory.config.RuntimeBeanReference;
import com.litespring.beans.factory.config.TypedStringValue;
import com.litespring.beans.factory.support.BeanDefinitionRegistry;
import com.litespring.beans.factory.support.GenericBeanDefinition;
import com.litespring.core.io.Resource;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    /**
     * 定义 xml 中 <bean> 标签的属性以及其子标签
     */
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";

    BeanDefinitionRegistry registry;

    protected final Logger logger = Logger.getLogger(getClass());

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
                parsePropertyElement(el, beanDefinition);
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

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator<Element> iterator = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element propElem = iterator.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (propertyName == null || propertyName.length() == 0) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }
            Object val = parsePropertyValue(propElem, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            // 在此处向 GenericBeanDefinition 中的 propertyValues 集合添加元素
            bd.getPropertyValues().add(pv);
        }

    }

    private Object parsePropertyValue(Element propElem, BeanDefinition bd, String propertyName) {
        boolean hasRefAttribute = (propElem.attribute(REF_ATTRIBUTE) != null);
        boolean hasValAttribute = (propElem.attribute(VALUE_ATTRIBUTE) != null);
        if (hasRefAttribute) {
            String refName = propElem.attributeValue(REF_ATTRIBUTE);
            if (refName == null || refName.length() == 0) {
                logger.error("<property> contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }
        if (hasValAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(propElem.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }
        throw new RuntimeException(propertyName + " must specify a ref or value");
    }
}
