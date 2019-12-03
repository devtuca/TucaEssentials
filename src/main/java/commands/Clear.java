package commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Clear implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("Somente jogadores.");
        }
        if (!(sender.hasPermission("TucaE.Clear"))) {
            sender.sendMessage("§cVocê não tem permissão.");
            return false;
        }
            if (args.length < 0) {
                sender.sendMessage("§cUse: /clear [player]");
                return false;
            }
            if (args.length == 0) {
                Player p = (Player) sender;
                if (inventoryIsEmpty(p)) {
                    p.sendMessage("§cInventário está vazio.");
                    return false;
                } else {
                    int size = Arrays.stream(p.getInventory().getContents())
                            .filter(i -> i != null && i.getType() != Material.AIR)
                            .mapToInt(ItemStack::getAmount).sum();

                    PlayerInventory inv = p.getInventory();
                    inv.setHelmet(null);
                    inv.setChestplate(null);
                    inv.setLeggings(null);
                    inv.setBoots(null);
                    inv.clear();
                    p.updateInventory();
                    p.sendMessage("§cForam limpos §a" + size + " §citens do seu inventário.");
                }
                return false;
            }
            if (args.length == 1) {
                Player p = (Player) sender;
                String targetName = args[0];
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(targetName);
                Player target = Bukkit.getPlayer(targetName);
                if(!(targetOffline.isOnline())) {
                    p.sendMessage("§cEste usúario está offline.");
                    return false;
                }
                if(inventoryIsEmpty(target)) {
                    p.sendMessage("§cO inventário de §a" + target.getName() + " §cestá vazio.");
                    return false;
                }

                PlayerInventory inv = target.getInventory();
                inv.setHelmet(null);
                inv.setChestplate(null);
                inv.setLeggings(null);
                inv.setBoots(null);
                inv.clear();
                target.updateInventory();
                int size = Arrays.stream(target.getInventory().getContents())
                        .filter(i -> i != null && i.getType() != Material.AIR)
                        .mapToInt(ItemStack::getAmount).sum();
                target.sendMessage("§cSeu inventário foi limpo por §a" + p.getName() + " §cforam removidos §a" + size + " §citens.");
                p.sendMessage("§cVocê limpou §a" + size + " §citens do inventário de §a" + target.getName() + "§c.");
            }
            return false;
        }

    private boolean inventoryIsEmpty(Player p) {
        PlayerInventory inv = p.getInventory();

        for (ItemStack i : inv.getContents()) {
            if (i != null && i.getType() != Material.AIR)
                return false;
        }

        for (ItemStack i : inv.getArmorContents()) {
            if (i != null && i.getType() != Material.AIR)
                return false;
        }

        if (p.getItemOnCursor() != null && p.getItemOnCursor().getType() != Material.AIR)
            return false;

        return true;
    }
}
