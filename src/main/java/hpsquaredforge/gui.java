package hpsquaredforge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public class gui implements Listener {
    public enum State {
        MENU,
        ARMOR,
        TOOLS,
        RAW_MATERIALS,
        WEAPONS,
        ACCESSORIES,
        COMPLEX_MATERIALS,
    }

    public Inventory get_gui(State state, Player p) {
        // Collect Items
        {
            file.SaveFile data = new file.SaveFile();

            Inventory i = Bukkit.createInventory(p, 54, "Collect Items");

            for (hpsquaredforge.file.OngoingRecipe r : data.getRecipes()) {
                if (p != null) {
                    if (!p.equals(r.getPlayer())) {
                        continue;
                    }
                }

                if (r.finish_time < System.currentTimeMillis())
                    i.addItem(r.getItem().item);
            }

            if (!i.isEmpty()) {
                return i;
            }
        }

        Inventory gui = Bukkit.createInventory(null, 54, "Forge");
        file.SaveFile data = new file.SaveFile();

        ItemStack header_item = new ItemStack(Material.IRON_ORE);
        ItemMeta header_item_meta = header_item.getItemMeta();
        header_item_meta.setDisplayName(ChatColor.GOLD + "The Forge");
        String[] header_item_lore = {"Here is where you", "can create very", "powerful items to keep", "out your competition!"};
        header_item_meta.setLore(Arrays.stream(header_item_lore).toList());
        header_item.setItemMeta(header_item_meta);

        NamespacedKey forge_item_key = new NamespacedKey(HPSquaredForge.GetPlugin(), "forge_item");

        switch (state) {
            case MENU -> {
                for (int i = 0; i < 54; i++) {
                    gui.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                }

                gui.setItem(13, header_item);

                gui.setItem(3, new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
                gui.setItem(4, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
                gui.setItem(5, new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
                gui.setItem(11, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                gui.setItem(12, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                gui.setItem(14, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                gui.setItem(15, new ItemStack(Material.RED_STAINED_GLASS_PANE));

                gui.setItem(19, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(21, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(23, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(25, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(28, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(29, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(30, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(31, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(32, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(33, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(34, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(37, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(39, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(41, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
                gui.setItem(43, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));

                ItemStack armor_item = new ItemStack(Material.IRON_CHESTPLATE);
                ItemMeta armor_item_meta = armor_item.getItemMeta();
                armor_item_meta.setDisplayName(ChatColor.BLUE + "Armor");
                armor_item.setItemMeta(armor_item_meta);
                gui.setItem(20, armor_item);

                ItemStack tool_item = new ItemStack(Material.IRON_PICKAXE);
                ItemMeta tool_item_meta = tool_item.getItemMeta();
                tool_item_meta.setDisplayName(ChatColor.BLUE + "Tools");
                tool_item.setItemMeta(tool_item_meta);
                gui.setItem(22, tool_item);

                ItemStack raw_item = new ItemStack(Material.RAW_IRON);
                ItemMeta raw_item_meta = raw_item.getItemMeta();
                raw_item_meta.setDisplayName(ChatColor.BLUE + "Raw Materials");
                raw_item.setItemMeta(raw_item_meta);
                gui.setItem(24, raw_item);

                ItemStack weapon_item = new ItemStack(Material.IRON_SWORD);
                ItemMeta weapon_item_meta = weapon_item.getItemMeta();
                weapon_item_meta.setDisplayName(ChatColor.BLUE + "Weapons");
                weapon_item.setItemMeta(weapon_item_meta);
                gui.setItem(38, weapon_item);

                ItemStack accessory_item = new ItemStack(Material.CLOCK);
                ItemMeta accessory_item_meta = accessory_item.getItemMeta();
                accessory_item_meta.setDisplayName(ChatColor.BLUE + "Accessories");
                accessory_item.setItemMeta(accessory_item_meta);
                gui.setItem(40, accessory_item);

                ItemStack complex_item = new ItemStack(Material.RAW_GOLD_BLOCK);
                ItemMeta complex_item_meta = complex_item.getItemMeta();
                complex_item_meta.setDisplayName(ChatColor.BLUE + "Complex Materials");
                complex_item.setItemMeta(complex_item_meta);
                gui.setItem(42, complex_item);

                break;
            }
            case ARMOR -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "ARMOR");
                    List<String> lines = new LinkedList<>();;
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }

                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));

                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);
                    if (a.category == file.Category.ARMOR) {
                        gui.addItem(a.getItem());
                    }
                }
            }
            case TOOLS -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "TOOLS");
                    List<String> lines = new LinkedList<>();
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }
                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));
                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);
                    if (a.category == file.Category.TOOL) {
                        gui.addItem(a.getItem());
                    }
                }
            }
            case WEAPONS -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "WEAPONS");
                    List<String> lines = new LinkedList<>();;
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }
                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));
                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);
                    if (a.category == file.Category.WEAPON) {
                        gui.addItem(a.getItem());
                    }
                }
            }
            case ACCESSORIES -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    if (a.item.getType() == Material.AIR) {
                        continue;
                    }
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "ACCESSORIES");
                    List<String> lines = new LinkedList<>();;
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }
                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));
                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);
                    if (a.category == file.Category.ACCESSORY) {
                        gui.addItem(a.getItem());
                    }
                }
            }
            case RAW_MATERIALS -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "RAW_MATERIALS");
                    List<String> lines = new LinkedList<>();;
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }
                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));
                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);
                    if (a.category == file.Category.RAW_MATERIAL) {
                        gui.addItem(a.getItem());
                    }
                }
            }
            case COMPLEX_MATERIALS -> {
                gui.addItem(header_item);
                for (file.ForgeItem a : data.items) {
                    ItemMeta meta = null;
                    try {
                        meta = a.getItem().getItemMeta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    meta.getPersistentDataContainer().set(forge_item_key, PersistentDataType.STRING, "COMPLEX_MATERIALS");
                    List<String> lines = new LinkedList<>();;
                    for (ItemStack s : a.getIngredients()) {
                        if (s != null) {
                            if (s.getItemMeta().hasDisplayName()) {
                                System.out.println(s.getAmount() + "x " + s.getItemMeta().displayName().examinableName());
                                lines.add(s.getAmount() + "x " + s.getItemMeta().getDisplayName());
                            }
                            else {
                                System.out.println(s.getAmount() + "x " + s.getType());
                                lines.add(s.getAmount() + "x " + s.getType());
                            }
                        }
                    }
                    lines.add(file.SecsToTime((int) (a.time_in_ticks / 20)));
                    if (lines != null) {
                        meta.setLore(lines);
                    }
                    a.getItem().setItemMeta(meta);

                    if (a.category == file.Category.COMPLEX_MATERIAL) {
                        gui.addItem(a.getItem());
                    }
                }
            }
        }

        return gui;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent c) {
        if (c.getCurrentItem() == null) {
            return;
        }

        // Pickup GUI
        if (c.getView().getTitle().equals("Collect Items")) {
            file.SaveFile data = new file.SaveFile();

            c.setCancelled(true);

            for (file.OngoingRecipe r : data.recipes) {
                if (r.item.item.equals(c.getCurrentItem())) {
                    c.getWhoClicked().getInventory().addItem(r.item.item);
                    data.getRecipes().remove(r);
                    c.setCurrentItem(new ItemStack(Material.COAL));
                    break;
                }
            }

            file.SaveFile.Serialize(data);
        }

        ItemStack header_item = new ItemStack(Material.IRON_ORE);
        ItemMeta header_item_meta = header_item.getItemMeta();
        header_item_meta.setDisplayName(ChatColor.GOLD + "The Forge");
        String[] header_item_lore = {"Here is where you", "can create very", "powerful items to keep", "out your competition!"};
        header_item_meta.setLore(Arrays.stream(header_item_lore).toList());
        header_item.setItemMeta(header_item_meta);

        if (c.getInventory().contains(header_item)) {
            c.setCancelled(true);
            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Armor")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.ARMOR, (Player) c.getWhoClicked()));
            }

            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Weapons")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.WEAPONS, (Player) c.getWhoClicked()));
            }

            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Accessories")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.ACCESSORIES, (Player) c.getWhoClicked()));
            }

            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Raw Materials")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.RAW_MATERIALS, (Player) c.getWhoClicked()));
            }

            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Complex Materials")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.COMPLEX_MATERIALS, (Player) c.getWhoClicked()));
            }

            if (c.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Tools")) {
                c.getWhoClicked().closeInventory();
                c.getWhoClicked().openInventory(get_gui(State.TOOLS, (Player) c.getWhoClicked()));
            }

            file.SaveFile data = new file.SaveFile();

            // Start recipe
            NamespacedKey forge_item_key = new NamespacedKey(HPSquaredForge.GetPlugin(), "forge_item");
            if (c.getCurrentItem().getItemMeta().getPersistentDataContainer().has(forge_item_key)) {
                file.ForgeItem item = null;
                for (file.ForgeItem i : data.items) {
                    if (i.item.getItemMeta().getDisplayName().equals(c.getCurrentItem().getItemMeta().getDisplayName())) {
                        item = i;
                    } else {
                        break;
                    }
                }

                boolean enough_mats = true;
                for (ItemStack i : item.getIngredients()) {
                    if(i != null) {
                        if (!c.getWhoClicked().getInventory().containsAtLeast(i, i.getAmount())) {
                            c.getWhoClicked().sendMessage(ChatColor.RED + "You don't have the required materials!");
                            enough_mats = false;
                        }
                    }
                }

                if (enough_mats == false) {
                    return;
                }

                for (ItemStack i : item.getIngredients()) {
                    if (i != null) {
                        c.getWhoClicked().getInventory().removeItem(i);
                    }
                }

                file.OngoingRecipe o = new file.OngoingRecipe();
                o.item = item;
                o.finish_time = System.currentTimeMillis() + (item.time_in_ticks * 50);
                o.player = (Player) c.getWhoClicked();

                data.recipes.add(o);

                try {
                    data.SetData(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Could return by here, don't add any code that needs to run consistently

            try {
                data.SetData(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void InteractEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getScoreboardTags().contains("forge_ghast")) {
            e.getPlayer().openInventory(get_gui(State.MENU, e.getPlayer()));
        }
    }

    @EventHandler
    public void CloseGui(InventoryCloseEvent e) {
        // Create Forge Recipe Stuff
        if (e.getPlayer().getPersistentDataContainer().has(create_forge_recipe.forge_creator_key)) {
            long cooldown = e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_key, PersistentDataType.LONG);
            e.getPlayer().getPersistentDataContainer().remove(create_forge_recipe.forge_creator_key);

            file.SaveFile data = new file.SaveFile();

            file.ForgeItem item = new file.ForgeItem();

            item.item = e.getPlayer().getInventory().getItemInMainHand();
            item.time_in_ticks = cooldown;
            file.Category cat = null;
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("armor")) {
                cat = file.Category.ARMOR;
            }
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("weapon")) {
                cat = file.Category.WEAPON;
            }
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("raw_material")) {
                cat = file.Category.RAW_MATERIAL;
            }
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("complex_material")) {
                cat = file.Category.COMPLEX_MATERIAL;
            }
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("accessory")) {
                cat = file.Category.ACCESSORY;
            }
            if (e.getPlayer().getPersistentDataContainer().get(create_forge_recipe.forge_creator_category_key, PersistentDataType.STRING).toLowerCase(Locale.ROOT).equals("tool")) {
                cat = file.Category.TOOL;
            }
            if (cat == null) {
                e.getPlayer().sendMessage(ChatColor.RED + "Invalid category! Possible options: \"weapon\" \"armor\" \"raw_material\" \"complex_material\" \"accessory\" \"tool\"");
                return;
            }
            item.category = cat;
            item.ingredients = Arrays.stream(e.getInventory().getContents()).toList();

            data.items.add(item);

            try {
                data.SetData(data);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
