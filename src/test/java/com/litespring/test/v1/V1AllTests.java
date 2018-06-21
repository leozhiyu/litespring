/**
 * @author:Leo
 * @create 2018/6/21
 * @desc
 */
package com.litespring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BeanFactoryTest.class,
        ApplicationContextTest.class,
        ResourceTest.class})
public class V1AllTests {

}
