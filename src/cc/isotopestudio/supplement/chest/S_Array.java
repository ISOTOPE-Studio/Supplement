package cc.isotopestudio.supplement.chest;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.supplement.util.Util;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static cc.isotopestudio.supplement.Supplement.chestData;

public class S_Array {

    public static final Map<String, S_Array> arrays = new HashMap<>();

    private ConfigurationSection config;

    private final String name;
    private int interval;
    private int count = 0;

    private final Set<Location> chestLocations = new HashSet<>();
    private final Map<String, Package> packages = new HashMap<>();


    public S_Array(String name) {
        this.name = name;
        config = chestData.getConfigurationSection(name);
        if (config == null) {
            config = chestData.createSection(name);
        } else {
            loadFromConfig();
        }
        arrays.put(name, this);
    }

    private void loadFromConfig() {
        interval = config.getInt("interval");
        if (config.isConfigurationSection("packages")) {
            for (String packageName : config.getConfigurationSection("packages").getKeys(false)) {
                int odd = config.getInt("packages." + packageName + ".odd");
                List<ItemStack> items = new ArrayList<>();
                if (config.isConfigurationSection("packages." + packageName + ".items")) {
                    for (String numKey : config.getConfigurationSection("packages." + packageName + ".items").getKeys(false)) {
                        items.add(config.getItemStack("packages." + packageName + ".items." + numKey));
                    }
                }
                packages.put(packageName, new Package(name, packageName, odd, items));
            }
        }
        for (String chestLocationLine : config.getStringList("chests")) {
            chestLocations.add(Util.stringToLocation(chestLocationLine));
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
        config.set("interval", interval);
        chestData.save();
    }

    public Map<String, Package> getPackages() {
        return packages;
    }

    public Package getPackage(String name) {
        return packages.containsKey(name) ? packages.get(name) : null;
    }

    public Package createPackage(String packageName, int odd) {
        Package aPackage = new Package(name, packageName, odd);
        packages.put(packageName, aPackage);
        config.set("packages." + packageName + ".odd", odd);
        chestData.save();
        return aPackage;
    }

    public void addChestLocation(Location loc) {
        chestLocations.add(loc);
        config.set("chests", null);
        List<String> list = new ArrayList<>();
        for (Location location : chestLocations) {
            list.add(Util.locationToString(location));
        }
        config.set("chests", list);
        chestData.save();
    }

    public Set<Location> getChestLocations() {
        return chestLocations;
    }

    /*
        should be executed every second
        */
    boolean isReady() {
        if (count == interval) {
            count = 0;
            return true;
        }
        count++;
        return false;
    }

    @Override
    public String toString() {
        return "S_Array{" + ", name='" + name + '\'' +
                "\n, interval=" + interval +
                "\n, count=" + count +
                "\n, chestLocations=" + chestLocations +
                "\n, packages=" + packages +
                "\n}";
    }
}
