package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalMinecart;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * User: Tanel Suurhans
 * Date: 3/20/11
 */
public class MinecartManager {

    private MooCraft plugin;
    private static ConcurrentMap<Integer, LocalMinecart> minecarts = new ConcurrentHashMap<Integer, LocalMinecart>();

    public MinecartManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public LocalMinecart getMinecart(Vehicle vehicle) {

        if (vehicle instanceof Minecart) {
            return getMinecart((Minecart) vehicle);
        } else {
            return null;
        }

    }

    public LocalMinecart getMinecart(Minecart minecart) {

        LocalMinecart localCart = minecarts.get(minecart.getEntityId());

        if (localCart == null) {
            localCart = new LocalMinecart(minecart);
            minecarts.put(minecart.getEntityId(), localCart);
        }

        return localCart;

    }

    public void removeMinecart(LocalMinecart minecart) {

        if (minecarts.containsKey(minecart.getEntityId())) {
            minecarts.remove(minecart.getEntityId());
        }

    }

    public void enable() {
    }

    public void disable() {
        minecarts.clear();
    }

}
