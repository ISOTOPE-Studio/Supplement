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
                    if (num < 1)  throw new NumberFormatException();
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
            if (args[0].equalsIgnoreCase("removeArray")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " removeArray <��������> - ɾ������"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                arrays.get(args[1]).delete();
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removePackage")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " removePackage <��������> <����> - ɾ����"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                if (arrays.get(args[1]).removePackage(args[2]))
                    player.sendMessage(S.toPrefixGreen("�ɹ�"));
                else
                    player.sendMessage(S.toPrefixRed("��������"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removeItem")) {
                if (args.length < 4) {
                    ;
                    sender.sendMessage(S.toYellow("/" + label + " removeItem <��������> <����> <ID> - ɾ����Ʒ"));
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
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 0 || num >= pack.getItems().size()) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("���ֲ���"));
                    return true;
                }
                pack.getItems().remove(num);
                pack.save();
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("removeChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " removeChest <��������> <ID> - ɾ������"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                int num;
                try {
                    num = Integer.parseInt(args[3]);
                    if (num < 0 && num >= array.getChestLocations().size()) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(S.toPrefixRed("���ֲ���"));
                    return true;
                }
                array.getChestLocations().remove(num);
                array.saveChestLocation();
                player.sendMessage(S.toPrefixGreen("�ɹ�"));
                return true;
            }
            if (args[0].equalsIgnoreCase("listArray")) {
                sender.sendMessage(S.toPrefixAqua("�����б�"));
                List<S_Array> list = new ArrayList<>(arrays.values());
                for (int i = 0; i < list.size(); i++) {
                    sender.sendMessage(S.toYellow("   " + i + " | " + list.get(i).getName()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listPackage")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " listPackage <��������> - ���б�"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                sender.sendMessage(S.toPrefixAqua("���� " + array.getName() + " �İ��б�"));
                List<Package> list = new ArrayList<>(array.getPackages().values());
                for (int i = 0; i < list.size(); i++) {
                    sender.sendMessage(S.toYellow("   " + i + " | " + list.get(i).getName()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listItem")) {
                if (args.length < 3) {
                    sender.sendMessage(S.toYellow("/" + label + " listItem <��������> <����> - ��Ʒ�б�"));
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
                sender.sendMessage(
                        S.toPrefixAqua("���� " + array.getName() + ", �� " + pack.getName() + " ��Ʒ�б�"));
                sender.sendMessage(S.toGray("    ID | ����"));
                for (int i = 0; i < pack.getItems().size(); i++) {
                    ItemStack item = pack.getItems().get(i);
                    sender.sendMessage(
                            S.toYellow("   " + i + " | " + Util.getItemName(item) + " �� " + item.getAmount()));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("listChest")) {
                if (args.length < 2) {
                    sender.sendMessage(S.toYellow("/" + label + " listChest <��������> - �����б�"));
                    return true;
                }
                if (!arrays.containsKey(args[1])) {
                    player.sendMessage(S.toPrefixRed("���в�����"));
                    return true;
                }
                S_Array array = arrays.get(args[1]);
                sender.sendMessage(
                        S.toPrefixAqua("���� " + array.getName() + ", �� �����б�"));
                sender.sendMessage(S.toGray("    ID | ����"));
                for (int i = 0; i < array.getChestLocations().size(); i++) {
                    Location loc = array.getChestLocations().get(i);
                    sender.sendMessage(
                            S.toYellow("   " + i + " | " + Util.locationToString(loc)));
                }
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

        sender.sendMessage(S.toYellow("/" + label + " removeArray <��������> - ɾ������"));
        sender.sendMessage(S.toYellow("/" + label + " removePackage <��������> <����> - ɾ����"));
        sender.sendMessage(S.toYellow("/" + label + " removeItem <��������> <����> <ID> - ɾ����Ʒ"));
        sender.sendMessage(S.toYellow("/" + label + " removeChest <��������> <ID> - ɾ������"));

        sender.sendMessage(S.toYellow("/" + label + " listArray - �����б�"));
        sender.sendMessage(S.toYellow("/" + label + " listPackage <��������> - ���б�"));
        sender.sendMessage(S.toYellow("/" + label + " listItem <��������> <����> - ��Ʒ�б�"));
        sender.sendMessage(S.toYellow("/" + label + " listChest <��������> - �����б�"));

        sender.sendMessage(S.toYellow("/" + label + " reload"));
        System.out.println(S_Array.arrays);
    }
}