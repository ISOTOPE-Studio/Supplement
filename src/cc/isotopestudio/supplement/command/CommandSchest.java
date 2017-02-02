package cc.isotopestudio.supplement.command;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.supplement.chest.Package;
import cc.isotopestudio.supplement.chest.S_Array;
import cc.isotopestudio.supplement.util.S;
import cc.isotopestudio.supplement.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cc.isotopestudio.supplement.Supplement.plugin;
import static cc.isotopestudio.supplement.chest.S_Array.arrays;

public class CommandSchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Schest")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("玩家执行的命令"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("supplement.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }

            if (args.length < 1) {
                sendHelpPage1(label, sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("createArray")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " createArray <队列名字> <间隔 秒> - 创建队列"));
                    return true;
                }
                int num;
                try {
                    num = Integer.parseInt(args[2]);
                    if (num < 1)  throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("数字不对"));
                    return true;
                }
                if (arrays.containsKey(args[1])) {
                    arrays.get(args[1]).setInterval(num);
                } else {
                    S_Array array = new S_Array(args[1]);
                    arrays.put(args[1], array);
                    array.setInterval(num);
                }
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("createPackage")) {
                if (args.length < 4) {
                    sender.sendMessage(S.toYellow("/" + label + " createPackage <队列名字> <包名> <相对几率> - 创建包"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 1) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("数字不对"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                array.createPackage(args[2], num);
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("addItem")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " addItem <队列名字> <包名> - 添加物品到包"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Package pack = array.getPackage(args[2]);
                if (pack == null) {
                    player.sendMessage(S.toPrefixRed("包不存在"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("物品不对"));
                    return true;
                }
                pack.addItem(item);
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("addChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " addChest <队列名字> - 添加目标箱子到队列"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Block block = player.getTargetBlock(null, 5);
                if (block.getType() != Material.CHEST) {
                    player.sendMessage(S.toPrefixRed("不是箱子"));
                    return true;
                }
                array.addChestLocation(block.getLocation());
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removeArray")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " removeArray <队列名字> - 删除队列"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                arrays.get(args[1]).delete();
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removePackage")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " removePackage <队列名字> <包名> - 删除包"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                if (arrays.get(args[1]).removePackage(args[2]))
                    player.sendMessage(S.toPrefixGreen("成功"));
                else
                    player.sendMessage(S.toPrefixRed("包不存在"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removeItem")) {
                if (args.length < 4) {
                    ;
                    sender.sendMessage(S.toYellow("/" + label + " removeItem <队列名字> <包名> <ID> - 删除物品"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Package pack = array.getPackage(args[2]);
                if (pack == null) {
                    player.sendMessage(S.toPrefixRed("包不存在"));
                    return true;
                }
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 0 || num >= pack.getItems().size()) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("数字不对"));
                    return true;
                }
                pack.getItems().remove(num);
                pack.save();
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removeChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " removeChest <队列名字> <ID> - 删除箱子"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 0 && num >= array.getChestLocations().size()) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("数字不对"));
                    return true;
                }
                array.getChestLocations().remove(num);
                array.saveChestLocation();
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            if (args[0].equalsIgnoreCase("listArray")) {
                sender.sendMessage(S.toPrefixAqua("队列列表"));
                List<S_Array> list = new ArrayList<>(arrays.values());
                for (int i = 0; i < list.size(); i++) {
                    sender.sendMessage(S.toYellow("   " + i + " | " + list.get(i).getName()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listPackage")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " listPackage <队列名字> - 包列表"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                sender.sendMessage(S.toPrefixAqua("队列 " + array.getName() + " 的包列表"));
                List<Package> list = new ArrayList<>(array.getPackages().values());
                for (int i = 0; i < list.size(); i++) {
                    sender.sendMessage(S.toYellow("   " + i + " | " + list.get(i).getName()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listItem")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " listItem <队列名字> <包名> - 物品列表"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Package pack = array.getPackage(args[2]);
                if (pack == null) {
                    player.sendMessage(S.toPrefixRed("包不存在"));
                    return true;
                }
                sender.sendMessage(
                        S.toPrefixAqua("队列 " + array.getName() + ", 包 " + pack.getName() + " 物品列表"));
                sender.sendMessage(S.toGray("    ID | 名称"));
                for (int i = 0; i < pack.getItems().size(); i++) {
                    ItemStack item = pack.getItems().get(i);
                    sender.sendMessage(
                            S.toYellow("   " + i + " | " + Util.getItemName(item) + " × " + item.getAmount()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " listChest <队列名字> - 箱子列表"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("队列不存在"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                sender.sendMessage(
                        S.toPrefixAqua("队列 " + array.getName() + ", 包 箱子列表"));
                sender.sendMessage(S.toGray("    ID | 名称"));
                for (int i = 0; i < array.getChestLocations().size(); i++) {
                    Location loc = array.getChestLocations().get(i);
                    sender.sendMessage(
                            S.toYellow("   " + i + " | " + Util.locationToString(loc)));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                player.sendMessage(S.toPrefixGreen("成功"));
                return true;
            }
            sendHelpPage1(label, sender);
        }
        return false;
    }

    private void sendHelpPage1(String label, CommandSender sender) {
        sender.sendMessage(S.toPrefixGreen("帮助菜单"));
        sender.sendMessage(S.toYellow("/" + label + " createArray <队列名字> <间隔 秒> - 创建队列"));
        sender.sendMessage(S.toItalicYellow("    重新设置间隔也使用如上指令"));
        sender.sendMessage(S.toYellow("/" + label + " createPackage <队列名字> <包名> <相对几率> - 创建包"));
        sender.sendMessage(S.toYellow("/" + label + " addItem <队列名字> <包名> - 添加物品到包"));
        sender.sendMessage(S.toYellow("/" + label + " addChest <队列名字> - 添加目标箱子到队列"));

        sender.sendMessage(S.toYellow("/" + label + " removeArray <队列名字> - 删除队列"));
        sender.sendMessage(S.toYellow("/" + label + " removePackage <队列名字> <包名> - 删除包"));
        sender.sendMessage(S.toYellow("/" + label + " removeItem <队列名字> <包名> <ID> - 删除物品"));
        sender.sendMessage(S.toYellow("/" + label + " removeChest <队列名字> <ID> - 删除箱子"));

        sender.sendMessage(S.toYellow("/" + label + " listArray - 队列列表"));
        sender.sendMessage(S.toYellow("/" + label + " listPackage <队列名字> - 包列表"));
        sender.sendMessage(S.toYellow("/" + label + " listItem <队列名字> <包名> - 物品列表"));
        sender.sendMessage(S.toYellow("/" + label + " listChest <队列名字> - 箱子列表"));

        sender.sendMessage(S.toYellow("/" + label + " reload"));
        System.out.println(S_Array.arrays);
    }
}