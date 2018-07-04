package com.jrdm.util;

import java.util.ResourceBundle;

/**
 * <br/>
 * Created on 2018/7/3 10:39.
 *
 * @author zhubenle
 */
public final class ResourceBundleUtil {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("language.message");

    private ResourceBundleUtil() {
    }

    /**
     * 获取字符数据
     *
     * @param key
     *         key
     *
     * @return value
     */
    public static String getStringValue(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    /**
     * 获取整型数据
     *
     * @param key
     *         key
     *
     * @return value
     */
    public static Integer getIntegerValue(String key) {
        return Integer.valueOf(RESOURCE_BUNDLE.getString(key));
    }

}
