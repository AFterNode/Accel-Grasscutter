package cn.afternode.accel.plugin;

import cn.afternode.accel.Accel;
import cn.afternode.accel.event.events.PluginLoadFinishEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;

public abstract class AccelPlugin {
    public final String name;
    private PluginProperty prop = null;
    private final Logger logger;

    public AccelPlugin(String name) {
        this.name = name;
        logger = LoggerFactory.getLogger(name);
        Accel.getEventManager().register(this);
    }

    /**
     * Plugin startup logic
     */
    public void init() {}

    /**
     * Plugin enable logic
     */
    public void onEnable() {}

    /**
     * Plugin disable logic
     */
    public void onDisable(){}

    /**
     * DO NOT USE THIS METHOD IN YOUR PLUGIN
     * @param prop Plugin meta property
     */
    public final void setMeta(PluginProperty prop) {
        if (this.prop == null) {
            this.prop = prop;
        }
    }

    public final Logger getLogger() {
        return logger;
    }

    /**
     * Get resource in your plugin jar as an InputStream
     */
    public final InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }

    /**
     * Get resource in your plugin jar as an URL
     */
    public final URL getResource(String path) {
        return getClass().getResource(path);
    }

    /**
     * Call when all plugins are loaded
     */
    public final void onPluginLoadFinish(PluginLoadFinishEvent event){}
}
