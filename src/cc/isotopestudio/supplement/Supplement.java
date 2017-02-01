package cc.isotopestudio.supplement;

import cc.isotopestudio.supplement.chest.ArrayUpdateTask;
import cc.isotopestudio.supplement.chest.UpdateTask;
import cc.isotopestudio.supplement.command.CommandSchest;
import cc.isotopestudio.supplement.util.PluginFile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Supplement extends JavaPlugin {

    private static final String pluginName = "Supplement";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("Supplement").append("]").append(ChatColor.RED).toString();

    public static Supplement plugin;

    public static PluginFile chestData;

    @Override
    public void onEnable() {
        plugin = this;
        chestData = new PluginFile(this, "chest.yml");

        this.getCommand("schest").setExecutor(new CommandSchest());

        new UpdateTask().runTask(this);

        new ArrayUpdateTask().runTaskTimer(this, 20, 20);


        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        chestData.reload();
        new UpdateTask().runTask(this);
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

}
