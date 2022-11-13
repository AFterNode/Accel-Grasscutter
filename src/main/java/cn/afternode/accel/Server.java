package cn.afternode.accel;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;

import java.util.List;

public class Server {
    private final GameServer gcServer = Grasscutter.getGameServer();

    public List<Player> getOnlinePlayers() {
        return gcServer.getAnnouncementSystem().getOnlinePlayers();
    }
}
