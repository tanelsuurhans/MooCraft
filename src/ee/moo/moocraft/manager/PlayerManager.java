package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.player.MooPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class PlayerManager implements Iterable {

    private MooCraft plugin;
    private static HashMap<Integer, MooPlayer> playerList = new HashMap<Integer, MooPlayer>();

    public PlayerManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player entity) {

        if (!playerList.containsKey(entity.getEntityId())) {
            playerList.put(entity.getEntityId(), new MooPlayer(entity, plugin.getDatabaseManager()));
        }

    }

    public void removePlayer(Entity entity) {
        playerList.remove(entity.getEntityId());
    }

    public MooPlayer getPlayer(Integer entityId) {
        return playerList.get(entityId);
    }

    public MooPlayer getPlayer(Entity entity) {
        return playerList.get(entity.getEntityId());
    }

    public Iterator<MooPlayer> iterator() {
        return playerList.values().iterator();
    }
    
}
