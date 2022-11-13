package cn.afternode.accel.plugin;

import cn.afternode.accel.Accel;
import cn.afternode.accel.GCPlugin;
import cn.afternode.accel.event.events.PluginLoadFinishEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private final Logger logger;
    private final HashMap<String, AccelPlugin> loadedPlugins;
    private final List<AccelPlugin> enabledPlugins;

    public PluginManager() {
        logger = LoggerFactory.getLogger("AccelPluginManager");
        loadedPlugins = new HashMap<>();
        enabledPlugins = new ArrayList<>();
    }

    public void loadPlugins() {
        File[] plgFiles = GCPlugin.instance.getPluginFolder().listFiles();
        List<File> jarFiles = new ArrayList<>();
        for (File plgFile : plgFiles) {
            if (plgFile.getName().endsWith(".jar")) {
                jarFiles.add(plgFile);
            }
        }

        logger.info("Found " + jarFiles.size() + " jar files in plugin folder, start loading...");
        int loaded = 0;
        for (File jarFile : jarFiles) {
            try {
                loadPlugin(jarFile);
                loaded++;
            } catch (Exception e) {
                logger.error("Failed to load: " + jarFile.getName(), e);
            }
        }
        logger.info("Loaded " + loaded + " plugins");

        Accel.getEventManager().call(new PluginLoadFinishEvent());
    }

    public void loadPlugin(File input) {
        try {
            logger.info("Loading jar: " + input.getName());
            List<Class<?>> classList = new ArrayList<>();
            URLClassLoader loader = new URLClassLoader(
                    new URL[]{new URL("file:" + input.getAbsolutePath())}, getClass().getClassLoader()
            );

            JarFile file = new JarFile(input);
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try {
                        String name = entry.getName().split("\\.class")[0].replace("/", ".");
                        Class<?> clazz = loader.loadClass(name);
                        if (GCPlugin.instance.debug) {
                            logger.info("Loaded class: " + clazz.getName());
                        }
                        classList.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            Properties prop = null;
            Class<?> plgClass = null;
            for (Class<?> clazz : classList) {
                if (prop == null) {
                    prop = new Properties();
                    InputStream is = clazz.getClassLoader().getResourceAsStream("plugin.properties");
                    if (is == null) {
                        throw new NullPointerException("Plugin properties load failed");
                    }
                    prop.load(is);
                }
                if (clazz.getName().equals(prop.get("main"))) {
                    plgClass = clazz;
                    break;
                }
            }

            PluginProperty plgProp = new PluginProperty(prop);
            if (plgClass == null){
                throw new NullPointerException("Plugin man not found: " + plgProp.getMainClassName());
            }

            Object obj = plgClass.getDeclaredConstructor().newInstance();
            if (!(obj instanceof AccelPlugin plg)) {
                throw new IllegalArgumentException("Invalid plugin main: " + plgProp.getMainClassName());
            }
            if (!plg.name.equals(plgProp.getName())) {
                throw new IllegalArgumentException("Invalid plugin property");
            }
            if (loadedPlugins.containsKey(plg.name)) {
                throw new RuntimeException("The plugin name is in use: " +plg.name);
            }
            logger.info("Loading plugin: " + plg.name);
            plg.init();
            plg.onEnable();
        } catch (Throwable e)  {
            logger.error("Failed to load plugin file: " + input.getName(), e);
        }
    }

    public AccelPlugin getPlugin(@NotNull String name) {
        return loadedPlugins.get(name);
    }

    public AccelPlugin getPlugin(@NotNull Class<? extends AccelPlugin> clazz) {
        for (AccelPlugin plugin : loadedPlugins.values()) {
            if (plugin.getClass() == clazz) {
                return plugin;
            }
        }
        return null;
    }

    public void enablePlugin(@NotNull AccelPlugin plugin) {
        if (enabledPlugins.contains(plugin)) {
            throw new IllegalStateException("Plugin already enabled: " + plugin.name);
        }
        plugin.onEnable();
    }

    public void disablePlugin(@NotNull AccelPlugin plugin) {
        if (!enabledPlugins.contains(plugin)) {
            throw new IllegalStateException("Plugin hasn't enabled: " + plugin.name);
        }
        plugin.onDisable();
    }
}
