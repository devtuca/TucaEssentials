package managers;

import com.tucaessentials.TucaEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HomeManager {

    public static FileConfiguration map = TucaEssentials.homes;

    public static void setarHome(Player p, String nome) {

        String path = "Homes." + p.getUniqueId().toString() + "." + nome;
        map.set(path + ".X", p.getLocation().getX());
        map.set(path + ".Y", p.getLocation().getY());
        map.set(path + ".Z", p.getLocation().getZ());
        map.set(path + ".Yaw", p.getLocation().getYaw());
        map.set(path + ".Pitch", p.getLocation().getPitch());
        map.set(path + ".World", p.getLocation().getWorld().getName());
        TucaEssentials.saveHomesFile();
    }

    public static void sendHome(Player p, String nome) {
        p.teleport(getHomeLocation(p, nome));
    }

    public static Location getHomeLocation(Player p, String nome) {
        String path = "Homes." + p.getUniqueId().toString() + "." + nome;
        return new Location(

                Bukkit.getWorld(map.getString(path + ".World")),
                map.getDouble(path + ".X"),
                map.getDouble(path + ".Y"),
                map.getDouble(path + ".Z"),
                map.getLong(path + ".Yaw"),
                map.getLong(path + ".Pitch")

        );
    }

    public static boolean homeIsNull(Player p, String nome) {
        return  map.getString("Homes." + p.getUniqueId().toString() + "." + nome) == null;
    }

    public static boolean delHome(Player p, String nome) {
        String path = "Homes." + p.getUniqueId().toString() + "." + nome;
        map.set(path, null);
        TucaEssentials.saveHomesFile();
        return false;
    }
}
