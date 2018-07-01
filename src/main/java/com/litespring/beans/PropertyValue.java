/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.beans;

public class PropertyValue {
    private final String name;
    private final Object value;

    // todo converted
    // todo convertedValue

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
