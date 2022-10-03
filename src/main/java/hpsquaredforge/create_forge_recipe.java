package hpsquaredforge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class create_forge_recipe implements CommandExecutor {
    public static NamespacedKey forge_creator_key = new NamespacedKey(HPSquaredForge.GetPlugin(), "forge_creator");
    public static NamespacedKey forge_creator_category_key = new NamespacedKey(HPSquaredForge.GetPlugin(), "forge_creator_category");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long cooldown = Long.parseLong(args[0]);
        String category = args[1];

        Player p = (Player) sender;

        if (p.getInventory().getItemInMainHand() == null) {
            p.sendMessage(ChatColor.RED + "Hold an item in your hand");
            return false;
        }

        if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "Hold an item in your hand");
            return false;
        }

        p.getPersistentDataContainer().set(forge_creator_key, PersistentDataType.LONG, cooldown);
        p.getPersistentDataContainer().set(forge_creator_category_key, PersistentDataType.STRING, category);

        p.openInventory(Bukkit.createInventory(null, 54, "Ingredients"));

        return true;
    }
}