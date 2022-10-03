package hpsquaredforge;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class file {
    public static String SecsToTime(int seconds) {
        int hours = (int) Math.floor(seconds / 3600);
        int minutes = (int) (Math.floor(seconds / 60) - hours * 60);
        int secs = (int) Math.floor(seconds - ((hours * 3600) + (minutes * 60)));

        return hours + " hours, " + minutes + " minutes, " + secs + " seconds";
    }

    public enum Category implements Serializable {
        WEAPON,
        ARMOR,
        RAW_MATERIAL,
        COMPLEX_MATERIAL,
        TOOL,
        ACCESSORY,
    }

    static String file_path() {
        return System.getProperty("user.dir") + "/plugins/forge_data";
    }

    public static class ForgeItem implements Serializable {
        public Category category;
        public ItemStack item;
        public List<ItemStack> ingredients;
        public long time_in_ticks;

        public ItemStack getItem() {
            return item;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public void setItem(ItemStack items) {
            this.item = items;
        }

        public List<ItemStack> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<ItemStack> ingredients) {
            this.ingredients = ingredients;
        }
    }

    public static class OngoingRecipe implements Serializable {
        public Player player;
        public ForgeItem item;
        public long finish_time;


        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public ForgeItem getItem() {
            return item;
        }

        public void setItem(ForgeItem item) {
            this.item = item;
        }

        public int getFinish_time() {
            return (int) finish_time;
        }

        public void setFinish_time(int finish_time) {
            this.finish_time = finish_time;
        }
    }

    public static class SaveFile implements Serializable {
        public List<ForgeItem> items;
        public List<String> whitelist;
        public List<OngoingRecipe> recipes;

        public List<ForgeItem> getItems() {
            return items;
        }

        public void setItems(List<ForgeItem> items) {
            this.items = items;
        }

        public List<String> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(List<String> whitelist) {
            this.whitelist = whitelist;
        }

        public List<OngoingRecipe> getRecipes() {
            return recipes;
        }

        public void setRecipes(List<OngoingRecipe> recipes) {
            this.recipes = recipes;
        }

        public SaveFile() {
            File f = new File(System.getProperty("user.dir") + "\\plugins\\forge_data");

            if (f.isFile()) {
                SaveFile data = Deserialize();
                if (data == null) {
                    this.items = new LinkedList<ForgeItem>();
                    this.whitelist = List.of(new String[]{"Siliwolf"});
                    this.recipes = new LinkedList<OngoingRecipe>();
                }
                else {
                    this.items = data.items;
                    this.recipes = data.recipes;
                    this.whitelist = data.whitelist;
                }
            }
        }

        public static void Serialize(Serializable data) {
            try {
                ObjectOutputStream o = new BukkitObjectOutputStream(new FileOutputStream(file_path()));
                o.writeObject(data);
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public SaveFile Deserialize() {
            try {
                ObjectInputStream i = new BukkitObjectInputStream(new FileInputStream(file_path()));
                SaveFile s = (SaveFile) i.readObject();
                i.close();
                return s;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {}

            return null;
        }

        public void SetData(SaveFile data) throws IOException {
            Serialize(data);
        }
    }
}
