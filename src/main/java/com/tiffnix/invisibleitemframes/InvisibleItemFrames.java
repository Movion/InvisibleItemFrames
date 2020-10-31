package com.tiffnix.invisibleitemframes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class InvisibleItemFrames extends JavaPlugin {
    public static InvisibleItemFrames INSTANCE;
    public static NamespacedKey KEY_IS_INVISIBLE;
    public static ItemStack INVISIBLE_FRAME;

    public static boolean isInvisibleItemFrame(ItemStack item) {
        if (item == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return meta.getPersistentDataContainer().has(KEY_IS_INVISIBLE, PersistentDataType.BYTE);
    }

    public static boolean isInvisibleItemFrame(Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getType() != EntityType.ITEM_FRAME) {
            return false;
        }
        return entity.getPersistentDataContainer().has(KEY_IS_INVISIBLE, PersistentDataType.BYTE);
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        KEY_IS_INVISIBLE = new NamespacedKey(this, "invisible");

        getServer().getPluginManager().registerEvents(new PluginListener(), this);

        ItemStack item = new ItemStack(Material.ITEM_FRAME);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Invisible Item Frame");
        meta.getPersistentDataContainer().set(KEY_IS_INVISIBLE, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setAmount(8);
        INVISIBLE_FRAME = item;

        NamespacedKey key = new NamespacedKey(this, "invisible_item_frame");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("FFF", "F F", "FFF");
        recipe.setIngredient('F', Material.ITEM_FRAME);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
