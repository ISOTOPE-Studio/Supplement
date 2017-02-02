package cc.isotopestudio.supplement.util;
/*
 * Created by Mars Tan on 12/31/2016.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class Util {
    public static String locationToString(Location loc) {
        if (loc == null) return "";
        return loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }

    public static Location stringToLocation(String string) {
        if (string == null) return null;
        String[] s = string.split(" ");
        if (s.length != 4) return null;
        World world = Bukkit.getWorld(s[0]);
        int x = Integer.parseInt(s[1]);
        int y = Integer.parseInt(s[2]);
        int z = Integer.parseInt(s[3]);
        return new Location(world, x + .5, y, z + .5);
    }


    public static Material getMaterialByName(String string) {
        if (string == null) return null;
        Material material = Material.getMaterial(string);
        if (material != null) return material;
        int id;
        try {
            id = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
        material = Material.getMaterial(id);
        if (material != null) return material;
        return null;
    }

    public static String getItemName(ItemStack item) {
        return (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ?
                item.getItemMeta().getDisplayName() : item.getType().name();
    }
}
