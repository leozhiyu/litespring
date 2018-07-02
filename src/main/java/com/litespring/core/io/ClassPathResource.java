/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.core.io;

import com.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        // 共用同一个构造方法，保证 classLoader 一定会被初始化
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public InputStream getInputStream() throws FileNotFoundException {
        InputStream in = classLoader.getResourceAsStream(this.path);
        if (in == null) {
            throw new FileNotFoundException(path + " cat not opened");
        }
        return in;
    }

    public String getDescription() {
        return this.path;
    }
}
