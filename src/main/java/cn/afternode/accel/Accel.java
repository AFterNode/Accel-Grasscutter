package cn.afternode.accel;

import cn.afternode.accel.plugin.PluginManager;
import cn.afternode.accel.utils.AccelSecurityManager;
import com.darkmagician6.eventapi.EventManager;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;

public class Accel {
    private static EventManager eventManager = null;
    private static Server server;

    public static PluginManager getPluginManager() {
        return GCPlugin.instance.getPluginManager();
    }

    public static EventManager getEventManager() {
        if (eventManager == null) {
            eventManager  = new EventManager();
        }
        return eventManager;
    }

    public static Server getServer() {
        return server;
    }

    /**
     * DO NOT USE THIS IN YOUR PLUGIN
     */
    public static void setServer(Server server) {
        if (!AccelSecurityManager.isAccessible(StackWalker.getInstance().getCallerClass())) {
            throw new IllegalStateException();
        }
        Accel.server = server;
    }

    public static Player getPlayer(int uid) {
        return Grasscutter.getGameServer().getPlayerByUid(uid, true);
    }
}
