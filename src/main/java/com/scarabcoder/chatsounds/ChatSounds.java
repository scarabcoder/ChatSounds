package com.scarabcoder.chatsounds;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatSounds extends JavaPlugin {

    @Override
    public void onEnable() {


        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        ProtocolLibrary.getProtocolManager().addPacketListener(new OutgoingChatHandler(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT));


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("cs.reload")) {

            sender.sendMessage(ChatColor.RED.toString() + "You don't have permission to execute this command.");
            return true;
        }
        reloadConfig();
        getLogger().info("Config reloaded.");
        sender.sendMessage("ChatSounds config reloaded!");
        return true;
    }


}
