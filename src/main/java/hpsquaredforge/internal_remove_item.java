package hpsquaredforge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class internal_remove_item implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        file.SaveFile data = new file.SaveFile();
        int idx = Integer.parseInt(args[0]);

        data.items.remove(idx);

        file.SaveFile.Serialize(data);

        return true;
    }
}
