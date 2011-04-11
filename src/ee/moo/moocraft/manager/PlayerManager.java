package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class PlayerManager {

    private MooCraft plugin;
    private static ConcurrentHashMap<Integer, LocalPlayer> playerList = new ConcurrentHashMap<Integer, LocalPlayer>();

    public PlayerManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void enable() {

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(player);
        }

    }

    public void disable() {
        playerList.clear();
    }

    public void addPlayer(Player entity) {
        if (!playerList.containsKey(entity.getEntityId())) {
            playerList.put(entity.getEntityId(), new LocalPlayer(entity, plugin.getPersistenceManager()));
        }
    }

    public void removePlayer(Player entity) {
        playerList.remove(entity.getEntityId());
    }

    public LocalPlayer getPlayer(Player player) {
        return playerList.get(player.getEntityId());
    }

    public void removeWorld(World world) {

        for (LocalPlayer player : playerList.values()) {
            player.removeHome(world);
        }

    }
    
}
