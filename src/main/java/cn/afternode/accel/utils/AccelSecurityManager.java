package cn.afternode.accel.utils;

import cn.afternode.accel.Accel;

import java.util.ArrayList;
import java.util.List;

public class AccelSecurityManager {
    private static final List<Class<?>> accessClasses = new ArrayList<>();
    static {
        accessClasses.add(Accel.class);
    }

    public static boolean isAccessible(Class<?> clazz) {
        return accessClasses.contains(clazz);
    }
}
