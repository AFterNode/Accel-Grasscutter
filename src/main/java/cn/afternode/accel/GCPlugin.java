package cn.afternode.accel;

import cn.afternode.accel.event.EventWrappers;
import cn.afternode.accel.plugin.PluginManager;
import emu.grasscutter.plugin.Plugin;

import java.io.File;

public class GCPlugin extends Plugin {
    public static GCPlugin instance;
    public boolean debug = true;

    private File dataFolder;
    private File pluginFolder;

    private PluginManager pluginManager;
    private EventWrappers eventWrappers;

    @Override
    public void onEnable() {
        if (debug) {
            getLogger().warn("Running in debug mode.");
        }

        instance = this;

        dataFolder = getDataFolder();
        pluginFolder = new File(dataFolder, "plugins");
        if (pluginFolder.exists() && !pluginFolder.isDirectory()) {
            throw new RuntimeException("Plugin folder is not a directory");
        }
        if (!pluginFolder.exists()) {
            if (!pluginFolder.mkdirs()) {
                throw new RuntimeException("Could not create plugin folder");
            }
        }

        Accel.setServer(new Server());

        // Load plugins
        pluginManager = new PluginManager();
        pluginManager.loadPlugins();

        // Load event wrappers
        eventWrappers = new EventWrappers();
        eventWrappers.init();
    }

    public File getPluginFolder() {
        return pluginFolder;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
