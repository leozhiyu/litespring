/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.test.v1;

import com.litespring.core.io.ClassPathResource;
import com.litespring.core.io.FileSystemResource;
import com.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {
    @Test
    public void testClassPathResource() throws IOException {
        Resource resource = new ClassPathResource("pet-v1.xml");

        try(InputStream is = resource.getInputStream();){
            Assert.assertNotNull(is);
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        Resource resource = new FileSystemResource("src\\test\\resources\\pet-v1.xml");
        try(InputStream is = resource.getInputStream();){
            Assert.assertNotNull(is);
        }
    }
}
