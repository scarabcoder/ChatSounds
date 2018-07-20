package com.scarabcoder.chatsounds;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.regex.Pattern;

class OutgoingChatHandler extends PacketAdapter {

    OutgoingChatHandler(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
    }

    @Override
    public void onPacketSending(PacketEvent event) {

        FileConfiguration config = getPlugin().getConfig();




        BaseComponent[] components = ComponentSerializer.parse(event.getPacket().getChatComponents().read(0).getJson());

        String message = TextComponent.toPlainText(components);

        ConfigurationSection sounds = config.getConfigurationSection("sounds");

        for(String key : sounds.getKeys(false)) {

            ConfigurationSection sound = sounds.getConfigurationSection(key);
            String regexPattern = sound.getString("regex");
            if(Pattern.matches(regexPattern, message)) {

                event.getPlayer().playSound(event.getPlayer().getLocation(), sound.getString("sound"), (float) sound.getDouble("volume"), (float) sound.getDouble("pitch"));

            }

        }

    }


}
