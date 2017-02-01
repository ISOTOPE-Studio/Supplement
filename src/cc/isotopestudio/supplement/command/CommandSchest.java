package cc.isotopestudio.supplement.command;
/*
 * Created by david on 2017/2/1.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.supplement.chest.Package;
import cc.isotopestudio.supplement.chest.S_Array;
import cc.isotopestudio.supplement.util.S;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static cc.isotopestudio.supplement.Supplement.plugin;
import static cc.isotopestudio.supplement.chest.S_Array.arrays;

public class CommandSchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Schest")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("���ִ�е�����"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("supplement.admin")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }

            if (args.length < 1) {
                sendHelpPage1(label, sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("createArray")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " createArray <��������> <��� ��> - ��������"));
                    return true;
                }
                int num;
                try {
                    num = Integer.parseInt(args[2]);
                    if (num < 1) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("���ֲ���"));
                    return true;
                }
                if (arrays.containsKey(args[1])) {
                    arrays.get(args[1]).setInterval(num);
                } else {
                    S_Array array = new S_Array(args[1]);
                    arrays.put(args[1], array);
                    array.setInterval(num);
                }
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("createPackage")) {
                if (args.length < 4) {
                    sender.sendMessage(S.toYellow("/" + label + " createPackage <��������> <����> <��Լ���> - ������"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 1) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("���ֲ���"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                array.createPackage(args[2], num);
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("addItem")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " addItem <��������> <����> - �����Ʒ����"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Package pack = array.getPackage(args[2]);
                if (pack == null) {
                    player.sendMessage(S.toPrefixRed("��������"));
                    return true;
                }
                ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("��Ʒ����"));
                    return true;
                }
                pack.addItem(item);
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("addChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " addChest <��������> - ���Ŀ�����ӵ�����"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                Block block = player.getTargetBlock(null, 5);
                if (block.getType() != Material.CHEST) {
                    player.sendMessage(S.toPrefixRed("��������"));
                    return true;
                }
                array.addChestLocation(block.getLocation());
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.onReload();
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            sendHelpPage1(label, sender);
        }
        return false;
    }

    private void sendHelpPage1(String label, CommandSender sender) {
        sender.sendMessage(S.toPrefixGreen("�����˵�"));
        sender.sendMessage(S.toYellow("/" + label + " createArray <��������> <��� ��> - ��������"));
        sender.sendMessage(S.toItalicYellow("    �������ü��Ҳʹ������ָ��"));
        sender.sendMessage(S.toYellow("/" + label + " createPackage <��������> <����> <��Լ���> - ������"));
        sender.sendMessage(S.toYellow("/" + label + " addItem <��������> <����> - �����Ʒ����"));
        sender.sendMessage(S.toYellow("/" + label + " addChest <��������> - ���Ŀ�����ӵ�����"));
        sender.sendMessage(S.toYellow("/" + label + " reload"));
        System.out.println(S_Array.arrays);
    }
}