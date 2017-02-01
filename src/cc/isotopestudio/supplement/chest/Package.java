package cc.isotopestudio.supplement.chest;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cc.isotopestudio.supplement.Supplement.chestData;

public class Package {
    private final String arrayName;
    private final String name;
    private List<ItemStack> items = new ArrayList<>();
    private int odd;

    Package(String arrayName, String name, int odd) {
        this.arrayName = arrayName;
        this.name = name;
        this.odd = odd;
    }

    Package(String arrayName, String name, int odd, List<ItemStack> items) {
        this(arrayName, name, odd);
        this.items = items;
    }

    public int getOdd() {
        return odd;
    }

    public void addItem(ItemStack item) {
        items.add(item);
        save();
    }

    public List<ItemStack> getItems() {
        return new ArrayList<>(items);
    }

    private void save() {
        ConfigurationSection section = chestData.createSection(arrayName + ".packages." + name);
        section.set("odd", odd);
        for (int i = 0; i < items.size(); i++) {
            section.set("items." + i, items.get(i));
        }
        chestData.save();
    }

    @Override
    public String toString() {
        return "Package{" + "arrayName='" + arrayName + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", odd=" + odd +
                "}\n";
    }
}
