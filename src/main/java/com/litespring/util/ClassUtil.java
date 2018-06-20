/**
 * @author:Leo
 * @create 2018/6/13
 * @desc
 * todo 这一块不太懂，还需补充 classloader 知识
 * 出于对安全的考虑，先从线程的classloader开始找，如果找不到则从当前类找，最后从系统找
 * 因为有可能不同的 jar 包或者 RMI 远程调用，所以可能 classloader 会在本地找不到
 */
package com.litespring.util;

public abstract class ClassUtil {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassUtil.class.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
        }
        return cl;
    }
}
