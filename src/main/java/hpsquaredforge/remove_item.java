package hpsquaredforge;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class remove_item implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        file.SaveFile data = new file.SaveFile();

        for(file.ForgeItem i : data.getItems()) {
            Player p = (Player) sender;

            TextComponent text = new TextComponent(ChatColor.GREEN + "[" + ChatColor.WHITE + i.item + ", " + i.ingredients + ChatColor.GREEN + "]");
            text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/internal_remove_item " + data.getItems().indexOf(i)));
            p.sendMessage(text);
        }

        return true;
    }
}
