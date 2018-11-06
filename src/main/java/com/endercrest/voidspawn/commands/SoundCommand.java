package com.endercrest.voidspawn.commands;

import com.endercrest.voidspawn.ConfigManager;
import com.endercrest.voidspawn.VoidSpawn;
import com.endercrest.voidspawn.utils.MessageUtil;
import com.endercrest.voidspawn.utils.NumberUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SoundCommand implements SubCommand {

    @Override
    public boolean onCommand(Player p, String[] args){
        if(args.length == 1){
            p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix +
                    "All sounds available are listed here: https://goo.gl/jGcL8B or use 'clear' to remove the sound."));
            return true;
        }

        if(args.length >= 2){
            String soundName = args[1].toUpperCase();

            if(soundName.equalsIgnoreCase("clear")){
                ConfigManager.getInstance().removeSound(p.getWorld().getName());
                p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("Cleared sound from %s.", p.getWorld().getName())));
                return true;
            }

            org.bukkit.Sound sound;
            try{
                sound = org.bukkit.Sound.valueOf(soundName.toUpperCase());
            }catch(IllegalArgumentException e){
                p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("&c%s is not a valid sound!", soundName)));
                return false;
            }
            ConfigManager.getInstance().setSound(p.getWorld().getName(), sound.name());
        }

        if(args.length >= 3){
            String volume = args[2];

            if(!NumberUtil.isFloat(volume)){
                p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("&c%s is not a valid number.", volume)));
                return false;
            }
            ConfigManager.getInstance().setVolume(p.getWorld().getName(), Float.parseFloat(volume));
        }

        if(args.length >= 4){
            String pitch = args[3];

            if(!NumberUtil.isFloat(pitch)){
                p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("&c%s is not a valid number.", pitch)));
                return false;
            }
            ConfigManager.getInstance().setPitch(p.getWorld().getName(), Float.parseFloat(pitch));
        }

        if(args.length == 2){
            p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("Sound successfully set for %s.", p.getWorld().getName())));
        }else if(args.length == 3){
            p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("Sound, and volume successfully set for %s.", p.getWorld().getName())));
        }else if(args.length >= 4){
            p.sendMessage(MessageUtil.colorize(VoidSpawn.prefix + String.format("Sound, volume, and pitch successfully set for %s.", p.getWorld().getName())));
        }
        return true;
    }

    @Override
    public String helpInfo(){
        return "/vs sound (sound) [volume] [pitch] - Sets the sound when entering the void.";
    }

    @Override
    public String permission(){
        return "cc.admin.sound";
    }

    @Override
    public List<String> getTabCompletion(Player player, String[] args) {
        switch(args.length) {
            case 1:
                List<String> sounds = Arrays.stream(Sound.values()).map(Enum::name).collect(Collectors.toList());
                sounds.add("none");
                return sounds
                        .stream()
                        .filter(sound -> sound.toLowerCase().startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            case 2:
            case 3:
                return new ArrayList<String>() {{
                    add("0");
                    add("0.25");
                    add("0.5");
                    add("0.75");
                    add("1");
                }};
            default:
                return new ArrayList<>();
        }
    }
}
