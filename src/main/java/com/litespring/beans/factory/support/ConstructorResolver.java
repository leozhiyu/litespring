/**
 * @author:Leo
 * @create 2018/7/3
 * @desc
 */
package com.litespring.beans.factory.support;

import com.litespring.beans.BeanDefinition;
import com.litespring.beans.ConstructorArgument;
import com.litespring.beans.SimpleTypeConverter;
import com.litespring.beans.factory.BeanCreationException;
import com.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.List;


public class ConstructorResolver {
    protected final Log logger = LogFactory.getLog(getClass());

    // 此处不用 BeanFactory 是因为 BeanFactory 太底层了，用上层一些的比较好，少暴露一些接口
    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {
        // 确认使用的构造器
        Constructor<?> constructorToUse = null;
        // 确认要使用的参数
        Object[] argsToUse = null;

        // bean 对应的类
        Class<?> beanClass = null;

        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException( bd.getID(), "Instantiation of bean failed, can't resolve class", e);
        }

        // bean 构造函数列表
        Constructor[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument conargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != conargs.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(parameterTypes,
                    conargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);
            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException( bd.getID(), "can't find a create instance using "+constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?> [] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter ){
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //获取参数的值，可能是TypedStringValue, 也可能是RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try{
                //获得真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary( originalValue);
                //如果参数类型是 int, 但是值是字符串,例如"3",还需要转型
                //如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //转型成功，记录下来
                argsToUse[i] = convertedValue;
            }catch(Exception e){
                logger.error(e);
                return false;
            }
        }
        return true;
    }
}

