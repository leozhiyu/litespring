/**
 * @author:Leo
 * @create 2018/7/2
 * @desc
 */
package com.litespring.test.v2;

import com.litespring.beans.SimpleTypeConverter;
import com.litespring.beans.TypeConverter;
import com.litespring.beans.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TypedConverterTest {
    @Test
    public void testConverterStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try{
            converter.convertIfNecessary("3.1", Integer.class);
        }catch(TypeMismatchException e){
            return;
        }
        fail();
    }

}
