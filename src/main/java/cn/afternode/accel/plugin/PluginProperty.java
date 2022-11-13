package cn.afternode.accel.plugin;

import java.util.Properties;

public class PluginProperty {
    private final Properties properties;

    public PluginProperty(Properties prop) {
        properties = prop;
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public String getName() {
        return properties.getProperty("name");
    }

    public String getMainClassName() {
        return properties.getProperty("main");
    }
}
