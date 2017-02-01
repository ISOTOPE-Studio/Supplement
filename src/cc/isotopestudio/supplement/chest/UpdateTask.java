package cc.isotopestudio.supplement.chest;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import org.bukkit.scheduler.BukkitRunnable;

import static cc.isotopestudio.supplement.Supplement.chestData;
import static cc.isotopestudio.supplement.chest.S_Array.arrays;

public class UpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        arrays.clear();

        for (String arrayName : chestData.getKeys(false)) {
            arrays.put(arrayName, new S_Array(arrayName));
        }
    }

}
