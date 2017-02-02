package cc.isotopestudio.supplement.chest;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        for (S_Array array : S_Array.arrays.values()) {
            if (array.getChestLocations().size() > 0 && array.isReady()) {
                List<Package> list = new ArrayList<>(array.getPackages().values());

                int i;
                double sum = 0;
                double[] luckAcc = new double[list.size()];
                for (i = 0; i < list.size(); i++) {
                    double v = list.get(i).getOdd();
                    luckAcc[i] = sum + v;
                    sum += v;
                }
                sum = luckAcc[list.size() - 1];
                double point = random(sum);
                i = 0;
                while (true) {
                    if (luckAcc[i] < point) {
                        i++;
                        continue;
                    }
                    break;
                }
                List<ItemStack> items = new ArrayList<>(list.get(i).getItems());

                for (Location location : array.getChestLocations()) {
                    if (location.getBlock().getType() == Material.CHEST) {
                        Chest chest = (Chest) location.getBlock().getState();
                        Inventory inv = chest.getBlockInventory();
                        inv.clear();
                        List<Integer> ints = new ArrayList<>();
                        for (int j = 0; j < inv.getSize(); j++) {
                            ints.add(j);
                        }
                        Collections.shuffle(ints);
                        while (items.size() > 0 && ints.size() > 0) {
                            inv.setItem(ints.remove(0), items.remove(0));
                        }
                    }
                }

            }
        }
    }

    private static double random(double max) {
        return Math.random() * max;
    }

}
