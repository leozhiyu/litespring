/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.context.support;

import com.litespring.core.io.FileSystemResource;
import com.litespring.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
