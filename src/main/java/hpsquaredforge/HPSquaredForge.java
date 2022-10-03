package hpsquaredforge;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public final class HPSquaredForge extends JavaPlugin {
    public static HPSquaredForge INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new gui(),this);
        this.getCommand("create_forge_recipe").setExecutor(new create_forge_recipe());
        this.getCommand("remove_item").setExecutor(new remove_item());
        this.getCommand("internal_remove_item").setExecutor(new internal_remove_item());

        File f = new File(System.getProperty("user.dir") + "\\plugins\\forge_data");
        try {
            if (Files.readAllBytes(f.toPath()).length < 1) {
                System.out.println("Creating data");
                hpsquaredforge.file.SaveFile.Serialize(new file.SaveFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HPSquaredForge GetPlugin() {
        return INSTANCE;
    }
}
