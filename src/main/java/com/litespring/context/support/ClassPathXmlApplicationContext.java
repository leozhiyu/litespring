/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.context.support;

import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, this.getBeanClassLoader());
    }
}
